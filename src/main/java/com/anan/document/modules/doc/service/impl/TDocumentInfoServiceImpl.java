package com.anan.document.modules.doc.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.aliyuncs.exceptions.ClientException;
import com.anan.document.modules.constant.QuartzConstant;
import com.anan.document.modules.doc.entity.*;
import com.anan.document.modules.doc.mapper.TDocumentInfoMapper;
import com.anan.document.modules.doc.service.IntegrationAuthService;
import com.anan.document.modules.sys.config.dto.CfgBaseDTO;
import com.anan.document.modules.sys.config.dto.CfgUploadLocalDTO;
import com.anan.document.modules.sys.config.enums.UploadType;
import com.anan.document.modules.sys.config.service.CfgBaseService;
import com.anan.document.modules.sys.config.service.CfgUploadLocalService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yf.boot.base.api.api.dto.PagingReqDTO;
import com.yf.boot.base.api.exception.ServiceException;
import com.yf.boot.base.api.utils.BeanMapper;
import com.yf.document.ability.doc.service.OfficeService;
import com.anan.document.modules.doc.dto.TDocumentInfoDTO;
import com.yf.document.modules.doc.entity.*;
import com.yf.document1.modules.doc.entity.*;
import com.anan.document.modules.doc.service.TDocumentInfoService;
import com.anan.document.utils.QuartzUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.*;

import java.util.stream.Collectors;

/**
 * <p>
 * 文档信息表业务实现类
 * </p>
 *
 * @author Panjp
 * @since 2021-08-23 20:30
 */
@Service
@Slf4j
public class TDocumentInfoServiceImpl extends ServiceImpl<TDocumentInfoMapper, TDocumentInfo> implements TDocumentInfoService {

    private final String[] filterStr = {"zzDCC", "zz", "jhDCC", "jh", "mkDCC", "mk", "mkDCC", "yxkjDCC", "yxkj"};
    private final String[] integratedDocs = {"1603246114459107329", "1603246134180724737",
            "1603246034305957890", "1603246056913256450", "1603555653465210881", "1603246085677793281"};
    private final String[] docSupRecJi = {"1603246085677793281", "1603246114459107329", "1603246134180724737"};
    private final String[] roles = {"ahb", "ccwl", "cw", "pzgl", "rlzy", "ssyx", "swcg", "swfw", "xm", "yfzx", "zhgl"};
    private final String role = "ahb" + ",ccwl" + ",cw" + ",pzgl" + ",rlzy" + ",ssyx" + ",swcg" + ",swfw" + ",xm" + ",yfzx" + ",zhgl";
    @Autowired
    private OfficeService officeService;
    @Autowired
    private CfgBaseService cfgBaseService;
    @Autowired
    private CfgUploadLocalService cfgUploadLocalService;

    @Autowired
    private IntegrationAuthService integrationAuthService;

    @Autowired
    private TDocumentInfoMapper tDocumentInfoMapper;

    @Autowired
    private Scheduler scheduler;


    @Override
    public IPage<TDocumentInfoDTO> paging(PagingReqDTO<TDocumentInfoDTO> reqDTO) {

        IPage<TDocumentInfoDTO> pageData = baseMapper.paging(reqDTO.toPage(), reqDTO.getParams());

        return pageData;
    }

    /**
     * 分页查询
     * @param reqDTO
     * @return
     */
    @Override
    public IPage<TDocumentInfoDTO> paging1(PagingDTO<TDocumentInfoDTO> reqDTO) {
        final String jiLiangGuanLi = "1603576853188853762";
//        final String GOVERMENET_FILE = ""
        TDocumentInfoDTO params = reqDTO.getParams();
        // 计量管理单独sql查询
        if (jiLiangGuanLi.equals(params.getDocType())) {
            IPage<TDocumentInfoDTO> pageData = baseMapper.pagingJi(reqDTO.toPage(), params);
            return pageData;
        }
        // 其他所有文档查询
        String userName = reqDTO.getUserName();
        String docType = params.getDocType();
        List<IntegrationAuth> list = integrationAuthService.list(
                new LambdaQueryWrapper<IntegrationAuth>().eq(IntegrationAuth::getDocType, docType).eq(IntegrationAuth::getUserId, userName
                ));
        Set<String> docIds = new HashSet<>();
        ArrayList<String> strings = new ArrayList<>();
        if (list.size() > 0) {
            list.stream().forEach(i -> docIds.add(i.getDocId()));

            docIds.forEach(i -> strings.add(i));

        }
//        if (StrUtil.isNotBlank(params.getTitle())) {
//            for (String integratedDoc : integratedDocs) {
//                if (integratedDoc.equals(params.getDocType())) {
//                    return baseMapper.pageIntegrated(reqDTO.toPage(), params);
//                }
//            }
//        }
        IPage<TDocumentInfoDTO> pageData = baseMapper.paging(reqDTO.toPage(), params);
        if (strings.size() > 0) {
            for (String string : strings) {
                TDocumentInfo byId = this.getById(string);
                TDocumentInfoDTO tDocumentInfoDTO = new TDocumentInfoDTO();
                if (ObjectUtil.isNotEmpty(byId) && StrUtil.isNotBlank(byId.getCreateBy())) {
                    BeanUtils.copyProperties(byId, tDocumentInfoDTO);
                    pageData.getRecords().add(tDocumentInfoDTO);
                    pageData.setTotal(pageData.getTotal()+1);
                }
            }
        }
        return pageData;
    }

    /**
     * todo
     *
     * @param reqDTO
     * @return
     */
    @Override
    public IPage<TDocumentInfoDTO> paging(PagingDTO<TDocumentInfoDTO> reqDTO) {

        IPage<TDocumentInfoDTO> pageData = baseMapper.paging(reqDTO.toPage(), reqDTO.getParams());
        // userId是用户名不是id,存的用户角色名称（ZZDCC）
        String userId = reqDTO.getUserId();
        String userName = reqDTO.getUserName();
        String docType = reqDTO.getParams().getDocType();
        // 拿到用户的部门编号
//        String deptCode = reqDTO.getDeptCode();
        Order order = reqDTO.getOrder();
        long count = Arrays.stream(integratedDocs).filter(s -> s.equals(docType)).count();
        long docSupCount = Arrays.stream(docSupRecJi).filter(s -> s.equals(docType)).count();
        // 查询的是否是一体化文件类型的文档
        if (count > 0 && StringUtils.isNotBlank(userId)) {


            List<IntegrationAuth> list = new ArrayList<>();
            // 访问的是一体化文件中的支持文件或记录文件或技术文件
            if (docSupCount > 0) {
                // 当前菜单中可以访问的文件
                list = integrationAuthService.list(new LambdaQueryWrapper<IntegrationAuth>()
                        .eq(IntegrationAuth::getUserId, userName)
                        .eq(IntegrationAuth::getDocType, docType)
                );
            }
            if (userId.equals("zz") || userId.equals("zzDCC")) {
                // 过滤出当前用户可以访问的文件
                List<TDocumentInfoDTO> collect = pageData.getRecords().stream().filter(record -> "1".equals(record.getOneType()) || "2".equals(record.getOneType())).collect(Collectors.toList());

                pageData.setRecords(newAuthDocCollect(list, pageData, collect));
                return pageData;
            } else if (userId.equals("jh") || userId.equals("jhDCC")) {
                List<TDocumentInfoDTO> collect = pageData.getRecords().stream().filter(record -> "1".equals(record.getOneType()) || "3".equals(record.getOneType())).collect(Collectors.toList());
//                    pageData.setRecords(collect);
                pageData.setRecords(newAuthDocCollect(list, pageData, collect));
                return pageData;
            } else if (userId.equals("mk") || userId.equals("mkDCC")) {
                List<TDocumentInfoDTO> collect = pageData.getRecords().stream().filter(record -> "1".equals(record.getOneType()) || "5".equals(record.getOneType())).collect(Collectors.toList());
//                    pageData.setRecords(collect);
                pageData.setRecords(newAuthDocCollect(list, pageData, collect));
                return pageData;

            } else if (userId.equals("yxkj") || userId.equals("yxkjDCC")) {
                // 只能看yxkj文件
                List<TDocumentInfoDTO> collect = pageData.getRecords().stream().filter(record -> "1".equals(record.getOneType()) || "6".equals(record.getOneType())).collect(Collectors.toList());
//                    pageData.setRecords(collect);
                pageData.setRecords(newAuthDocCollect(list, pageData, collect));
                return pageData;
            }
            // 如果角色是职能部门，只能看通用文件
            else if (role.contains(userId)) {
                // 是职能部门
                List<TDocumentInfoDTO> collect = pageData.getRecords().stream().filter(record -> "1".equals(record.getOneType())).collect(Collectors.toList());
//                    pageData.setRecords(collect);
                pageData.setRecords(newAuthDocCollect(list, pageData, collect));
                return pageData;
            }

        }

        if (order != null) {
            pageData = sort(order, pageData);
        } else {
            List<TDocumentInfoDTO> collect = pageData.getRecords().stream().filter(r -> r.getFileCode() != null).sorted(Comparator.comparing(TDocumentInfoDTO::getFileCode)).collect(Collectors.toList());
            List<TDocumentInfoDTO> collectNull = pageData.getRecords().stream().filter(r -> r.getFileCode() == null).collect(Collectors.toList());
            if (collectNull.size() > 0) {
                for (int i = 0; i < collectNull.size(); i++) {
                    collect.add(collectNull.get(i));
                }
            }

            pageData.setRecords(collect);
        }
        return pageData;
    }


    public List newAuthDocCollect(List<IntegrationAuth> list, IPage<TDocumentInfoDTO> pageData, List<TDocumentInfoDTO> collect) {
        if (list.size() > 0) {
            for (IntegrationAuth integrationAuth : list) {
                for (TDocumentInfoDTO record : pageData.getRecords()) {
                    if (record.getId().equals(integrationAuth.getDocId())) {
                        // 把可以访问的文件添加到集合中
                        collect.add(record);
                        break;
                    }
                }
            }
        }
        return collect;
    }

    /**
     * 排序
     *
     * @param order
     * @param pageData
     * @return
     */
    public IPage sort(Order order, IPage pageData) {
        String columnName = order.getColumnName().trim();
        String sortType = order.getSortType().trim();
        List<TDocumentInfoDTO> records = pageData.getRecords();
        if ("[\"createTime\"]".equals(columnName)) {
            if ("asc".equals(sortType)) {
                List<TDocumentInfoDTO> collect = records.stream().filter(o -> o.getCreateTime() != null)
                        .sorted(Comparator.comparing((TDocumentInfoDTO::getCreateTime), Comparator.reverseOrder())).collect(Collectors.toList());
                List<TDocumentInfoDTO> collectNull = records.stream().filter(o -> o.getCreateTime() == null).collect(Collectors.toList());
                mergeList(collect, collectNull);
                pageData.setRecords(collect);
            } else {
                List<TDocumentInfoDTO> collect = records.stream().filter(o -> o.getCreateTime() != null)
                        .sorted(Comparator.comparing(s -> s.getCreateTime().getTime())).collect(Collectors.toList());
                List<TDocumentInfoDTO> collectNull = records.stream().filter(o -> o.getCreateTime() == null).collect(Collectors.toList());
                mergeList(collect, collectNull);
                pageData.setRecords(collect);
            }
        } else if ("[\"expireTime\"]".equals(columnName)) {
            if ("asc".equals(sortType)) {
                List<TDocumentInfoDTO> collect = records.stream().filter(o -> o.getCreateTime() != null)
                        .sorted(Comparator.comparing((TDocumentInfoDTO::getExpireTime), Comparator.reverseOrder())).collect(Collectors.toList());
                List<TDocumentInfoDTO> collectNull = records.stream().filter(o -> o.getExpireTime() == null).collect(Collectors.toList());
                mergeList(collect, collectNull);
                pageData.setRecords(collect);
            } else {
                List<TDocumentInfoDTO> collect = records.stream().filter(o -> o.getExpireTime() != null)
                        .sorted(Comparator.comparing(s -> s.getExpireTime().getTime())).collect(Collectors.toList());
                List<TDocumentInfoDTO> collectNull = records.stream().filter(o -> o.getExpireTime() == null).collect(Collectors.toList());
                mergeList(collect, collectNull);
                pageData.setRecords(collect);
            }
// effectiveDate
        } else if ("[\"effectiveDate\"]".equals(columnName)) {
            if ("asc".equals(sortType)) {
                List<TDocumentInfoDTO> collect = records.stream().filter(o -> o.getEffectiveDate() != null)
                        .sorted(Comparator.comparing(TDocumentInfoDTO::getEffectiveDate, Comparator.reverseOrder())).collect(Collectors.toList());
                List<TDocumentInfoDTO> collectNull = records.stream().filter(o -> o.getEffectiveDate() == null).collect(Collectors.toList());
                mergeList(collect, collectNull);
                pageData.setRecords(collect);
            } else {
                List<TDocumentInfoDTO> collect = records.stream().filter(o -> o.getEffectiveDate() != null)
                        .sorted(Comparator.comparing(s -> s.getEffectiveDate().getTime())).collect(Collectors.toList());
                List<TDocumentInfoDTO> collectNull = records.stream().filter(o -> o.getEffectiveDate() == null).collect(Collectors.toList());
                mergeList(collect, collectNull);
                pageData.setRecords(collect);
            }
        }

        return pageData;
    }

    public List mergeList(List collect, List<TDocumentInfoDTO> collectNull) {
        if (collect != null) {
            if (collectNull != null) {
                for (TDocumentInfoDTO t : collectNull) {
                    collect.add(t);
                }
            }
        }
        return collect;
    }

    @Override
    @Transactional
    public void save(TDocumentInfoDTO reqDTO) {
        //复制参数
        TDocumentInfo entity = new TDocumentInfo();
        BeanMapper.copy(reqDTO, entity);

        if (entity.getFileUrl() != null) {
            File file = new File(entity.getFileUrl());
            String name = file.getName();
            String fileFormat = name.substring(name.lastIndexOf(".") + 1, name.length());
            entity.setFileFormat(fileFormat);
        }

        LambdaQueryWrapper<TDocumentInfo> queryWrapper = new LambdaQueryWrapper<>();
        // id是否存在
        String id = entity.getId();
        if (StringUtils.isEmpty(id)) {
            // id不存在
            if (entity.getFileCode() != null) {
                String fileCode = entity.getFileCode();
                queryWrapper.eq(TDocumentInfo::getFileCode, fileCode);
                TDocumentInfo one = getOne(queryWrapper);
                // 文件编号是否重复
                if (one != null) {
                    // 文件编号重复
                    throw new ServiceException("文件编号已存在");
                }
            }


        }
        // id 存在
        if (entity.getReminderTime() != null) {
            // 提醒时间是否大于今天
            if (QuartzUtils.isBefore(entity.getReminderTime())) {
                throw new ServiceException("不能设置过去的时间");
            }
            // 提醒时间是否是今天
            Boolean today = QuartzUtils.isToday(entity.getReminderTime());
            if (today) {
                // 是今天提醒
                try {
                    // todo 创建定时任务
                    String docType = entity.getDocType();
                    String jobName = "title:" + entity.getTitle() + ",docType:" + docType + new Date().getTime();
                    JobDetail jobDetail = null;
                    // 供应商资质
                    if (docType.equals(QuartzConstant.SUPPLIER_DOC_TYPE)) {
                        jobDetail = JobBuilder.newJob(EmailTask.class)
                                .withIdentity(jobName, QuartzConstant.TODAY)
                                .usingJobData(QuartzConstant.DOC_TYPE, docType)
                                .usingJobData(QuartzConstant.CUSTOMER_NAME, entity.getCustomerName())
                                .usingJobData(QuartzConstant.FILE_NAME, entity.getTitle())
                                .build();
                    }
                    if (docType.equals(QuartzConstant.MEA_MANAGEMENT_DOC_TYPE)) {
                        jobDetail = JobBuilder.newJob(EmailTask.class)
                                .withIdentity(jobName, QuartzConstant.TODAY)
                                .usingJobData(QuartzConstant.DOC_TYPE, docType)
                                .usingJobData(QuartzConstant.MEA_MANAGEMENT_EQUIP_NUM, entity.getEquipNum())
                                .usingJobData(QuartzConstant.MEA_MANAGEMENT_EQUIP_NAME, entity.getEquipName())
                                .build();
                    }
                    if (docType.equals(QuartzConstant.ENVIRONMENT_REPORT_DOC_TYPE) || docType.equals(QuartzConstant.MSDS_DOC_TYPE))
                        jobDetail = JobBuilder.newJob(EmailTask.class)
                                .withIdentity(jobName, QuartzConstant.TODAY)
                                .usingJobData(QuartzConstant.DOC_TYPE, docType)
                                .usingJobData(QuartzConstant.FILE_NAME, entity.getTitle())
                                .build();
                    else {
                        jobDetail = JobBuilder.newJob(EmailTask.class)
                                .withIdentity(jobName, QuartzConstant.TODAY)
                                .usingJobData(QuartzConstant.DOC_TYPE, docType)
                                .usingJobData(QuartzConstant.FILE_CODE, entity.getFileCode())
                                .usingJobData(QuartzConstant.FILE_NAME, entity.getTitle())
                                .build();
                    }

                    CronTrigger trigger = TriggerBuilder.newTrigger()
                            .withIdentity(jobName, QuartzConstant.TODAY)
                            .withSchedule(CronScheduleBuilder.cronSchedule(QuartzUtils.getCron(entity.getReminderTime())))
                            .build();
                    scheduler.scheduleJob(jobDetail, trigger);
                    entity.setIsSend(1);
                } catch (SchedulerException e) {
                    log.error("创建定时任务出错：" + e.getMessage());
                    throw new ServiceException("发生错误");
                }
            }
        }

        // 保存或修改
        if (entity.getCreateTime()==null) {
            entity.setCreateTime(new Date());
        }
        if (entity.getDocType()=="1603576853188853762"&&entity.getId()!=null) {
            updateById(entity);
        }
        saveOrUpdate(entity);



        // 转换文档
//        try {
//            this.convert(entity);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new ServiceException("在转码时出现问题：" + e.getMessage());
//        }

    }

    @Override
    public void updateStatus(TDocumentInfoDTO reqDTO) {
        TDocumentInfo entity = new TDocumentInfo();
        BeanMapper.copy(reqDTO, entity);
        baseMapper.updateById(entity);
//        baseMapper.updateStatus(entity);
    }

    @Override
    public void delete(List<String> ids) {
        //批量删除
        this.removeByIds(ids);
    }

    @Override
    public TDocumentInfoDTO detail(String id) {
        TDocumentInfo entity = this.getById(id);
        TDocumentInfoDTO dto = new TDocumentInfoDTO();
        BeanMapper.copy(entity, dto);
        return dto;
    }

    /**
     * 分页查询
     *
     * @param reqDTO
     * @return
     */
    @Override
    public List<TDocumentInfoDTO> list(TDocumentInfoDTO reqDTO) {
        log.error(reqDTO.toString());

        //分页查询并转换
        QueryWrapper<TDocumentInfo> wrapper = new QueryWrapper<>();

        //转换并返回
        List<TDocumentInfo> list = this.list(wrapper);

        //转换数据
        List<TDocumentInfoDTO> dtoList = BeanMapper.mapList(list, TDocumentInfoDTO.class);

        return dtoList;
    }

    @Override
    public IPage<TDocumentInfoDTO> sortAsc(int current,int size,String docType) {
        IPage<TDocumentInfoDTO> page = baseMapper.sortAsc(current, size, docType);
        return page;
    }


    /**
     * 转换Office文档为pdf格式
     *
     * @param entity
     * @throws Exception
     */
    private void convert(TDocumentInfo entity) throws Exception {

        // 配置信息
        CfgBaseDTO cfg = cfgBaseService.findSimple();

        // 只转换Office类型
        String fileType = entity.getFileType();

        // Office类型文档转换
        this.convertDoc(cfg.getUploadType(), entity);
        return;

    }

    /**
     * 转换文档类型数据
     *
     * @param uploadType
     * @param entity
     * @throws ClientException
     */
    private void convertDoc(Integer uploadType, TDocumentInfo entity) throws ClientException, IOException {

        // 本地上传
        if (UploadType.LOCAL.equals(uploadType)) {
            CfgUploadLocalDTO conf = cfgUploadLocalService.find();
            // 相对文件路径
            String url = entity.getFileUrl();
            // 变成物理路径
            String path = url.replace(conf.getUrl(), conf.getLocalDir());
            // 目标地址
            String dist = path + ".pdf";
            // 返回物理路径
            String rest = officeService.convert(path, dist);
            // 再变回访问路径
            String result = rest.replace(conf.getLocalDir(), conf.getUrl());
            entity.setViewUrl(result);
            this.updateById(entity);
            return;
        }
    }

}

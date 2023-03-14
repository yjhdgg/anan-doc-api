package com.anan.document.modules.doc.controller;

import cn.hutool.json.JSONUtil;
import com.anan.document.modules.constant.QuartzConstant;
import com.anan.document.modules.doc.entity.*;
import com.anan.document.modules.doc.mapper.TestMapper;
import com.anan.document.modules.doc.service.IntegrationAuthService;
import com.anan.document.modules.doc.service.TestService;
import com.anan.document.modules.sys.user.dto.request.SysUserQueryReqDTO;
import com.anan.document.modules.sys.user.entity.SysUser;
import com.anan.document.modules.sys.user.entity.SysUserRole;
import com.anan.document.modules.sys.user.mapper.SysUserRoleMapper;
import com.anan.document.modules.sys.user.service.SysUserService;
import com.anan.document.modules.user.UserUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yf.boot.base.api.api.ApiRest;
import com.yf.boot.base.api.api.controller.BaseController;
import com.yf.boot.base.api.api.dto.BaseIdReqDTO;
import com.yf.boot.base.api.api.dto.BaseIdsReqDTO;
import com.yf.boot.base.api.api.dto.PagingReqDTO;
import com.yf.boot.base.api.exception.ServiceException;
import com.yf.boot.base.api.utils.file.StringUtils;
import com.anan.document.modules.doc.dto.TDocumentInfoDTO;
import com.yf.document.modules.doc.entity.*;
import com.anan.document.modules.doc.service.TDocumentInfoService;
import com.anan.document.utils.QuartzUtils;
import com.yf.document1.modules.doc.entity.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;

/**
 * <p>
 * 文档信息表控制器
 * </p>
 *
 * @author Panjp
 * @since 2021-08-23 20:30
 */
@Api(tags = {"文档信息表"})
@RestController
@RequestMapping("/api/doc/doc")
@Slf4j
public class TDocumentInfoController extends BaseController {

    @Autowired
    private TDocumentInfoService baseService;


    @Autowired
    private IntegrationAuthService integrationAuthService;


    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    Scheduler scheduler;

    @Autowired
    TestMapper testMapper;

    @Autowired
    TestService testService;

    @Scheduled(cron = "0 0 0 1/1 * ? ")
    public void init() {

        LambdaQueryWrapper<TDocumentInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.between(TDocumentInfo::getReminderTime, LocalDate.now().atTime(0, 0, 0), LocalDate.now().atTime(23, 59, 59));
        wrapper.eq(TDocumentInfo::getIsSend, 0);
        List<TDocumentInfo> list = baseService.list(wrapper);
        log.info("正在查询" + new Date());
        if (list.size() > 0) {

            for (TDocumentInfo entity : list) {
                try {
                    log.info("mybatis");
                    String docType = entity.getDocType();
                    String jobName = "title:" + entity.getTitle() + ",docType:" + docType + new Date().getTime();
                    log.info("正在创建定时任务：" + jobName);
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
                    //计量管理
                    if (docType.equals(QuartzConstant.MEA_MANAGEMENT_DOC_TYPE)) {
                        jobDetail = JobBuilder.newJob(EmailTask.class)
                                .withIdentity(jobName, QuartzConstant.TODAY)
                                .usingJobData(QuartzConstant.DOC_TYPE, docType)
                                .usingJobData(QuartzConstant.MEA_MANAGEMENT_EQUIP_NUM, entity.getEquipNum())
                                .usingJobData(QuartzConstant.MEA_MANAGEMENT_EQUIP_NAME, entity.getEquipName())
                                .build();
                    }
                    // 环境物质报告
                    if (docType.equals(QuartzConstant.ENVIRONMENT_REPORT_DOC_TYPE) || docType.equals(QuartzConstant.MSDS_DOC_TYPE)) {
                        jobDetail = JobBuilder.newJob(EmailTask.class)
                                .withIdentity(jobName, QuartzConstant.TODAY)
                                .usingJobData(QuartzConstant.DOC_TYPE, docType)
                                .usingJobData(QuartzConstant.FILE_NAME, entity.getTitle())
                                .build();
                    }
                    // MSDS
                    if (docType.equals(QuartzConstant.MSDS_DOC_TYPE)){

                    }
                    // 临时管理文件、变更通知、技术文件
                    else {
                        String oneType = entity.getOneType();
                      if (oneType==null) {
                          return;
                      }
                        if ("2".equals(oneType)){
                            // 发给载带厂
                            jobDetail = JobBuilder.newJob(EmailTask.class)
                                    .withIdentity(jobName, QuartzConstant.TODAY)
                                    .usingJobData(QuartzConstant.DOC_TYPE, docType)
                                    .usingJobData(QuartzConstant.FILE_NAME, entity.getTitle())
                                    .usingJobData(QuartzConstant.ONETYPE,entity.getOneType())
                                    .build();
                        }
                        if ("3".equals(oneType)){
                            // 发给eSIM
                            jobDetail = JobBuilder.newJob(EmailTask.class)
                                    .withIdentity(jobName, QuartzConstant.TODAY)
                                    .usingJobData(QuartzConstant.DOC_TYPE, docType)
                                    .usingJobData(QuartzConstant.FILE_NAME, entity.getTitle())
                                    .usingJobData(QuartzConstant.ONETYPE,entity.getOneType())
                                    .build();
                        }
                        if ("5".equals(oneType)){
                            // 发给模块厂
                            jobDetail = JobBuilder.newJob(EmailTask.class)
                                    .withIdentity(jobName, QuartzConstant.TODAY)
                                    .usingJobData(QuartzConstant.DOC_TYPE, docType)
                                    .usingJobData(QuartzConstant.FILE_NAME, entity.getTitle())
                                    .usingJobData(QuartzConstant.ONETYPE,entity.getOneType())
                                    .build();
                        }
                        if ("6".equals(oneType)){
                            // 发给引线框架
                            jobDetail = JobBuilder.newJob(EmailTask.class)
                                    .withIdentity(jobName, QuartzConstant.TODAY)
                                    .usingJobData(QuartzConstant.DOC_TYPE, docType)
                                    .usingJobData(QuartzConstant.FILE_NAME, entity.getTitle())
                                    .usingJobData(QuartzConstant.ONETYPE,entity.getOneType())
                                    .build();
                        }
//                        jobDetail = JobBuilder.newJob(EmailTask.class)
//                                .withIdentity(jobName, QuartzConstant.TODAY)
//                                .usingJobData(QuartzConstant.DOC_TYPE, docType)
//                                .usingJobData(QuartzConstant.FILE_CODE, entity.getFileCode())
//                                .usingJobData(QuartzConstant.FILE_NAME, entity.getTitle())
//                                .build();
                    }

                    CronTrigger trigger = TriggerBuilder.newTrigger()
                            .withIdentity(jobName, QuartzConstant.TODAY)
                            .withSchedule(CronScheduleBuilder.cronSchedule(QuartzUtils.getCron(entity.getReminderTime())))
                            .build();
                    scheduler.scheduleJob(jobDetail, trigger);
                    // 已经发送过了
                    entity.setIsSend(1);
                    baseService.updateById(entity);
                } catch (SchedulerException e) {
                    log.error("创建定时任务出错：" + e.getMessage());
                    throw new ServiceException("发生错误");
                }
            }
        }
    }


    @PostMapping("/saveTest")
    public ApiRest saveTestData(@RequestBody Test test) {
        boolean b = testService.saveOrUpdate(test);
        if (b) {
            return super.success();
        }
        return super.failure();
    }

    @GetMapping("/getTestData")
    public void getTestData(PagingDTO<TDocumentInfoDTO> reqDTO, HttpServletResponse response) throws IOException {
//        Page<Test> testPage = testMapper.myPage(reqDTO.getCurrent() * reqDTO.getSize(), reqDTO.getSize());
        IPage<Test> testPageInfo = new Page<>();
        testPageInfo.setCurrent(reqDTO.getCurrent());
        testPageInfo.setSize(reqDTO.getSize());
        IPage<Test> testPage = testService.page(testPageInfo);
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();
        Result result = new Result();
        result.setCode(200);
        result.setData(testPage);
        outputStream.write(JSONUtil.toJsonStr(testPage).getBytes());
    }

    @GetMapping("/getAuth")
    public ApiRest getAuth(@RequestParam("id") String id, @RequestParam("docType") String docType) {
        // id 是docId
        LambdaQueryWrapper<IntegrationAuth> eq = new LambdaQueryWrapper<IntegrationAuth>();
        eq.eq(IntegrationAuth::getDocId, id);
        eq.eq(IntegrationAuth::getDocType, docType);
        List<IntegrationAuth> list = integrationAuthService.list(eq);
        return super.success(list);
    }

    @GetMapping("/users")
    @ApiOperation(value = "查询所有用户和用户角色")
    public ApiRest getUserPage(PagingReqDTO<SysUserQueryReqDTO> reqDTO) {
        IPage<SysUser> userPage = sysUserService.getUserPage(reqDTO);
        ArrayList<UserRole> userRoles = new ArrayList<>();
        for (SysUser record : userPage.getRecords()) {
            SysUserRole sysUserRole = sysUserRoleMapper.selectOne(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, record.getId()));
            // 部门编码存的roleId
            record.setDeptCode(sysUserRole.getRoleId());
        }


//        List<IntegrationAuth> list = integrationAuthService.list(new LambdaQueryWrapper<IntegrationAuth>().
//                eq(IntegrationAuth::getDocId,reqDTO.getUserId()));
//        return super.success(Arrays.asList(userPage, list));
        return super.success(userPage);
    }

    /**
     * 保存一体化文件
     *
     * @param integrationAuthList
     * @return
     */
    @PostMapping("/auth")
    @ApiOperation(value = "一体化文件权限")
    public ApiRest setIntegrationAuth(@RequestBody ArrayList<IntegrationAuth> integrationAuthList) {
        LambdaQueryWrapper<IntegrationAuth> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        for (IntegrationAuth integrationAuth : integrationAuthList) {
            lambdaQueryWrapper.eq(IntegrationAuth::getUserId, integrationAuth.getUserId());
            lambdaQueryWrapper.eq(IntegrationAuth::getDocType, integrationAuth.getDocType());
            lambdaQueryWrapper.eq(IntegrationAuth::getDocId, integrationAuth.getDocId());
            IntegrationAuth one = integrationAuthService.getOne(lambdaQueryWrapper);
            if (ObjectUtils.isEmpty(one)) {
                integrationAuthService.saveOrUpdate(integrationAuth);
            }
            lambdaQueryWrapper.clear();
        }
        return super.success();
       /* if (integrationAuth.getIsView()==1){
            if (StringUtils.isBlank(integrationAuth.getUserId())) {
                integrationAuthService.remove(new LambdaQueryWrapper<IntegrationAuth>().
                        eq(IntegrationAuth::getDocId, integrationAuth.getDocId()).eq(IntegrationAuth::getDocType,integrationAuth.getDocType()));
                return super.success();
            }
            integrationAuthService.saveOrUpdate(integrationAuth, new LambdaQueryWrapper<IntegrationAuth>().
                    eq(IntegrationAuth::getDocId, integrationAuth.getDocId()).
                    eq(IntegrationAuth::getUserId, integrationAuth.getUserId())
                    .eq(IntegrationAuth::getDocType, integrationAuth.getDocType())
            );
            return super.success();
        }else if (integrationAuth.getIsView()==0){
            integrationAuthService.remove(new LambdaQueryWrapper<IntegrationAuth>().eq(IntegrationAuth::getUserId,integrationAuth.getUserId()).
                    eq(IntegrationAuth::getDocId,integrationAuth.getDocId())
                    .eq(IntegrationAuth::getDocType,integrationAuth.getDocType())
            );
            return super.success();
        }
        return super.failure();*/

    }

    @PostMapping("/deleteAuth")
    public ApiRest delIntegrationAuth(@RequestBody IntegrationAuth integration) {
        if (ObjectUtils.isEmpty(integration)) {
            return super.failure("发生错误");
        }
        LambdaQueryWrapper<IntegrationAuth> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(IntegrationAuth::getUserId, integration.getUserId());
        lambdaQueryWrapper.eq(IntegrationAuth::getDocType, integration.getDocType());
        lambdaQueryWrapper.eq(IntegrationAuth::getDocId, integration.getDocId());
        boolean remove = integrationAuthService.remove(lambdaQueryWrapper);
        if (remove) {
            return super.success();
        } else
            return super.failure("此用户未授权访问");

    }

    @GetMapping("/auth")
    @ApiOperation(value = "获取一体化文件权限")
    public ApiRest getIntegrationAuth() {
        List<IntegrationAuth> list = integrationAuthService.list();
        return super.success(list);
    }


    @GetMapping("/fileCode")
    @ApiOperation(value = "根据文件编码获取文档信息")
    public ApiRest getByFileCode(@RequestParam("fileCode") String fileCode) {
        System.out.println(fileCode + ":fileCode123");

        if (fileCode != null) {
            TDocumentInfo one = baseService.getOne(new LambdaQueryWrapper<TDocumentInfo>().eq(TDocumentInfo::getFileCode, fileCode));
            if (one != null) {
                return super.success("文件编号已存在");
            }
        }
        return super.success();
    }


    /**
     * 添加或修改
     *
     * @param reqDTO
     * @return
     */
    @ApiOperation(value = "添加或修改")
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public ApiRest save(@RequestBody TDocumentInfoDTO reqDTO) {
        baseService.save(reqDTO);
        return super.success();
    }

    /**
     * 提交审核
     *
     * @param reqDTO
     * @return
     */
    @ApiOperation(value = "提交审核")
    @RequestMapping(value = "/submiReview", method = {RequestMethod.POST})
    public ApiRest submiReview(@RequestBody TDocumentInfoDTO reqDTO) {
        reqDTO = baseService.detail(reqDTO.getId());
        reqDTO.setStatus("2");
        baseService.updateStatus(reqDTO);
        return super.success();
    }

    /**
     * 审核通过
     *
     * @param reqDTO
     * @return
     */
    @ApiOperation(value = "审核通过")
    @RequestMapping(value = "/approved", method = {RequestMethod.POST})
    public ApiRest approved(@RequestBody TDocumentInfoDTO reqDTO) {
        reqDTO = baseService.detail(reqDTO.getId());
        reqDTO.setState(1);


        reqDTO.setEffectiveDate(new Timestamp(new Date().getTime()));
        baseService.updateStatus(reqDTO);
        return super.success();
    }

    /**
     * 审核不通过
     *
     * @param reqDTO
     * @return
     */
    @ApiOperation(value = "审核不通过")
    @RequestMapping(value = "/approvedFailed", method = {RequestMethod.POST})
    public ApiRest approvedFailed(@RequestBody TDocumentInfoDTO reqDTO) {
        reqDTO = baseService.detail(reqDTO.getId());
        reqDTO.setState(0);
        reqDTO.setEffectiveDate(new Timestamp(new Date().getTime()));
        baseService.updateStatus(reqDTO);
        return super.success();
    }

    /**
     * 批量删除
     *
     * @param reqDTO
     * @return
     */
    @ApiOperation(value = "批量删除")
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public ApiRest delete(@RequestBody BaseIdsReqDTO reqDTO) {
        //根据ID删除
        baseService.delete(reqDTO.getIds());
        return super.success();
    }

    /**
     * 查找详情
     *
     * @param reqDTO
     * @return
     */
    @ApiOperation(value = "查找详情")
    @RequestMapping(value = "/detail", method = {RequestMethod.POST})
    public ApiRest<TDocumentInfoDTO> detail(@RequestBody BaseIdReqDTO reqDTO) {
        TDocumentInfoDTO dto = baseService.detail(reqDTO.getId());
        return super.success(dto);
    }

    @RequestMapping(value = "/queryAll", method = {RequestMethod.POST})
    public ApiRest integrationQuery(@RequestBody PagingDTO<TDocumentInfoDTO> reqDTO) {
        // select * from tb where doc_type in
        Page<TDocumentInfo> page = new Page<>(reqDTO.getCurrent(), reqDTO.getSize());
        LambdaQueryWrapper<TDocumentInfo> query = new LambdaQueryWrapper<>();
        query.and(wrapper -> wrapper
                .eq(TDocumentInfo::getDocType, "1603246034305957890")
                .or()
                .eq(TDocumentInfo::getDocType, "1603246056913256450")
                .or()
                .eq(TDocumentInfo::getDocType, "1603555653465210881")
                .or()
                .eq(TDocumentInfo::getDocType, "1603246085677793281")
                .or()
                .eq(TDocumentInfo::getDocType,"1603246114459107329")
                .or()
                .eq(TDocumentInfo::getDocType, "1603246134180724737"));

        String zhiNengDepart = "ssyx,swcg,swfw,xm,yfzx,zhgl,pzgl,rlzy,ahb,ccwl,cw";
        String fenChang = "zzDCC,zz,jhDCC,jh,mkDCC,mk,mkDCC,yxkjDCC,yxkj";
        String userName = reqDTO.getUserId();

        if (userName != null) {
            TDocumentInfoDTO params = reqDTO.getParams();
            String title = params.getTitle();
            query.and(wrapper ->wrapper.like(TDocumentInfo::getTitle, title).or().like(TDocumentInfo::getFileCode,title));

//            params.setOneType("1");
            // 职能部门
            if (zhiNengDepart.contains(userName)) {

                baseService.page(page, query);
                return super.success(page);
            }
            // 是分厂用户
            if (fenChang.contains(userName)) {

                if ("zzDCC".equals(userName) || "zz".equals(userName)) {
                    params.setOneType("2");

                }
                if (userName.equals("jh") || userName.equals("jhDCC")) {
                    params.setOneType("3");
                }
                if (userName.equals("mk") || userName.equals("mkDCC")) {
                    params.setOneType("5");
                }
                if (userName.equals("yxkj") || userName.equals("yxkjDCC")) {
                    params.setOneType("6");
                }
                query.and(wrapper ->
                        wrapper.eq(TDocumentInfo::getOneType,params.getOneType())
                                .or().eq(TDocumentInfo::getOneType,"1")
                );
                baseService.page(page, query);
                return super.success(page);
            }
        }



            baseService.page(page, query);

        return super.success(page);
    }

    /**
     * 分页查找
     *
     * @param reqDTO
     * @return
     */
    @ApiOperation(value = "分页查找")
    @RequestMapping(value = "/paging", method = {RequestMethod.POST})
    public ApiRest<IPage<TDocumentInfoDTO>> paging(@RequestBody PagingDTO<TDocumentInfoDTO> reqDTO, HttpServletResponse response) throws IOException {

        String zhiNengDepart = "ssyx,swcg,swfw,xm,yfzx,zhgl,pzgl,rlzy,ahb,ccwl,cw";
        String nameStr = "zzDCC,zz,jhDCC,jh,mkDCC,mk,mkDCC,yxkjDCC,yxkj";
        String userName = reqDTO.getUserId();


        // 是分厂用户
        if (userName != null) {
            TDocumentInfoDTO params = reqDTO.getParams();
            if (zhiNengDepart.contains(userName)) {
                params.setOneType("1");
            }
            if (nameStr.contains(userName)) {

                if ("zzDCC".equals(userName) || "zz".equals(userName)) {
                    params.setOneType("2");
                }
                if (userName.equals("jh") || userName.equals("jhDCC")) {
                    params.setOneType("3");
                }
                if (userName.equals("mk") || userName.equals("mkDCC")) {
                    params.setOneType("5");
                }
                if (userName.equals("yxkj") || userName.equals("yxkjDCC")) {
                    params.setOneType("6");
                }
            }
        }
        //分页查询并转换
        IPage<TDocumentInfoDTO> page = baseService.paging1(reqDTO);

        return super.success(page);
    }


    /**
     * 前端分页查找
     *
     * @param reqDTO
     * @return
     */
    @ApiOperation(value = "前端分页查找")
    @RequestMapping(value = "/webpaging", method = {RequestMethod.POST})
    public ApiRest<IPage<TDocumentInfoDTO>> webpaging(@RequestBody PagingReqDTO<TDocumentInfoDTO> reqDTO) {
        TDocumentInfoDTO dto = reqDTO.getParams();
        if (null == dto) {
            dto = new TDocumentInfoDTO();
        }
        if (!StringUtils.isBlank(dto.getSortType())) {
            List<OrderItem> lsOrder = new ArrayList<>();
            OrderItem item = new OrderItem();
            item.setAsc(dto.isAsc());
            item.setColumn(" a." + dto.getSortType());
            lsOrder.add(item);
            reqDTO.setOrders(lsOrder);
        }
        dto.setStatus("3");
        //分页查询并转换
        IPage<TDocumentInfoDTO> page = baseService.paging(reqDTO);

        return super.success(page);
    }

    /**
     * 我的资料
     *
     * @param reqDTO
     * @return
     */
    @ApiOperation(value = "分页查找")
    @RequestMapping(value = "/myDocPaging", method = {RequestMethod.POST})
    public ApiRest<IPage<TDocumentInfoDTO>> myDocPaging(@RequestBody PagingReqDTO<TDocumentInfoDTO> reqDTO) {
        TDocumentInfoDTO parm = reqDTO.getParams();
        if (null == parm) {
            parm = new TDocumentInfoDTO();
        }
        parm.setCreateBy(UserUtils.getUserId());
        //分页查询并转换
        IPage<TDocumentInfoDTO> page = baseService.paging(reqDTO);

        return super.success(page);
    }


    /**
     * 查找列表，每次最多返回200条数据
     *
     * @param reqDTO
     * @return
     */
    @ApiOperation(value = "查找列表")
    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    public ApiRest<List<TDocumentInfoDTO>> list(@RequestBody TDocumentInfoDTO reqDTO) {
        // 查找列表
        List<TDocumentInfoDTO> dtoList = baseService.list(reqDTO);
        return super.success(dtoList);
    }

}

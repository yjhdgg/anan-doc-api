package com.anan.document.modules.doc.service;

import com.anan.document.modules.doc.entity.PagingDTO;
import com.anan.document.modules.doc.entity.TDocumentInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yf.boot.base.api.api.dto.PagingReqDTO;
import com.anan.document.modules.doc.dto.TDocumentInfoDTO;

import java.util.List;

/**
* <p>
* 文档信息表业务接口类
* </p>
*
* @author Panjp
* @since 2021-08-23 20:30
*/
public interface TDocumentInfoService extends IService<TDocumentInfo> {


    /**
    * 分页查询数据
    * @param reqDTO
    * @return
    */
    IPage<TDocumentInfoDTO> paging(PagingReqDTO<TDocumentInfoDTO> reqDTO);

    IPage<TDocumentInfoDTO> paging(PagingDTO<TDocumentInfoDTO> reqDTO);
    IPage<TDocumentInfoDTO> paging1(PagingDTO<TDocumentInfoDTO> reqDTO);

    /**
    * 添加或保存
    * @param reqDTO
    * @return
    */
    void save(TDocumentInfoDTO reqDTO);

    /**
     * 添加或保存
     * @param reqDTO
     * @return
     */
    void updateStatus(TDocumentInfoDTO reqDTO);

    /**
    * 批量删除
    * @param ids
    * @return
    */
    void delete(List<String> ids);


    /**
    * 查找详情
    * @param id
    * @return
    */
    TDocumentInfoDTO detail(String id);

    /**
    * 查找列表
    * @param reqDTO
    * @return
    */
    List<TDocumentInfoDTO> list(TDocumentInfoDTO reqDTO);


    IPage<TDocumentInfoDTO> sortAsc(int current,int size,String docType);
}

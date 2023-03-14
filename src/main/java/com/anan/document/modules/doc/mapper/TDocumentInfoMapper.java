package com.anan.document.modules.doc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.anan.document.modules.doc.dto.TDocumentInfoDTO;
import com.anan.document.modules.doc.entity.TDocumentInfo;
import org.apache.ibatis.annotations.Param;

/**
* <p>
* 文档信息表Mapper
* </p>
*
* @author Panjp
* @since 2021-08-23 20:30
*/
public interface TDocumentInfoMapper extends BaseMapper<TDocumentInfo> {
    void updateStatus(TDocumentInfo dto);

    /**
     * 分页查询
     * @param page
     * @param query
     * @return
     */
    IPage<TDocumentInfoDTO> paging(Page page, @Param("query") TDocumentInfoDTO query);
    IPage<TDocumentInfoDTO> pageIntegrated(Page page, @Param("query") TDocumentInfoDTO query);
    IPage<TDocumentInfoDTO> pagingJi(Page page, @Param("query") TDocumentInfoDTO query);


    IPage<TDocumentInfoDTO> sortAsc(@Param("current") int current,@Param("size") int size, @Param("docType") String docType);

//    IPage<TDocumentInfoDTO> queryYiTiHua(@Param(("two")String two,))
}

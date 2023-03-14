package com.anan.document.modules.doc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author anan
 * @Description TODO
 * @Date 2023/3/2 16:23
 * @Version 1.0
 */
@RestController
@RequestMapping("/query")
public class QueryController {
//    public ApiRest queryAll(@RequestBody PagingDTO<TDocumentInfoDTO> reqDTO) {
//        TDocumentInfoDTO params = reqDTO.getParams();
//        String docType = params.getDocType();
//        LambdaQueryWrapper<TDocumentInfo> queryWrapper = new LambdaQueryWrapper<>();
//        String [] yiTiHua = {"1603246034305957890","1603246056913256450","1603555653465210881","1603246085677793281","1603246114459107329","1603246134180724737"};
//        for (String s : yiTiHua) {
//            if (s.equals(docType)) {
//                //一体化文件
//                queryWrapper.and(wrapper -> wrapper
//                        .eq(TDocumentInfo::getDocType, "1603246034305957890")
//                        .or()
//
//                        query.and(wrapper -> wrapper
//                                .eq(TDocumentInfo::getDocType, "1603246034305957890")
//                                .or()
//                                .eq(TDocumentInfo::getDocType, "1603246056913256450")
//                                .or()
//                                .eq(TDocumentInfo::getDocType, "1603555653465210881")
//                                .or()
//                                .eq(TDocumentInfo::getDocType, "1603246085677793281")
//                                .or()
//                                .eq(TDocumentInfo::getDocType,"1603246114459107329")
//                                .or()
//                                .eq(TDocumentInfo::getDocType, "1603246134180724737"));
//                baseService.page(page, query);
//            }
//        }
//        return null;
//    }
}

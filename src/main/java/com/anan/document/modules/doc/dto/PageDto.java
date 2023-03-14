package com.anan.document.modules.doc.dto;

import io.swagger.models.auth.In;
import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

/**
 * @Author anan
 * @Description TODO
 * @Date 2023/2/21 16:33
 * @Version 1.0
 */
@Data
public class PageDto<T> {
    private Integer current;
    private Integer size;

    private String query;

    T params;
}

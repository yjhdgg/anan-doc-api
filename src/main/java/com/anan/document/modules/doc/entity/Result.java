package com.anan.document.modules.doc.entity;

import lombok.Data;

/**
 * @Author anan
 * @Description TODO
 * @Date 2023/2/14 14:31
 * @Version 1.0
 */
@Data
public class Result {
    private Integer code;
    private String msg;
    private Object data;
}

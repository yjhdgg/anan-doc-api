package com.anan.document.modules.doc.entity;

import lombok.Data;

/**
 * @Author anan
 * @Description TODO
 * @Date 2023/1/30 13:58
 * @Version 1.0
 */
@Data
public class UserRole {

    private String id;
    private String roleId;
    private String userName;

    public UserRole (String id, String userName, String roleId) {
       this.id = id;
       this.roleId = roleId;
       this.userName = userName;
    }
}

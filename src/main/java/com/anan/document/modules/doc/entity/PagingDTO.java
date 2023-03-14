package com.anan.document.modules.doc.entity;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;



public class PagingDTO<T> {

    private Order order;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @ApiModelProperty(
            value = "当前页码",
            required = true,
            example = "1"
    )
    private Integer current;
    @ApiModelProperty(
            value = "每页数量",
            required = true,
            example = "10"
    )
    private Integer size;
    @ApiModelProperty("查询参数")
    private T params;
    @ApiModelProperty("排序方式")
    private List<OrderItem> orders;
    @JsonIgnore
    @ApiModelProperty("当前用户的ID")
    private String userId;

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 部门编号
     */
    private String deptCode;

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public Page toPage() {
        Page page = new Page();
        page.setCurrent((long) this.current);
        page.setSize((long) this.size);
        page.setOrders(this.orders);
        return page;
    }

    public PagingDTO() {
    }

    public Integer getCurrent() {
        return this.current;
    }

    public Integer getSize() {
        return this.size;
    }

    public T getParams() {
        return this.params;
    }

    public List<OrderItem> getOrders() {
        return this.orders;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public void setParams(T params) {
        this.params = params;
    }

    public void setOrders(List<OrderItem> orders) {
        this.orders = orders;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }





}

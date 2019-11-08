package me.zhengjie.modules.basic_management.wechat.service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PublicWarehouseDto {
    private  Long id;

    //所需仓库地址
    private String warehouseSite;

    //仓库图片
    private String warehousePhpto;

    //可用面积
    private Long acreage;

    //报价金额
    private BigDecimal offer;

    //客户名称
    private String name;

    //客户电话
    private String phone;
}

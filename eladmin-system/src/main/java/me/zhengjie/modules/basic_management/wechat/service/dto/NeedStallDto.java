package me.zhengjie.modules.basic_management.wechat.service.dto;

import lombok.Data;

import java.util.Date;


@Data
public class NeedStallDto {
    //id
    private  Long id;

    //所需仓库地址
    private String stallArea;

    //需求面积
    private Long acreage;

    //档口用途
    private String stallPurpose;

    //客户发布日期
    private Date requiredDate;

    //客户名称
    private String name;

    //客户电话
    private String phone;
}

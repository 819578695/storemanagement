package me.zhengjie.modules.basic_management.wechat.domain;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

/**
 * 小程序客户发布档口需求
 */
@Entity
@Data
@Table(name="applet_need_stall")
public class NeedStall {
    //id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private  Long id;

    //所需仓库地址
    @Column(name = "stall_area")
    private String stallArea;

    //需求面积
    @Column(name = "acreage")
    private Long acreage;

    //档口用途
    @Column(name = "stall_purpose")
    private String stallPurpose;

    //客户发布日期
    @Column(name = "required_date")
    private Date requiredDate;

    //客户名称
    @Column(name = "name")
    private String name;

    //客户电话
    @Column(name = "phone")
    private String phone;

    //客户唯一标识
    @Column(name = "open_id")
    private String openId;
}

package me.zhengjie.modules.basic_management.wechat.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@Table(name="applet_public_warehouse")
public class PublicWarehouse {
    //id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private  Long id;

    //所需仓库地址
    @Column(name = "warehouse_site")
    private String warehouseSite;

    //仓库图片
    @Column(name = "warehouse_phpto")
    private String warehousePhpto;

    //可用面积
    @Column(name = "acreage")
    private Long acreage;

    //报价金额
    @Column(name = "offer")
    private BigDecimal offer;

    //客户名称
    @Column(name = "name")
    private String name;

    //客户电话
    @Column(name = "phone")
    private String phone;
}

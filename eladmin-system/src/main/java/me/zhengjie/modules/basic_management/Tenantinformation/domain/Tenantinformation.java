package me.zhengjie.modules.basic_management.Tenantinformation.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import java.io.Serializable;

/**
* @author zlk
* @date 2019-08-12
*/
@Entity
@Data
@Table(name="basics_tenement")
public class Tenantinformation implements Serializable {

    // id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // 面积(m²)
    @Column(name = "Area")
    private String area;

    // 档口/电商楼
    @Column(name = "stall")
    private String stall;

    // 房号(门牌号)
    @Column(name = "roomnumber")
    private Long roomnumber;

    // 公司名称
    @Column(name = "companyname")
    private String companyname;

    // 物流专线
    @Column(name = "logisticsline")
    private String logisticsline;

    // 联系人
    @Column(name = "linkman")
    private String linkman;

    // 联系电话
    @Column(name = "phone")
    private String phone;

    // 欠款金额
    @Column(name = "amountinarear")
    private Long amountinarear;

    // 合同详情
    @Column(name = "thecontractdetails")
    private String thecontractdetails;

    // 部门id
    @Column(name = "dept_id")
    private Long dept_id;

    public void copy(Tenantinformation source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
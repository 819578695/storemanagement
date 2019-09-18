package me.zhengjie.modules.basic_management.Tenantinformation.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.domain.Archivesmouthsmanagement;
import me.zhengjie.modules.business.domain.LeaseContract;
import me.zhengjie.modules.system.domain.Dept;
import me.zhengjie.modules.system.domain.DictDetail;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

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
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "stall_type")
    private DictDetail dictDetail;

    // 房号(门牌号)
    @Column(name = "stall_id")
    private Archivesmouthsmanagement Archivesmouthsmanagement;

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

    //关联合同表
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "contract_id")
    private LeaseContract LeaseContract;

    // 部门id
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "dept_id")
    private Dept dept;

    // 创建时间
    @CreationTimestamp
    @Column(name = "tenement_date")
    private Timestamp tenementdate;

    public void copy(Tenantinformation source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}

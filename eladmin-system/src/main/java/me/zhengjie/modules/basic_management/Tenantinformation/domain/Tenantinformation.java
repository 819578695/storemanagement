package me.zhengjie.modules.basic_management.Tenantinformation.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.domain.Archivesmouthsmanagement;
import me.zhengjie.modules.business.domain.LeaseContract;
import me.zhengjie.modules.system.domain.Dept;
import me.zhengjie.modules.system.domain.DictDetail;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
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

    // 房号(门牌号)
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "stall_id")
    @NotFound(action= NotFoundAction.IGNORE)
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
    private BigDecimal amountinarear;

    //关联合同表
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "contract_id")
    @NotFound(action= NotFoundAction.IGNORE)
    private LeaseContract LeaseContract;

    // 部门id
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "dept_id")
    @NotFound(action= NotFoundAction.IGNORE)
    private Dept dept;

    // 创建时间
    @CreationTimestamp
    @Column(name = "tenement_date")
    private Timestamp tenementdate;

    //是否过期
    @Column(name = "past_due")
    private String Pastdue;

    public void copy(Tenantinformation source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}

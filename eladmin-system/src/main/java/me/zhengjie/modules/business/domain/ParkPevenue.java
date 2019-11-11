package me.zhengjie.modules.business.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.domain.Archivesmouthsmanagement;
import me.zhengjie.modules.basic_management.Tenantinformation.domain.Tenantinformation;
import me.zhengjie.modules.basic_management.thearchives.domain.BasicsPark;
import me.zhengjie.modules.system.domain.Dept;
import me.zhengjie.modules.system.domain.DictDetail;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @author kang
* @date 2019-08-23
*/
@Entity
@Data
@Table(name="park_pevenue")
public class ParkPevenue implements Serializable {

    // 主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // 园区id
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "park_id")
    @NotFound(action= NotFoundAction.IGNORE)
    private BasicsPark  basicsPark;


    @OneToOne
    @JoinColumn(name = "receipt_payment_account_id")
    @NotFound(action= NotFoundAction.IGNORE)
    private ReceiptPaymentAccount receiptPaymentAccount;

    /**
     * 部门id
     */
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "dept_id")
    @NotFound(action= NotFoundAction.IGNORE)
    private Dept dept;

    // 档口Id
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "archives_mouths_id")
    @NotFound(action= NotFoundAction.IGNORE)
    private Archivesmouthsmanagement archivesmouthsmanagement;

    // 房租
    @Column(name = "house_rent")
    private BigDecimal houseRent;

    // 物业费
    @Column(name = "property_rent")
    private BigDecimal propertyRent;

    // 水费
    @Column(name = "water_rent")
    private BigDecimal waterRent;

    // 电费
    @Column(name = "electricity_rent")
    private BigDecimal electricityRent;

    // 卫生费
    @Column(name = "sanitation_rent")
    private BigDecimal sanitationRent;

    // 违约金
    @Column(name = "liquidated_rent")
    private BigDecimal liquidatedRent;

    // 滞纳金
    @Column(name = "late_rent")
    private BigDecimal lateRent;

    // 地磅费
    @Column(name = "ground_pound_rent")
    private BigDecimal groundPoundRent;

    // 类型( 已付  欠款  补缴)
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "type")
    @NotFound(action= NotFoundAction.IGNORE)
    private DictDetail payType;

    //管理费
    @Column(name = "management_rent")
    private BigDecimal managementRent;

    //停车费
    @Column(name = "parking_rent")
    private BigDecimal parkingRent;

    // 付款方式(关联)
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "payment_type")
    @NotFound(action= NotFoundAction.IGNORE)
    private DictDetail dictDetail;

    // 合同
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "lease_contract_id")
    @NotFound(action= NotFoundAction.IGNORE)
    private LeaseContract leaseContract;

    // 修改时间

    @Column(name = "update_time")
    private Timestamp updateTime;

    // 创建时间
    @CreationTimestamp
    @Column(name = "create_time")
    private Timestamp createTime;

    // 所缴月份(起止时间)
    @Column(name = "start_time")
    private Timestamp startTime;

    // 所缴月份(截止时间)
    @Column(name = "end_time")
    private Timestamp endTime;

    //收款时间
    @Column(name = "payment_time")
    private Timestamp paymentTime;

    // 租户信息
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "tenement_id")
    @NotFound(action= NotFoundAction.IGNORE)
    private Tenantinformation tenantinformation;

    // 是否删除
    @Column(name = "is_delete")
    private String isDelete;

    // 审核状态
    @Column(name = "is_vertify")
    private int isVertify;

    // 备注
    @Column(name = "pevenue_remarks")
    private String pevenueaRemarks;

    public void copy(ParkPevenue source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}

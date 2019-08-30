package me.zhengjie.modules.business.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.domain.Archivesmouthsmanagement;
import me.zhengjie.modules.basic_management.thearchives.domain.BasicsPark;
import me.zhengjie.modules.system.domain.Dept;
import me.zhengjie.modules.system.domain.DictDetail;
import org.hibernate.annotations.CreationTimestamp;

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
    private BasicsPark  basicsPark;


    @OneToOne
    @JoinColumn(name = "receipt_payment_account_id")
    private ReceiptPaymentAccount receiptPaymentAccount;

    /**
     * 部门id
     */
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "dept_id")
    private Dept dept;

    // 档口Id
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "archives_mouths_id")
    private Archivesmouthsmanagement archivesmouthsmanagement;

    // 房租
    @Column(name = "house_rent")
    private BigDecimal houseRent;

    // 物业费
    @Column(name = "property_rent")
    private BigDecimal propertyRent;

    // 水电费
    @Column(name = "water_electricity_rent")
    private BigDecimal waterElectricityRent;

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

    // 欠款金额
    @Column(name = "arrers_rent")
    private BigDecimal arrersRent;

    //管理费
    @Column(name = "management_rent")
    private BigDecimal managementRent;

    //停车费
    @Column(name = "parking_rent")
    private BigDecimal parkingRent;

    // 付款方式(关联)
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "payment_type")
    private DictDetail dictDetail;

    // 创建时间
    @CreationTimestamp
    @Column(name = "create_time")
    private Timestamp createTime;

    public void copy(ParkPevenue source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}

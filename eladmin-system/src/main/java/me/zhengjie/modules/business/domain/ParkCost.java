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
* @date 2019-08-22
*/
@Entity
@Data
@Table(name="park_cost")
public class ParkCost implements Serializable {

    // 主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // 档口Id
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "archives_mouths_id")
    private Archivesmouthsmanagement archivesmouthsmanagement;
    /**
     * 部门id
     */
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "dept_id")
    private Dept dept;

    // 场地租金
    @Column(name = "site_rent",columnDefinition = "decimal(12,2) default 0")
    private BigDecimal siteRent;

    // 水费
    @Column(name = "water_rent")
    private BigDecimal waterRent;

    // 电费
    @Column(name = "electricity_rent")
    private BigDecimal electricityRent;

    // 物业费
    @Column(name = "property_rent")
    private BigDecimal propertyRent;

    // 税赋成本
    @Column(name = "tax_cost")
    private BigDecimal taxCost;

    // 其他费用
    @Column(name = "other_rent")
    private BigDecimal otherRent;

    // 付款方式(关联)
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "payment_type")
    private DictDetail dictDetail;

    // 创建时间
    @CreationTimestamp
    @Column(name = "create_time")
    private Timestamp createTime;

    // 合同
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "rent_contract_id")
    private RentContract rentContract;

    //收付款信息
    @OneToOne
    @JoinColumn(name = "receipt_payment_account_id")
    private ReceiptPaymentAccount receiptPaymentAccount;

    public void copy(ParkCost source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}

package me.zhengjie.modules.business.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.domain.Archivesmouthsmanagement;
import me.zhengjie.modules.basic_management.Tenantinformation.domain.Tenantinformation;
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
* @author nmk
* @date 2019-08-29
*/
@Entity
@Data
@Table(name="lease_contract")
public class LeaseContract implements Serializable {

    // 主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // 合同编号
    @Column(name = "contract_no")
    private String contractNo;

    // 租户id
    @OneToOne()
    @JoinColumn(name = "tenement_id")
    @NotFound(action= NotFoundAction.IGNORE)
    private Tenantinformation tenantinformation;

    /**
     * 部门id
     */
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "dept_id")
    @NotFound(action= NotFoundAction.IGNORE)
    private Dept dept;

    // 档口Id
    @OneToOne()
    @JoinColumn(name = "stall_id")
    @NotFound(action= NotFoundAction.IGNORE)
    private Archivesmouthsmanagement archivesmouthsmanagement;

    // 合同名称
    @Column(name = "contract_name")
    private String contractName;

    // 起止日期
    @Column(name = "start_date")
    private Timestamp startDate;

    // 截止日期
    @Column(name = "end_date")
    private Timestamp endDate;

    // 免租期起止日期
    @Column(name = "rent_free_start_time")
    private Timestamp rentFreeStartTime;

    // 免租期截止日期
    @Column(name = "rent_free_end_time")
    private Timestamp rentFreeEndTime;

    // 免租期
    @Column(name = "rent_free_period")
    private Long rentFreePeriod;

    // 保证金
    @Column(name = "deposit")
    private BigDecimal deposit;

    // 未缴费用
    @Column(name = "unpaid_expenses")
    private BigDecimal unpaidExpenses;

    // 已缴费用
    @Column(name = "paymented_expenses")
    private BigDecimal paymentedExpenses;

    // 合同总金额
    @Column(name = "contract_amount")
    private BigDecimal contractAmount;

    // 文件名
    @Column(name = "file_name")
    private String fileName;

    // 备注
    @Column(name = "remarks")
    private String remarks;

    // 合同支付周期
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "pay_cycle")
    @NotFound(action= NotFoundAction.IGNORE)
    private DictDetail payCycle;

    // 合同支付单价
    @Column(name = "pay_price")
    private BigDecimal payPrice;

    // 合同支付单价
    @Column(name = "is_enable")
    private String isEnable;

    //创建时间
    @CreationTimestamp
    @Column(name = "create_time")
    private Timestamp createTime;

    public void copy(LeaseContract source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}

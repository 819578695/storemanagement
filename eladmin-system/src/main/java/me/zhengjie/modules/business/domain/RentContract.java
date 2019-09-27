package me.zhengjie.modules.business.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import me.zhengjie.modules.system.domain.Dept;
import me.zhengjie.modules.system.domain.DictDetail;

import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @author kang
* @date 2019-08-28
*/
@Entity
@Data
@Table(name="rent_contract")
public class RentContract implements Serializable {

    // 主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // 园区id
    @Column(name = "park_id")
    private Long parkId;

    /**
     * 部门id
     */
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "dept_id")
    private Dept dept;

    @Column(name = "contract_name")
    private String contractName;

    // 起止日期
    @Column(name = "start_date")
    private Timestamp startDate;

    // 截止日期
    @Column(name = "end_date")
    private Timestamp endDate;

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

    @Column(name = "contract_amount")
    private BigDecimal contractAmount;

    // 文件名
    @Column(name = "file_name")
    private String fileName;

    // 合同编号
    @Column(name = "contract_no")
    private String contractNo;

    // 合同支付周期
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "pay_cycle")
    private DictDetail payCycle;

    // 合同支付单价
    @Column(name = "pay_price")
    private BigDecimal payPrice;

    public void copy(RentContract source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}

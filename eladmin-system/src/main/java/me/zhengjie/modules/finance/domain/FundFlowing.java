package me.zhengjie.modules.finance.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import me.zhengjie.modules.system.domain.Dept;
import me.zhengjie.modules.system.domain.DictDetail;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @author nmk
* @date 2019-08-22
*/
@Entity
@Data
@Table(name="fund_flowing")
public class FundFlowing implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // 交易日期
    @CreationTimestamp
    @Column(name = "trad_date",nullable = false)
    private Timestamp tradDate;

    // 金额
    @Column(name = "money",nullable = false)
    private BigDecimal money;

//    // 记账类型(房租,停车,地磅,采购,往来款,投资款)
//    @Column(name = "tally_type_id",nullable = false)
//    private Long tallyTypeId;

    // 当前账户余额
    @Column(name = "urrent_balance",nullable = false)
    private BigDecimal urrentBalance;

    // 收付款人名称
    @Column(name = "receipt_payment_name",nullable = false)
    private String receiptPaymentName;

//    // 类型(收入,支出)
//    @Column(name = "type",nullable = false)
//    private Long type;

    // 银行账号
    @Column(name = "back_num")
    private String backNum;

    // 银行户名
    @Column(name = "back_account")
    private String backAccount;

    /**
     * 部门ID
     */
    @OneToOne
    @JoinColumn(name = "dept_id")
    private Dept dept;

    /**
     * 交易类型
     */
    @OneToOne
    @JoinColumn(name = "trad_type_id")
    private DictDetail tradType;

    /**
     * 记账类型
     */
    @OneToOne
    @JoinColumn(name = "tally_type_id")
    private DictDetail tallyType;

    @Column(name = "park_cost_pevenue_id")
    private Long parkCostPevenueId;


    /**
     * 类型
     */
    @OneToOne
    @JoinColumn(name = "type_id")
    private DictDetail typeDict;

    public void copy(FundFlowing source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}

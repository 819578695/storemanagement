package me.zhengjie.modules.finance.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
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
@Table(name="journal_account_of_capital")
public class JournalAccountOfCapital implements Serializable {

    @Id
    @Column(name = "id")
    private Integer id;

    // 交易日期
    @Column(name = "trad_date",nullable = false)
    private Timestamp tradDate;

    // 交易类型
    @Column(name = "trad_type_id",nullable = false)
    private Integer tradTypeId;

    // 金额
    @Column(name = "money",nullable = false)
    private BigDecimal money;

    // 记账类型(房租,停车,地磅,采购,往来款,投资款)
    @Column(name = "tally_type_id",nullable = false)
    private String tallyTypeId;

    // 当前账户余额
    @Column(name = "urrent_balance",nullable = false)
    private BigDecimal urrentBalance;

    // 收付款人名称
    @Column(name = "receipt_payment_name",nullable = false)
    private String receiptPaymentName;

    // 类型(收入,支出)
    @Column(name = "type",nullable = false)
    private Integer type;

    // 银行账号
    @Column(name = "back_num")
    private Integer backNum;

    // 银行户名
    @Column(name = "back_account")
    private String backAccount;

    public void copy(JournalAccountOfCapital source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
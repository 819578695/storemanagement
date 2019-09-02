package me.zhengjie.modules.finance.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;


/**
* @author nmk
* @date 2019-08-22
*/
@Data
public class JournalAccountOfCapitalDTO implements Serializable {

    private Long id;

    // 交易日期
    private Timestamp tradDate;

    // 交易类型
    private Long tradTypeId;

    // 金额
    private BigDecimal money;

    // 记账类型(房租,停车,地磅,采购,往来款,投资款)
    private Long tallyTypeId;

    // 当前账户余额
    private BigDecimal urrentBalance;

    // 收付款人名称
    private String receiptPaymentName;

    // 类型(收入,支出)
    private Long typeId;

    // 银行账号
    private String backNum;

    // 银行户名
    private String backAccount;

    //部门id
    private Long deptId;

    //部门名称
    private String deptName;

    //交易类型label
    private String tradTypeLabel;

    //记账类型
    private String tallyTypeIdLabel;

    //类型
    private String typeLabel;
}

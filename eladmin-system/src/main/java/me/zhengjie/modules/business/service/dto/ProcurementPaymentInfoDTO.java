package me.zhengjie.modules.business.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;


/**
* @author kang
* @date 2019-10-09
*/
@Data
public class ProcurementPaymentInfoDTO implements Serializable {

    // 主键
    private Long id;

    // 对应采购信息id
    private Long procurementId;


    // 实际付款金额
    private BigDecimal actualPaymentAmount;

    // 实际付款日期
    private Timestamp actualPaymentDate;

    // 支付方式
    private Long paymentTypeId;

    // 支付名称
    private String paymentTypeName;

    // 收付款信息
    private Long receiptPaymentAccountId;

    // 收付款信息
    private String receiptPaymentAccountName;

    // 创建时间
    private Timestamp createTime;

    // 付款说明
    private String paymentDescription;

    // 付款比例
    private String paymentRatio;

    // 应付日期
    private Timestamp dueDate;

    // 是否删除
    private String isDelete;
}

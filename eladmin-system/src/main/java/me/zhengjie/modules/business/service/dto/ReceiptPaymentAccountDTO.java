package me.zhengjie.modules.business.service.dto;

import lombok.Data;
import java.io.Serializable;


/**
* @author kang
* @date 2019-08-21
*/
@Data
public class ReceiptPaymentAccountDTO implements Serializable {

    private Long id;

    // 名称
    private String name;

    // 部门id关联
    private Long deptId;

    // 付款账户名称
    private String paymentAccount;

    // 付款账号
    private String paymentAccountNum;

    // 付款开户行
    private String paymentBank;

    // 收款账户名称
    private String receiptAccount;

    // 收款账号
    private String receiptAccountNum;

    // 收款开户行
    private String receiptBank;
}

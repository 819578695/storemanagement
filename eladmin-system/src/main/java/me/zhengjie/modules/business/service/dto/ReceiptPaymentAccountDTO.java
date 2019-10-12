package me.zhengjie.modules.business.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


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
    private Long detailId;

    //余额
    private BigDecimal remaining;

    // 账户名称
    private String accountName;

    // 付款账号
    private String accountNum;

    // 付款开户行
    private String bankName;
}

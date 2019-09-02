package me.zhengjie.modules.business.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import java.io.Serializable;

/**
* @author kang
* @date 2019-08-21
*/
@Entity
@Data
@Table(name="receipt_payment_account")
public class ReceiptPaymentAccount implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // 名称
    @Column(name = "name")
    private String name;

    // 部门id关联
    @Column(name = "dept_id")
    private Long deptId;

    // 付款账户名称
    @Column(name = "payment_account")
    private String paymentAccount;

    // 付款账号
    @Column(name = "payment_account_num")
    private String paymentAccountNum;

    // 付款开户行
    @Column(name = "payment_bank")
    private String paymentBank;

    // 收款账户名称
    @Column(name = "receipt_account")
    private String receiptAccount;

    // 收款账号
    @Column(name = "receipt_account_num")
    private String receiptAccountNum;

    // 收款开户行
    @Column(name = "receipt_bank")
    private String receiptBank;

    public void copy(ReceiptPaymentAccount source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}

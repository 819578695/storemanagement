package me.zhengjie.modules.business.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

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

    //金额
    @Column(name = "remaining")
    private BigDecimal remaining;

    // 账户详情id
    @Column(name = "detail_id")
    private Long detailId;

    // 账户名称
    @Column(name = "account_name")
    private String accountName;

    // 付款账号
    @Column(name = "account_num")
    private String accountNum;

    // 付款开户行
    @Column(name = "bank_name")
    private String bankName;

    public void copy(ReceiptPaymentAccount source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}

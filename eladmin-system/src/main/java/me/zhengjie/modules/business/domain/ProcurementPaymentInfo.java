package me.zhengjie.modules.business.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import me.zhengjie.modules.system.domain.DictDetail;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @author kang
* @date 2019-10-09
*/
@Entity
@Data
@Table(name="procurement_payment_info")
public class ProcurementPaymentInfo implements Serializable {

    // 主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // 对应采购信息id
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "procurement_id")
    @NotFound(action= NotFoundAction.IGNORE)
    private ProcurementInformation procurementInformation;

    // 实际付款金额
    @Column(name = "actual_payment_amount")
    private BigDecimal actualPaymentAmount;

    // 实际付款日期
    @Column(name = "actual_payment_date")
    private Timestamp actualPaymentDate;

    // 支付方式
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "payment_type")
    @NotFound(action= NotFoundAction.IGNORE)
    private DictDetail dictDetail;

    // 收付款信息
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "receipt_payment_account_id")
    @NotFound(action= NotFoundAction.IGNORE)
    private ReceiptPaymentAccount receiptPaymentAccount;

    // 创建时间
    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    // 付款说明
    @Column(name = "payment_description")
    private String paymentDescription;

    // 付款比例
    @Column(name = "payment_ratio")
    private String paymentRatio;

    // 应付日期
    @Column(name = "due_date")
    private Timestamp dueDate;

    public void copy(ProcurementPaymentInfo source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}

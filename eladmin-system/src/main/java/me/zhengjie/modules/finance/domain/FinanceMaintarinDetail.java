package me.zhengjie.modules.finance.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import me.zhengjie.modules.system.domain.DictDetail;

import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @author nmk
* @date 2019-08-29
*/
@Entity
@Data
@Table(name="finance_maintarin_detail")
public class FinanceMaintarinDetail implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;

    // 交易账户类型
    @OneToOne
    @JoinColumn(name = "trad_type_id")
    private DictDetail tradType;

    // 账户维护关联
    @Column(name = "maintain_id")
    private Long maintainId;

    // 余额
    @Column(name = "remaining")
    private BigDecimal remaining;

    // 最近交易日期
    @Column(name = "transaction_date")
    private Timestamp transactionDate;

    public void copy(FinanceMaintarinDetail source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}

package me.zhengjie.modules.finance.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import me.zhengjie.modules.system.domain.Dept;
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
@Table(name="fund_maintarin_detail")
public class MaintarinDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // 交易账户类型
    @OneToOne
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "trad_type_id")
    private DictDetail tradType;

    // 账户维护关联
    @Column(name = "maintain_id")
    private Long maintainId;

    // 最近交易日期
    @Column(name = "transaction_date")
    private Timestamp transactionDate;

    // 园区id
    @OneToOne
    @JoinColumn(name = "dept_id")
    private Dept dept;

    public void copy(MaintarinDetail source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}

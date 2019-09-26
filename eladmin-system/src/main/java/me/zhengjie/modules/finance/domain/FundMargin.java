package me.zhengjie.modules.finance.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;
import me.zhengjie.modules.system.domain.Dept;
import me.zhengjie.modules.system.domain.DictDetail;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Data
@Table(name="fund_margin")
public class FundMargin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // 交易日期
    @CreationTimestamp
    @Column(name = "trad_date",nullable = false)
    private Timestamp tradDate;

    // 金额
    @Column(name = "money",nullable = false)
    private BigDecimal money;

    //档口id
    @Column(name = "archives_mouths_id" ,nullable = false)
    private Long houseId;
    /**
     * 交易类型
     */
    @OneToOne
    @JoinColumn(name = "type_id")
    private DictDetail tradType;

    /**
     * 记账类型
     */
    @OneToOne
    @JoinColumn(name = "tally_type_id")
    private DictDetail tallyType;

    /**
     * 部门ID
     */
    @OneToOne
    @JoinColumn(name = "dept_id")
    private Dept dept;

    public void copy(FundMargin source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}

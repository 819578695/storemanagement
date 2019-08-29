package me.zhengjie.modules.finance.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @author nmk
* @date 2019-08-29
*/
@Entity
@Data
@Table(name="finance_maintain")
public class FinanceMaintain implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;

    // 园区id
    @Column(name = "park_id")
    private Long parkId;

    // 余额
    @Column(name = "remaining")
    private BigDecimal remaining;

    // 详情id
    @Column(name = "maintain_detail")
    private Long maintainDetail;

    public void copy(FinanceMaintain source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
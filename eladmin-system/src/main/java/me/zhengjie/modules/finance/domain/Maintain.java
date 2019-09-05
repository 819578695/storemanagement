package me.zhengjie.modules.finance.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import me.zhengjie.modules.system.domain.Dept;

import javax.persistence.*;
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @author nmk
* @date 2019-08-29
*/
@Entity
@Data
@Table(name="fund_maintain")
public class Maintain implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;

    // 部门id
    @OneToOne
    @JoinColumn(name = "dept_id")
    private Dept dept;

    // 余额
    @Column(name = "remaining")
    private BigDecimal remaining;

    public void copy(Maintain source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}

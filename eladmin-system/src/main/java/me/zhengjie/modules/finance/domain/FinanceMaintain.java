package me.zhengjie.modules.finance.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import me.zhengjie.modules.system.domain.Dept;
import org.hibernate.mapping.ToOne;

import javax.persistence.*;
import java.math.BigDecimal;
import java.io.Serializable;
import java.util.List;

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
    @OneToOne
    @JoinColumn(name = "dept_id")
    private Dept dept;

    // 余额
    @Column(name = "remaining")
    private BigDecimal remaining;

    // 详情id
    @Column(name = "maintain_detail")
    private Long maintainDetail;

//    @OneToMany(mappedBy = "finance_maintain",cascade={CascadeType.PERSIST,CascadeType.REMOVE})
//    private List<FinanceMaintarinDetail> financeMaintarinDetails;

    public void copy(FinanceMaintain source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}

package me.zhengjie.modules.finance.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import java.io.Serializable;

/**
* @author nmk
* @date 2019-08-29
*/
@Entity
@Data
@Table(name="finace_margin")
public class FinaceMargin implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // 园区支出关联
    @Column(name = "cost_id",nullable = false)
    private Long costId;

    // 园区收入关联
    @Column(name = "pevenue_id",nullable = false)
    private Long pevenueId;

    // 部门id
    @Column(name = "dept_id",nullable = false)
    private Long deptId;

    public void copy(FinaceMargin source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}

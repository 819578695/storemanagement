package me.zhengjie.modules.business.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import me.zhengjie.modules.system.domain.Dept;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @author kang
* @date 2019-08-22
*/
@Entity
@Data
@Table(name="park_cost")
public class ParkCost implements Serializable {

    // 主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // 园区id
    @Column(name = "park_id")
    private Long parkId;
    /**
     * 部门id
     */
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "dept_id")
    private Dept dept;

    // 场地租金
    @Column(name = "site_rent")
    private BigDecimal siteRent;

    // 水费
    @Column(name = "water_rent")
    private BigDecimal waterRent;

    // 电费
    @Column(name = "electricity_rent")
    private BigDecimal electricityRent;

    // 物业费
    @Column(name = "property_rent")
    private BigDecimal propertyRent;

    // 税赋成本
    @Column(name = "tax_cost")
    private BigDecimal taxCost;

    // 其他费用
    @Column(name = "other_rent")
    private BigDecimal otherRent;

    // 创建时间
    @CreationTimestamp
    @Column(name = "create_time")
    private Timestamp createTime;

    public void copy(ParkCost source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}

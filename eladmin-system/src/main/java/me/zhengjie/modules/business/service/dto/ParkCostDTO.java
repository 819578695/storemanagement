package me.zhengjie.modules.business.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;


/**
* @author kang
* @date 2019-08-22
*/
@Data
public class ParkCostDTO implements Serializable {

    // 主键
    private Long id;

    // 园区id
    private Long parkId;

    // 场地租金
    private BigDecimal siteRent;

    // 水费
    private BigDecimal waterRent;

    // 电费
    private BigDecimal electricityRent;

    // 物业费
    private BigDecimal propertyRent;

    // 税赋成本
    private BigDecimal taxCost;

    // 其他费用
    private BigDecimal otherRent;

    // 创建时间
    private Timestamp createTime;

    //部门名称
    private String deptName;

    //部门编号
    private  Long deptId;
}

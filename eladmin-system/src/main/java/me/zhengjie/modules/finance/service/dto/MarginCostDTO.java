package me.zhengjie.modules.finance.service.dto;

import lombok.Data;
import me.zhengjie.modules.business.domain.ParkPevenue;
import me.zhengjie.modules.business.service.dto.ParkCostDTO;
import me.zhengjie.modules.business.service.dto.ParkPevenueDTO;

import java.awt.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
* @author nmk
* @date 2019-08-29
*/
@Data
public class MarginCostDTO {
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

    //创建时间
    private Timestamp createTime;
}

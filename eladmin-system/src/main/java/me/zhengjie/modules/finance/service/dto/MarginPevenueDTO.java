package me.zhengjie.modules.finance.service.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;


/**
* @author nmk
* @date 2019-08-29
*/
@Data
public class MarginPevenueDTO {
    // 房租
    private BigDecimal houseRent;

    // 物业费
    private BigDecimal propertyRent;

    // 水费
    private BigDecimal waterRent;

    //电费
    private BigDecimal electricityRent;

    // 卫生费
    private BigDecimal sanitationRent;

    // 违约金
    private BigDecimal liquidatedRent;

    // 滞纳金
    private BigDecimal lateRent;

    // 地磅费
    private BigDecimal groundPoundRent;

    // 欠款金额
    private BigDecimal arrersRent;

    //管理费
    private BigDecimal managementRent;

    //停车费
    private BigDecimal parkingRent;

    //门牌号
    private Long houseNumber;
    //创建时间
    private Timestamp createTime;
}

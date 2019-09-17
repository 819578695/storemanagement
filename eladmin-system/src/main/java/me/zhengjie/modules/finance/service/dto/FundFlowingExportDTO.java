package me.zhengjie.modules.finance.service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FundFlowingExportDTO {
    //部门名称
    private String deptName;

    //房租
    private BigDecimal rentSum;

    //停车费
    private BigDecimal parkSum;

    //地磅费
    private BigDecimal wagonSum;

    //往来款
    private BigDecimal contactsSum;

    //投资款
    private BigDecimal investmentSum;

    //水费
    private BigDecimal waterSum;

    //电费
    private BigDecimal electricSum;

    //其他费用
    private BigDecimal otherSum;

    //物业费
    private BigDecimal propertySum;

    //税赋成本
    private BigDecimal scotSum;

    //违约金
    private BigDecimal penalSum;

    //管理费
    private BigDecimal managementSum;

    //滞纳金
    private BigDecimal overdueSum;

    //卫生费
    private BigDecimal sanitationSum;
}

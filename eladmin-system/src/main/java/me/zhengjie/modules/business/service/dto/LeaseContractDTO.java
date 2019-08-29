package me.zhengjie.modules.business.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;


/**
* @author nmk
* @date 2019-08-29
*/
@Data
public class LeaseContractDTO implements Serializable {

    // 主键
    private Long id;

    // 合同编号
    private String contractNo;

    // 租户id
    private Long tenementId;

    // 部门id
    private Long deptId;

    // 档口id
    private Long stallId;

    // 合同名称
    private String contractName;

    // 起止日期
    private Timestamp startDate;

    // 截止日期
    private Timestamp endDate;

    // 免租期
    private Long rentFreePeriod;

    // 保证金
    private BigDecimal deposit;

    // 未缴费用
    private BigDecimal unpaidExpenses;

    // 已缴费用
    private BigDecimal paymentedExpenses;

    // 合同总金额
    private BigDecimal contractAmount;

    // 文件名
    private String fileName;
}
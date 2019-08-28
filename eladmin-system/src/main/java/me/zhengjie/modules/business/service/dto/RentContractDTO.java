package me.zhengjie.modules.business.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;


/**
* @author kang
* @date 2019-08-28
*/
@Data
public class RentContractDTO implements Serializable {

    // 主键
    private Long id;

    // 园区id
    private Long parkId;

    // 部门id
    private Long deptId;

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

    private BigDecimal contractAmount;

    // 文件名
    private String fileName;

    // 合同编号
    private String contractNo;

    //部门名称
    private String deptName;

}

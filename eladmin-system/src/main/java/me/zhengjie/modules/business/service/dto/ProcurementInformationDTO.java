package me.zhengjie.modules.business.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;


/**
* @author kang
* @date 2019-08-20
*/
@Data
public class ProcurementInformationDTO implements Serializable {

    // 主键
    private Long id;

    // 项目编号
    private String pno;

    // 项目名称
    private String projectName;

    // 供应商名称
    private String supplierName;

    // 采购说明
    private String purchaseDescription;

    // 合同截止日
    private Timestamp contractEndDate;

    // 合同总金额
    private BigDecimal contractAmount;

    // 付款比例
    private String paymentRatio;

    // 申请金额
    private BigDecimal applicationsAmount;

    // 申请时间
    private Timestamp applicationsDate;

    // 应付日期
    private Timestamp dueDate;

    // 实际付款金额
    private BigDecimal actualPaymentAmount;

    // 实际付款日期
    private Timestamp actualPaymentDate;
    // 付款方式(关联)
    private Long paymentType;
    //收付款类型名称
    private  String receiptPaymentAccountName;
    //收付款类型id
    private  Long receiptPaymentAccountId;

    //付款方式名称
    private  String paymentTypeName;

    //部门名称
    private String deptName;

    // 部门id
    private Long deptId;
}

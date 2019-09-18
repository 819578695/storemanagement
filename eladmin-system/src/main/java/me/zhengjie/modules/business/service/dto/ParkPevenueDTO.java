package me.zhengjie.modules.business.service.dto;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;


/**
* @author kang
* @date 2019-08-23
*/
@Data
public class ParkPevenueDTO implements Serializable {

    // 主键
    private Long id;

    // 园区id
    private Long parkId;

    // 收付款信息
    private Long receiptPaymentAccountId;

    // 部门id
    private Long deptId;

    // 档口Id
    private Long archivesMouthsId;

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

    //管理费
    private BigDecimal managementRent;

    //停车费
    private BigDecimal parkingRent;

    // 创建时间
    private Timestamp createTime;

    //门牌号
    private Long houseNumber;

    //部门名称
    private String deptName;

    // 付款方式(关联)
    private Long paymentType;

    //收付款类型名称
    private  String receiptPaymentAccountName;

    //付款方式名称
    private String paymentTypeName;

    //园区名称
    private String basicsParkName;

    //合同名称
    private String leaseContractName;

    //合同id
    private Long leaseContractId;

    // 类型(1 已付 2 欠款 3 补缴)
    private Long payTypeId;

    // 类型(1 已付 2 欠款 3 补缴)
    private String payTypeValue;

    // 类型(1 已付 2 欠款 3 补缴)
    private String payTypeName;

    // 修改时间
    private Timestamp updateTime;


}

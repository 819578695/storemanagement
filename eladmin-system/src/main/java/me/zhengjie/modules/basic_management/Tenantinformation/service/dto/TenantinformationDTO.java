package me.zhengjie.modules.basic_management.Tenantinformation.service.dto;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
* @author zlk
* @date 2019-08-12
*/
@Data
public class TenantinformationDTO implements Serializable {

    // id
    private Long id;

    // 面积(m²)
    private String area;

    // 档口/电商楼
    private Long stalltype;

    //档口名称
    private String stalltypeName;

    // 公司名称
    private String companyname;

    // 物流专线
    private String logisticsline;

    // 联系人
    private String linkman;

    // 联系电话
    private String phone;

    // 欠款金额
    private BigDecimal amountinarear;

    //合同关联
    private Long contractid;

    // 合同详情
    private String thecontractdetails;

    // 关联门牌
    private Long stallid;

    // 门牌号
    private String roomnumber;

    // 部门id
    private Long deptId;

    // 创建时间
    private Timestamp tenementdate;

    // 租戶名稱
    private String tenementName;
}

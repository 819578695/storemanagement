package me.zhengjie.modules.basic_management.Tenantinformation.service.dto;

import lombok.Data;
import java.io.Serializable;
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
    private String stall;

    // 房号(门牌号)
    private Long roomnumber;

    // 公司名称
    private String companyname;

    // 物流专线
    private String logisticsline;

    // 联系人
    private String linkman;

    // 联系电话
    private String phone;

    // 欠款金额
    private Long amountinarear;

    // 合同详情
    private String thecontractdetails;

    // 部门id
    private Long deptId;

    // 创建时间
    private Timestamp tenementdate;
}
package me.zhengjie.modules.business.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;


/**
* @author nmk
* @date 2019-08-29
*/

@Data
public class LeaseContractSmallDTO implements Serializable {
    // 主键
    private Long id;

    // 合同编号
    private String contractNo;

    // 合同名称
    private String contractName;

    //合同备注
    private String remarks;

    //合同开始时间
    private Date startDate;

    //合同开始时间
    private Date endDate;

    //免租期起止日期
    private Date rentFreeStartTime;

    //免租期截止日期
    private Date rentFreeEndTime;

    // 租户id
    private Long tenementId;

    //租户名称
    private String tenementName;

    // 档口id
    private Long stallId;

    //门牌号
    private String houseNumber;


    public LeaseContractSmallDTO(Long id, String contractNo, String contractName, String remarks, Date startDate, Date endDate, Date rentFreeStartTime, Date rentFreeEndTime, Long tenementId, String tenementName, Long stallId, String houseNumber) {
        this.id = id;
        this.contractNo = contractNo;
        this.contractName = contractName;
        this.remarks = remarks;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rentFreeStartTime = rentFreeStartTime;
        this.rentFreeEndTime = rentFreeEndTime;
        this.tenementId = tenementId;
        this.tenementName = tenementName;
        this.stallId = stallId;
        this.houseNumber = houseNumber;
    }

    public LeaseContractSmallDTO() {
    }
}

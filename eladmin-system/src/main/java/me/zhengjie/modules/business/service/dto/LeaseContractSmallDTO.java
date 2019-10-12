package me.zhengjie.modules.business.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
* @author nmk
* @date 2019-08-29
*/

public class LeaseContractSmallDTO implements Serializable {
    // 主键
    private Long id;

    // 合同编号
    private String contractNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getTenementId() {
        return tenementId;
    }

    public void setTenementId(Long tenementId) {
        this.tenementId = tenementId;
    }

    public String getTenementName() {
        return tenementName;
    }

    public void setTenementName(String tenementName) {
        this.tenementName = tenementName;
    }

    public Long getStallId() {
        return stallId;
    }

    public void setStallId(Long stallId) {
        this.stallId = stallId;
    }

    public Long getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(Long houseNumber) {
        this.houseNumber = houseNumber;
    }

    // 合同名称
    private String contractName;

    //合同备注
    private String remarks;

    // 租户id
    private Long tenementId;

    //租户名称
    private String tenementName;

    // 档口id
    private Long stallId;

    //门牌号
    private Long houseNumber;


    public LeaseContractSmallDTO(Long id, String contractNo, String contractName, String remarks, Long tenementId, String tenementName, Long stallId, Long houseNumber) {
        this.id = id;
        this.contractNo = contractNo;
        this.contractName = contractName;
        this.remarks = remarks;
        this.tenementId = tenementId;
        this.tenementName = tenementName;
        this.stallId = stallId;
        this.houseNumber = houseNumber;
    }

    public LeaseContractSmallDTO() {
    }
}

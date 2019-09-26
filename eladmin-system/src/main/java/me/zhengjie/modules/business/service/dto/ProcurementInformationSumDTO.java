package me.zhengjie.modules.business.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
* @author kang
* @date 2019-08-20
*/
@Data
public class ProcurementInformationSumDTO implements Serializable {


    // 申请金额
    private Long id;
    private BigDecimal amount;
    private  Long deptId;

    public ProcurementInformationSumDTO(Long id, BigDecimal amount, Long deptId) {
        this.id = id;
        this.amount = amount;
        this.deptId = deptId;
    }
}

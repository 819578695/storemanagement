package me.zhengjie.modules.finance.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


@Data
public class FundMarginDTO implements Serializable {

    //总金额
    private BigDecimal money;

    // 记账类型(房租,停车,地磅,采购,往来款,投资款)
    private Long tallyTypeId;

    //记账类型名称
    private String tallyLabe;

    public FundMarginDTO(BigDecimal money, Long tallyTypeId, String tallyLabe) {
        this.money = money;
        this.tallyTypeId = tallyTypeId;
        this.tallyLabe = tallyLabe;
    }
}

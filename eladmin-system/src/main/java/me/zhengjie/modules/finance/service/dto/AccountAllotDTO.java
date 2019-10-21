package me.zhengjie.modules.finance.service.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class AccountAllotDTO {
    //部门ID
    private Long deptId;

    //源账户ID
    private List<Long> originId;

    //目标账户ID
    private List<Long> targetId;

    //金额
    private BigDecimal remaining;
}

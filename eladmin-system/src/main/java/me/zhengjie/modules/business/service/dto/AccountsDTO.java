package me.zhengjie.modules.business.service.dto;

import lombok.Data;
import me.zhengjie.modules.business.domain.ReceiptPaymentAccount;

import java.util.List;

@Data
public class AccountsDTO {
    //id
    private Long value;

    //字典值名称
    private String label;

    //账户详情集合
    private List<AccountsDTO> children;
}

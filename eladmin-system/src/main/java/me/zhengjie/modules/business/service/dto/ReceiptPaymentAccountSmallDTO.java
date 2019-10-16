package me.zhengjie.modules.business.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


/**
* @author kang
* @date 2019-08-21
*/
@Data
public class ReceiptPaymentAccountSmallDTO implements Serializable {

    private Long id;

    public ReceiptPaymentAccountSmallDTO() {
    }

    public ReceiptPaymentAccountSmallDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // 名称
    private String name;


}

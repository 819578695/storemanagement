package me.zhengjie.modules.basic_management.Tenantinformation.service.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TenantinformationSmallDTO implements Serializable {
    // id
    private Long id;

    // 联系人
    private String linkman;

    public TenantinformationSmallDTO(Long id, String linkman) {
        this.id = id;
        this.linkman = linkman;
    }

    public TenantinformationSmallDTO() {
    }
}

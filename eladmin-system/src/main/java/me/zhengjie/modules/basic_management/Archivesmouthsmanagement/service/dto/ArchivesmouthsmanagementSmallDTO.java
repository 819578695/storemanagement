package me.zhengjie.modules.basic_management.Archivesmouthsmanagement.service.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ArchivesmouthsmanagementSmallDTO implements Serializable {
    //id
    private Long id;

    //门牌号
    private String housenumber;

    public ArchivesmouthsmanagementSmallDTO(Long id, String housenumber) {
        this.id = id;
        this.housenumber = housenumber;
    }

    public ArchivesmouthsmanagementSmallDTO() {
    }
}

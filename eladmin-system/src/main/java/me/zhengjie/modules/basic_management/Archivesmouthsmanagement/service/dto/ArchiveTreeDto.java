package me.zhengjie.modules.basic_management.Archivesmouthsmanagement.service.dto;

import lombok.Data;

@Data
public class ArchiveTreeDto {
    //id
    private Long id;

    //门牌号
    private String name;

    //档口ID
    private Long deptId;

    public ArchiveTreeDto(Long id, String name,Long deptId) {
        this.id = id;
        this.name = name;
        this.deptId = deptId;
    }
}

package me.zhengjie.modules.basic_management.Archivesmouthsmanagement.service.dto;

import lombok.Data;

@Data
public class ArchiveTreeDto {
    //id
    private Long id;

    //门牌号
    private Long name;

    //档口编号
    private Long deptId;

    public ArchiveTreeDto(Long id, Long name,Long deptId) {
        this.id = id;
        this.name = name;
        this.deptId = deptId;
    }
}

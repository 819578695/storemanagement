package me.zhengjie.modules.finance.service.dto;

import lombok.Data;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.service.dto.ArchiveDto;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.service.dto.ArchiveTreeDto;

import java.io.Serializable;
import java.util.List;

@Data
public class TreeDTO implements Serializable {
    /**
     * ID
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    private List children;

    public TreeDTO(Long id, String name, List<ArchiveTreeDto> children) {
        this.id = id;
        this.name = name;
        this.children = children;
    }
}

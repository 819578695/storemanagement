package me.zhengjie.modules.basic_management.Archivesmouthsmanagement.service.dto;

import lombok.Data;
import me.zhengjie.annotation.Query;

import javax.validation.constraints.NotNull;

@Data
public class ArchivesmouthsmanagementQueryCriteria {

    // 模糊
    @Query(type = Query.Type.EQUAL)
    private Long id;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String housenumber;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String contacts;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String leasetype;

    //部门id
    @Query(propName = "id",joinName = "dept" ,type = Query.Type.EQUAL)
    private Long deptId;

    //是否租出\
    @Query(type = Query.Type.IF_RENT)
    private String tenementName;
}

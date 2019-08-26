package me.zhengjie.modules.basic_management.Archivesmouthsmanagement.service.dto;

import lombok.Data;
import me.zhengjie.annotation.Query;

@Data
public class ArchivesmouthsmanagementQueryCriteria {

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String housenumber;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String contacts;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String leasetype;

}

package me.zhengjie.modules.Archivesmouthsmanagement.service.dto;

import lombok.Data;
import me.zhengjie.annotation.Query;

@Data
public class ArchivesmouthsmanagementQueryCriteria {

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String huosenumber;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String contacts;

}

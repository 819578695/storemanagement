package me.zhengjie.modules.basic_management.thearchives.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import me.zhengjie.annotation.Query;

/**
* @author zlk
* @date 2019-08-26
*/
@Data
public class BasicsParkQueryCriteria{

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String garden;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String companyName;
}
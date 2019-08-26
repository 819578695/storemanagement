package me.zhengjie.modules.basic_management.Tenantinformation.service.dto;

import lombok.Data;
import me.zhengjie.annotation.Query;

/**
* @author zlk
* @date 2019-08-12
*/
@Data
public class TenantinformationQueryCriteria{

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String roomnumber;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String linkman;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String phone;
}
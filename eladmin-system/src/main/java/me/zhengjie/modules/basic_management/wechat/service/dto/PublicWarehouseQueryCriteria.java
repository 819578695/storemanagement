package me.zhengjie.modules.basic_management.wechat.service.dto;

import lombok.Data;
import me.zhengjie.annotation.Query;

@Data
public class PublicWarehouseQueryCriteria {
    @Query(type = Query.Type.EQUAL)
    private Long id;

    @Query(type = Query.Type.EQUAL)
    private String openId;
}

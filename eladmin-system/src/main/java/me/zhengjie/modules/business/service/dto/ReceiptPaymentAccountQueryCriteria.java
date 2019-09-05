package me.zhengjie.modules.business.service.dto;

import lombok.Data;
import me.zhengjie.annotation.Query;

/**
* @author kang
* @date 2019-08-21
*/
@Data
public class ReceiptPaymentAccountQueryCriteria{
    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String name;
    @Query(propName = "id",joinName = "dept" ,type = Query.Type.EQUAL)
    private Long deptId;
}

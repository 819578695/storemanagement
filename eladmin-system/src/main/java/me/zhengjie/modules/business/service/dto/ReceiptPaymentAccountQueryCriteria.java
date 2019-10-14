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

    //查询账户详情
    @Query(type = Query.Type.EQUAL)
    private Long detailId;
}

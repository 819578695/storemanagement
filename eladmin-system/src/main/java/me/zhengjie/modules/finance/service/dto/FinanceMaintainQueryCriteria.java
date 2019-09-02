package me.zhengjie.modules.finance.service.dto;

import lombok.Data;
import java.math.BigDecimal;
import me.zhengjie.annotation.Query;

/**
* @author nmk
* @date 2019-08-29
*/
@Data
public class FinanceMaintainQueryCriteria{

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private Long deptId;
}

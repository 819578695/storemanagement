package me.zhengjie.modules.finance.service.dto;

import lombok.Data;
import me.zhengjie.annotation.Query;

/**
* @author nmk
* @date 2019-08-29
*/
@Data
public class FinanceMaintarinDetailQueryCriteria{
    @Query(type = Query.Type.EQUAL)
    private Long deptId;
}

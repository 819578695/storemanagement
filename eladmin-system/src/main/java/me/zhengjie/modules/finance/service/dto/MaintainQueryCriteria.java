package me.zhengjie.modules.finance.service.dto;

import lombok.Data;
import me.zhengjie.annotation.Query;

/**
* @author nmk
* @date 2019-08-29
*/
@Data
public class MaintainQueryCriteria{

    @Query(propName = "name",joinName = "dept" ,type = Query.Type.INNER_LIKE)
    private String deptName;
    @Query(propName = "id",joinName = "dept" ,type = Query.Type.EQUAL)
    private Long deptId;
}

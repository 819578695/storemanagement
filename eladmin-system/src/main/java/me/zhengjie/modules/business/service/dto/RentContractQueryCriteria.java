package me.zhengjie.modules.business.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.math.BigDecimal;
import me.zhengjie.annotation.Query;

/**
* @author kang
* @date 2019-08-28
*/
@Data
public class RentContractQueryCriteria{
    @Query(propName = "name",joinName = "dept" ,type = Query.Type.INNER_LIKE)
    private String deptName;
    @Query(propName = "id",joinName = "dept" ,type = Query.Type.EQUAL)
    private Long deptId;

    @Query(type = Query.Type.INNER_LIKE)
    private String contractNo;
    @Query(type = Query.Type.EQUAL)
    private Integer isAudit;
}

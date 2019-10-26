package me.zhengjie.modules.business.service.dto;

import lombok.Data;

import java.sql.Date;
import me.zhengjie.annotation.Query;

/**
* @author nmk
* @date 2019-08-29
*/
@Data
public class LeaseContractQueryCriteria{
    @Query(propName = "housenumber",joinName = "archivesmouthsmanagement" ,type = Query.Type.INNER_LIKE)
    private String houseNumber;
    @Query(type = Query.Type.GREATER_THAN)
    private Date startDate;
    @Query(type = Query.Type.LESS_THAN)
    private Date endDate;
    @Query(propName = "id",joinName = "dept" ,type = Query.Type.EQUAL)
    private Long deptId;
    @Query(type = Query.Type.EQUAL)
    private Long tenementId;
}

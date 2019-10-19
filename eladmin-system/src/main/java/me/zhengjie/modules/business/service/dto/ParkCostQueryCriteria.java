package me.zhengjie.modules.business.service.dto;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;
import java.math.BigDecimal;
import me.zhengjie.annotation.Query;

/**
* @author kang
* @date 2019-08-22
*/
@Data
public class ParkCostQueryCriteria{
    @Query(propName = "name",joinName = "dept" ,type = Query.Type.INNER_LIKE)
    private String deptName;
    @Query(propName = "id",joinName = "dept" ,type = Query.Type.EQUAL)
    private Long deptId;
    @Query(type = Query.Type.EQUAL)
    private Integer isVertify;
    @Query(propName = "housenumber",joinName = "archivesmouthsmanagement" ,type = Query.Type.INNER_LIKE)
    private Long houseNumber;
    @Query(type = Query.Type.GREATER_THAN_DATE)
    private Date tradDateStart;
    @Query(type = Query.Type.LESS_THAN_DATE)
    private Date tradDateEnd;
    @Query(type = Query.Type.GREATER_THAN_DATE)
    private Date createTimeStart;
    @Query(type = Query.Type.LESS_THAN_DATE)
    private Date createTimeEnd;
}

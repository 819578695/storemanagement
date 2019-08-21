package me.zhengjie.modules.business.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Set;

import me.zhengjie.annotation.Query;

/**
* @author kang
* @date 2019-08-20
*/
@Data
public class ProcurementInformationQueryCriteria{
    @Query(type = Query.Type.INNER_LIKE)
    private String pno;
    @Query(type = Query.Type.GREATER_THAN)
    private Date applicationsDateStart;
    @Query(type = Query.Type.LESS_THAN)
    private  Date applicationsDateEnd;


}
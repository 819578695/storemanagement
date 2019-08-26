package me.zhengjie.modules.business.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.math.BigDecimal;
import me.zhengjie.annotation.Query;
import java.sql.Date;

/**
* @author kang
* @date 2019-08-23
*/
@Data
public class ParkPevenueQueryCriteria{
    @Query(propName = "housenumber",joinName = "archivesmouthsmanagement" ,type = Query.Type.INNER_LIKE)
    private Long houseNumber;
    @Query(type = Query.Type.GREATER_THAN_DATE)
    private Date createTimeStart;
    @Query(type = Query.Type.LESS_THAN_DATE)
    private Date createDateEnd;


}

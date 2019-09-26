package me.zhengjie.modules.finance.service.dto;

import lombok.Data;
import me.zhengjie.annotation.Query;

import java.sql.Date;

@Data
public class MarginQueryCriteria {
    @Query(propName = "id",joinName = "dept",type = Query.Type.EQUAL)
    private Long deptId;
    @Query(propName = "id",joinName = "tradType",type = Query.Type.EQUAL)
    private Long typeId;


    @Query(type = Query.Type.GREATER_THAN_DATE)
    private Date createTimeStart;
    @Query(type = Query.Type.LESS_THAN_DATE)
    private Date createTimeEnd;

    private Long tradTypeId;
    private Long houseId;
}

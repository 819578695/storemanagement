package me.zhengjie.modules.finance.service.dto;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;
import java.math.BigDecimal;
import me.zhengjie.annotation.Query;

/**
* @author nmk
* @date 2019-08-22
*/
@Data
public class FundFlowingQueryCriteria {
    @Query(type = Query.Type.GREATER_THAN_DATE)
    private String tradDateStart;
    @Query(type = Query.Type.LESS_THAN_DATE)
    private String tradDateEnd;

    @Query(propName = "id",joinName = "dept" ,type = Query.Type.EQUAL)
    private Long deptId;


    @Query(propName = "id",joinName = "tradType" ,type = Query.Type.EQUAL)
    private Long tradType;
    @Query(propName = "id",joinName = "tallyType" ,type = Query.Type.EQUAL)
    private Long tallyTypeId;
    @Query(propName = "id",joinName = "typeDict" ,type = Query.Type.EQUAL)
    private Long typeDict;
}

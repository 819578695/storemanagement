package me.zhengjie.modules.basic_management.client.service.dto;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;
import me.zhengjie.annotation.Query;

/**
* @author nmkzlk
* @date 2019-09-02
*/
@Data
public class BasicsClientQueryCriteria{

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private Timestamp parkDate;

    @Query(type = Query.Type.GREATER_THAN_DATE)
    private Date parkDateStart;
    @Query(type = Query.Type.LESS_THAN_DATE)
    private Date parkDateEnd;
}
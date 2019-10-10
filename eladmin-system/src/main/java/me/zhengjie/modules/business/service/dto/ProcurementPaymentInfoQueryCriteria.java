package me.zhengjie.modules.business.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.math.BigDecimal;
import me.zhengjie.annotation.Query;

/**
* @author kang
* @date 2019-10-09
*/
@Data
public class ProcurementPaymentInfoQueryCriteria{
    @Query(propName = "id",joinName = "procurementPaymentInfo" ,type = Query.Type.EQUAL)
    private Long procurementPaymentInfoId;
}

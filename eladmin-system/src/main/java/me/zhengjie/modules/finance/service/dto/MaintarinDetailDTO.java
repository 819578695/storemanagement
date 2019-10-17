package me.zhengjie.modules.finance.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;


/**
* @author nmk
* @date 2019-08-29
*/
@Data
public class MaintarinDetailDTO implements Serializable {

    // 处理精度丢失问题
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    // 交易账户类型
    private Long tradTypeId;
    //交易账户名称
    private String tradTypeLabel;

    // 账户维护关联
    private Long maintainId;

    // 最近交易日期
    private Timestamp transactionDate;

    //园区名称
    private String deptName;
    private Long deptId;
}

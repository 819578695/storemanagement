package me.zhengjie.modules.finance.service.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import me.zhengjie.modules.system.domain.Dict;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


/**
* @author nmk
* @date 2019-08-29
*/
@Data
public class MaintainDTO implements Serializable {

    // 处理精度丢失问题
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull(groups = Dict.Update.class)
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    private long deptId;
    //部门名称
    private String deptName;
    // 余额
    private BigDecimal remaining;

}

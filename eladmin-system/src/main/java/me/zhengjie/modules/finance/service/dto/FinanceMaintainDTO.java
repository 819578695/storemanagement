package me.zhengjie.modules.finance.service.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import me.zhengjie.modules.system.domain.Dict;
import me.zhengjie.modules.system.domain.DictDetail;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


/**
* @author nmk
* @date 2019-08-29
*/
@Data
public class FinanceMaintainDTO implements Serializable {

    // 处理精度丢失问题
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull(groups = Dict.Update.class)
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    // 部门id
    private Long deptId;
    //部门名称
    private String deptName;

    // 余额
    private BigDecimal remaining;

    // 详情id
    private Long maintainDetail;




}

package me.zhengjie.modules.finance.service.dto;

import lombok.Data;
import java.io.Serializable;


/**
* @author nmk
* @date 2019-08-29
*/
@Data
public class FinaceMarginDTO implements Serializable {

    private Long id;

    // 园区支出关联
    private Long costId;

    // 园区收入关联
    private Long pevenueId;

    // 部门id
    private Long deptId;
}

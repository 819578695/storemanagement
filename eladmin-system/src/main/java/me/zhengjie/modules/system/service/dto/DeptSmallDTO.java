package me.zhengjie.modules.system.service.dto;

import lombok.Data;
import java.io.Serializable;

/**
* @author Zheng Jie
* @date 2019-6-10 16:32:18
*/
@Data
public class DeptSmallDTO implements Serializable {

    /**
     * ID
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 上级部门
     */
    private Long pid;

    /**
     * 部门编号
     */
    private String deptNo;
}

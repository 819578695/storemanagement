package me.zhengjie.modules.basic_management.thearchives.service.dto;

import lombok.Data;

@Data
public class BasicsParkContractDTO {
    // id
    private Long id;

    // 部门id
    private Long deptId;

    //合同id
    private Long theContractInformation;

    // 文件名
    private String fileName;

    // 合同编号
    private String contractNo;

    //合同名称
    private String contractName;

    //是否启用
    private String isEnable;
}

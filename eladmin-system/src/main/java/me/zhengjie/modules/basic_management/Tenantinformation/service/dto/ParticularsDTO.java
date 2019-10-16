package me.zhengjie.modules.basic_management.Tenantinformation.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class ParticularsDTO implements Serializable {
    // id
    private Long id;

    // 面积(m²)
    private String area;

    // 档口/电商楼id
    private Long stalltype;

    //档口名称
    private String stalltypeName;

    // 关联门牌
    private Long stallid;

    // 门牌号
    private String roomnumber;

    //合同关联
    private Long contractid;

    //过期状态
    private String Pastdue;

    //合同创建时间
    private Timestamp createTime;


}

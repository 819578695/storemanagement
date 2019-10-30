package me.zhengjie.modules.basic_management.thearchives.service.dto;

import lombok.Data;

import java.security.PrivateKey;
import java.sql.Timestamp;
import java.io.Serializable;


/**
* @author zlk
* @date 2019-08-26
*/
@Data
public class BasicsParkDTO implements Serializable {

    // id
    private Long id;

    // 园区
    private String garden;

    // 成立时间
    private Timestamp dateOfEstablishment;

    // 物业公司名称
    private String companyName;

    // 开园时间
    private Timestamp openingTime;

    // 占用面积
    private Long occupiedArea;

    // 占地面积
    private Long floorSpace;

    // 建筑面积
    private Long coveredArea;

    // 可使用面积
    private Long usableArea;

    //可租用面积
    private Long lettableArea;

    // 合同信息
    private String theContractInformation;

    // 图片上传
    private String fileName;

    // 创建时间
    private Timestamp parkdate;

    // 部门id
    private Long deptId;

    //部门名称
    private String deptName;
}

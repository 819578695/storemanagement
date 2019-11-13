package me.zhengjie.modules.basic_management.Archivesmouthsmanagement.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class ArchivesmouthsmanagementDTO implements Serializable {

    //id
    private Long id;

    //门牌号
    private String housenumber;

    //面积
    private String acreage;

    //定金
    private String earnest;

    //合同保证金
    private String contractmoney;

    //联系人
    private String contacts;

    //租用类型
    private Long stalltype;

    //档口类型
    private String stalltypeName;

    //图片查看
    private String picturetoview;

    //创建日期
    private Timestamp stalldate;

    // 部门id
    private Long deptId;

    // 部门id
    private String name;

    //关联城市id
    private Long areaId;

    // 租戶名稱
    private String tenementName;

}

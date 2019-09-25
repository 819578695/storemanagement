package me.zhengjie.modules.basic_management.Archivesmouthsmanagement.service.dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class ArchiveDto implements Serializable {
    //id
    private Long id;

    //门牌号
    private Long housenumber;

    //面积
    private String acreage;

    //联系人
    private String contacts;

    //租用类型
    private Long stalltype;

    //档口名称
    private String stalltypeName;

    //图片查看
    private String picturetoview;

    //关联城市id
    private Long areaId;

    //关联城市name
    private String areaName;

    // 租戶名稱
    private String tenementName;
}

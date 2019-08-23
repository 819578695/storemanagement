package me.zhengjie.modules.Archivesmouthsmanagement.service.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ArchivesmouthsmanagementDTO implements Serializable {

    //id
    private Long id;

    //门牌号
    private Long housenumber;

    //面积
    private String acreage;

    //定金
    private String earnest;

    //合同保证金
    private String contractmoney;

    //联系人
    private String contacts;

    //租用类型
    private String leasetype;

    //图片查看
    private String picturetoview;

}

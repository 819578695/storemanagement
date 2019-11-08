package me.zhengjie.modules.basic_management.Archivesmouthsmanagement.service.dto;

import lombok.Data;

//档口信息批量上传
@Data
public class ArchiveUpLoadDto {

    //门牌号
    private String housenumber;

    //面积
    private String acreage;

    //定金
    private String earnest;

    //合同保障金
    private String contractmoney;

    //档口联系人
    private String contacts;

    //租用类型
    private String stall;

    //部门
    private Long deptId;

    public ArchiveUpLoadDto(String housenumber, String acreage, String earnest, String contractmoney, String contacts, String stall, Long deptId) {
        this.housenumber = housenumber;
        this.acreage = acreage;
        this.earnest = earnest;
        this.contractmoney = contractmoney;
        this.contacts = contacts;
        this.stall = stall;
        this.deptId = deptId;
    }

    public ArchiveUpLoadDto() {
    }
}

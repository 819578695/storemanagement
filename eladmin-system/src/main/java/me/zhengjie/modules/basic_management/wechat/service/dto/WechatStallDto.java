package me.zhengjie.modules.basic_management.wechat.service.dto;

import lombok.Data;

@Data
public class WechatStallDto {

    //档口编号
    private String housenumber;

    //档口面积
    private String acreage;

    //档口类型
    private String label;

    //档口图片
    private String picturetoview;

    public WechatStallDto(String housenumber, String acreage, String label, String picturetoview) {
        this.housenumber = housenumber;
        this.acreage = acreage;
        this.label = label;
        this.picturetoview = picturetoview;
    }
}

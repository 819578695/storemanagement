package me.zhengjie.modules.basic_management.city.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="t_area")
public class City {
    //id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "areaId")
    private  Long areaId;

    //地区编码
    @Column(name = "areaCode")
    private String areaCode;

    //地区名
    @Column(name = "areaName")
    private String areaName;

    //地区级别
    @Column(name = "level")
    private Long level;

    //城市编码
    @Column(name = "cityCode")
    private String cityCode;

    //城市中心点
    @Column(name = "center")
    private String center;

    //地区父节点
    @Column(name = "parentId")
    private Long parentId;

    public void copy(City source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}

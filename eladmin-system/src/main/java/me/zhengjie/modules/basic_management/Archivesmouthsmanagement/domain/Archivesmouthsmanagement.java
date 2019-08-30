package me.zhengjie.modules.basic_management.Archivesmouthsmanagement.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author zlk
 * @date 2019-08-17
 * */
@Entity
@Data
@Table(name="basics_stall")
public class Archivesmouthsmanagement implements Serializable {


    //id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private  Long id;
    // 部门id关联
    @Column(name = "dept_id")
    private Long deptId;

    //门牌号
    @Column(name = "housenumber")
    private Long housenumber;

    //面积
    @Column(name = "acreage")
    private String acreage;

    //定金
    @Column(name = "earnest")
    private String earnest;

    //合同保证金
    @Column(name = "contractmoney")
    private String contractmoney;

    //联系人
    @Column(name = "contacts")
    private String contacts;

    //租用类型
    @Column(name = "leasetype")
    private String leasetype;

    //图片查看
    @Column(name = "picturetoview")
    private String picturetoview;

    //创建时间
    @CreationTimestamp
    @Column(name = "stall_date")
    private Timestamp stalldate;

    public void copy(Archivesmouthsmanagement source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }

}

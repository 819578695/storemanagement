package me.zhengjie.modules.basic_management.Archivesmouthsmanagement.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;
import me.zhengjie.modules.basic_management.city.domain.City;
import me.zhengjie.modules.system.domain.Dept;
import me.zhengjie.modules.system.domain.DictDetail;
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

    // 部门id
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "dept_id")
    private Dept dept;


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
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "stall_type")
    private DictDetail dictDetail;

    //图片查看
    @Column(name = "picturetoview")
    private String picturetoview;

    //创建时间
    @CreationTimestamp
    @Column(name = "stall_date")
    private Timestamp stalldate;

    //省份id
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "areaId")
    private City areaId;

    public void copy(Archivesmouthsmanagement source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }

}

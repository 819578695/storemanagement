package me.zhengjie.modules.basic_management.thearchives.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import me.zhengjie.modules.system.domain.Dept;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.io.Serializable;
import java.util.List;

/**
* @author zlk
* @date 2019-08-26
*/
@Entity
@Data
@Table(name="basics_park")
public class BasicsPark implements Serializable {

    // id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // 部门id
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "dept_id")
    private Dept dept;


    // 园区
    @Column(name = "garden")
    private String garden;

    // 成立时间
    @Column(name = "date_of_establishment")
    private Timestamp dateOfEstablishment;

    // 物业公司名称
    @Column(name = "company_name")
    private String companyName;

    // 开园时间
    @Column(name = "opening_time")
    private Timestamp openingTime;

    // 占用面积
    @Column(name = "occupied_area")
    private Long occupiedArea;

    // 占地面积
    @Column(name = "floor_space")
    private Long floorSpace;

    // 建筑面积
    @Column(name = "covered_area")
    private Long coveredArea;

    // 可使用面积
    @Column(name = "usable_area")
    private Long usableArea;

    // 合同信息
    @Column(name = "the_contract_information")
    private String theContractInformation;

    // 图片上传
    @Column(name = "url")
    private String fileName;

    // 创建时间
    @CreationTimestamp
    @Column(name = "park_date")
    private Timestamp parkdate;

    public void copy(BasicsPark source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
    /*@Override
    public String toString() {
        return "Picture{" +
                "filename='" + imageUpload + '\'' +
                '}';
    }*/
}

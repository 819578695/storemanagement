package me.zhengjie.modules.basic_management.client.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author nmkzlk
* @date 2019-09-02
*/
@Entity
@Data
@Table(name="basics_client")
public class BasicsClient implements Serializable {

    // id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    // 客户姓名
    @Column(name = "name")
    private String name;

    // 客户电话
    @Column(name = "phone")
    private String phone;

    // 创建时间
    @CreationTimestamp
    @Column(name = "park_date")
    private Timestamp parkDate;

    public void copy(BasicsClient source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
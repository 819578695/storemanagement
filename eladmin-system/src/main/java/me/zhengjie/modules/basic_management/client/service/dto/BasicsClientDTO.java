package me.zhengjie.modules.basic_management.client.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;


/**
* @author nmkzlk
* @date 2019-09-02
*/
@Data
public class BasicsClientDTO implements Serializable {

    // id
    private Integer id;

    // 客户姓名
    private String name;

    // 客户电话
    private String phone;

    // 创建时间
    private Timestamp parkDate;
}
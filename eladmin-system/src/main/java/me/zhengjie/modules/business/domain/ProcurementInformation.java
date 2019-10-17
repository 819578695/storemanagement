package me.zhengjie.modules.business.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import me.zhengjie.modules.system.domain.Dept;
import me.zhengjie.modules.system.domain.DictDetail;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @author kang
* @date 2019-08-20
*/
@Entity
@Data
@Table(name="procurement_information")
public class ProcurementInformation implements Serializable {

    // 主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 部门id
     */
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "dept_id")
    @NotFound(action= NotFoundAction.IGNORE)
    private Dept dept;

    // 项目编号
    @Column(name = "pno",nullable = false)
    private String pno;

    // 项目名称
    @Column(name = "project_name",nullable = false)
    private String projectName;

    // 供应商名称
    @Column(name = "supplier_name")
    private String supplierName;

    // 采购说明
    @Column(name = "purchase_description")
    private String purchaseDescription;

    // 合同截止日
    @Column(name = "contract_end_date")
    private Timestamp contractEndDate;

    // 合同总金额
    @Column(name = "contract_amount")
    private BigDecimal contractAmount;

    // 申请金额
    @Column(name = "applications_amount")
    private BigDecimal applicationsAmount;

    // 申请时间
    @CreationTimestamp
    @Column(name = "applications_date")
    private Timestamp applicationsDate;

    // 创建时间
    @CreationTimestamp
    @Column(name = "create_time")
    private Timestamp createTime;

    // 文件名
    @Column(name = "file_name")
    private String fileName;

    // 是否启用
    @Column(name = "is_enable")
    private String isEnable;

    // 是否删除
    @Column(name = "is_delete")
    private String isDelete;


    public void copy(ProcurementInformation source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}

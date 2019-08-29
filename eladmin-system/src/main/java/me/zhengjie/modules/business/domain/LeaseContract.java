package me.zhengjie.modules.business.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @author nmk
* @date 2019-08-29
*/
@Entity
@Data
@Table(name="lease_contract")
public class LeaseContract implements Serializable {

    // 主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // 合同编号
    @Column(name = "contract_no")
    private String contractNo;

    // 租户id
    @Column(name = "tenement_id")
    private Long tenementId;

    // 部门id
    @Column(name = "dept_id")
    private Long deptId;

    // 档口id
    @Column(name = "stall_id")
    private Long stallId;

    // 合同名称
    @Column(name = "contract_name")
    private String contractName;

    // 起止日期
    @Column(name = "start_date")
    private Timestamp startDate;

    // 截止日期
    @Column(name = "end_date")
    private Timestamp endDate;

    // 免租期
    @Column(name = "rent_free_period")
    private Long rentFreePeriod;

    // 保证金
    @Column(name = "deposit")
    private BigDecimal deposit;

    // 未缴费用
    @Column(name = "unpaid_expenses")
    private BigDecimal unpaidExpenses;

    // 已缴费用
    @Column(name = "paymented_expenses")
    private BigDecimal paymentedExpenses;

    // 合同总金额
    @Column(name = "contract_amount")
    private BigDecimal contractAmount;

    // 文件名
    @Column(name = "file_name")
    private String fileName;

    public void copy(LeaseContract source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
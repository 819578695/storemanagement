package me.zhengjie.modules.finance.service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DetailDTO {
    //主键
    private Long id;
    //园区名称
    private String deptName;
    //交易账户名称
    private String tradTypeLabel;
    // 余额
    private BigDecimal remaining;

    public DetailDTO(Long id, String deptName, String tradTypeLabel, BigDecimal remaining) {
        this.id = id;
        this.deptName = deptName;
        this.tradTypeLabel = tradTypeLabel;
        this.remaining = remaining;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getTradTypeLabel() {
        return tradTypeLabel;
    }

    public void setTradTypeLabel(String tradTypeLabel) {
        this.tradTypeLabel = tradTypeLabel;
    }

    public BigDecimal getRemaining() {
        return remaining;
    }

    public void setRemaining(BigDecimal remaining) {
        this.remaining = remaining;
    }
}

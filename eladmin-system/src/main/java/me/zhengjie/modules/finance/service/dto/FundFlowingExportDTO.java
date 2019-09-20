package me.zhengjie.modules.finance.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class FundFlowingExportDTO implements Serializable {
    //部门名称
    private String deptName;

    //房租
    private BigDecimal pevenueRentSum;

    //停车费
    private BigDecimal parkSum;

    //地磅费
    private BigDecimal wagonSum;

    //往来款
    private BigDecimal contactsSum;

    //投资款
    private BigDecimal investmentSum;

    //水费
    private BigDecimal waterSum;

    //电费
    private BigDecimal electricSum;

    //其他费用
    private BigDecimal otherSum;

    //物业费
    private BigDecimal propertySum;

    //税赋成本
    private BigDecimal scotSum;

    //违约金
    private BigDecimal penalSum;

    //管理费
    private BigDecimal managementSum;

    //滞纳金
    private BigDecimal overdueSum;

    //卫生费
    private BigDecimal sanitationSum;


    //成本租金
    private BigDecimal siteRent;
    //成本水费
    private BigDecimal waterRent;
    //成本电费
    private BigDecimal electricityRent;
    //成本物业费
    private BigDecimal propertyRent;
    //成本赋税成本
    private BigDecimal taxCost;
    //成本其他费用
    private BigDecimal otherRent;
    //成本往来款
    private BigDecimal contactsRent;
    //成本投资款
    private BigDecimal investmentRent;
    public FundFlowingExportDTO(String deptName, BigDecimal pevenueRentSum, BigDecimal parkSum, BigDecimal wagonSum, BigDecimal contactsSum, BigDecimal investmentSum, BigDecimal waterSum, BigDecimal electricSum, BigDecimal otherSum, BigDecimal propertySum, BigDecimal scotSum, BigDecimal penalSum, BigDecimal managementSum, BigDecimal overdueSum, BigDecimal sanitationSum, BigDecimal siteRent, BigDecimal waterRent,BigDecimal electricityRent, BigDecimal propertyRent, BigDecimal taxCost,BigDecimal otherRent ,BigDecimal contactsRent,BigDecimal investmentRent) {
        this.deptName = deptName;
        if (contactsRent == null){
            this.contactsRent = BigDecimal.ZERO;
        }else {
            this.contactsRent = contactsRent;
        }
        if (investmentRent == null){
            this.investmentRent = BigDecimal.ZERO;
        }else {
            this.investmentRent = investmentRent;
        }
        if (otherRent == null){
            this.otherRent = BigDecimal.ZERO;
        }else {
            this.otherRent = otherRent;
        }
        if (taxCost == null){
            this.taxCost = BigDecimal.ZERO;
        }else {
            this.taxCost = taxCost;
        }
        if (propertyRent == null){
            this.propertyRent = BigDecimal.ZERO;
        }else {
            this.propertyRent = propertyRent;
        }
        if (electricityRent == null){
            this.electricityRent = BigDecimal.ZERO;
        }else {
            this.electricityRent = electricityRent;
        }
        if (waterRent == null){
            this.waterRent = BigDecimal.ZERO;
        }else {
            this.waterRent = waterRent;
        }
        if (siteRent == null){
            this.siteRent = BigDecimal.ZERO;
        }else {
            this.siteRent = siteRent;
        }
        if (pevenueRentSum == null){
            this.pevenueRentSum = BigDecimal.ZERO;
        }else {
            this.pevenueRentSum = pevenueRentSum;
        }
        if (parkSum == null){
            this.parkSum = BigDecimal.ZERO;
        }else {
            this.parkSum = parkSum;
        }
        if (wagonSum == null){
            this.wagonSum = BigDecimal.ZERO;
        }else {
            this.wagonSum = wagonSum;
        }
        if (contactsSum == null){
            this.contactsSum = BigDecimal.ZERO;
        }else {
            this.contactsSum = contactsSum;
        }
        if (investmentSum == null){
            this.investmentSum = BigDecimal.ZERO;
        }else {
            this.investmentSum = investmentSum;
        }
        if (waterSum == null){
            this.waterSum = BigDecimal.ZERO;
        }else {
            this.waterSum = waterSum;
        }
        if (electricSum == null){
            this.electricSum = BigDecimal.ZERO;
        }else {
            this.electricSum = electricSum;
        }
        if(otherSum == null){
            this.otherSum = BigDecimal.ZERO;
        }else {
            this.otherSum = otherSum;
        }
        if (propertySum == null){
            this.propertySum = BigDecimal.ZERO;
        }else {
            this.propertySum = propertySum;
        }
        if (scotSum == null){
            this.scotSum = BigDecimal.ZERO;
        }else {
            this.scotSum = scotSum;
        }
        if (penalSum == null){
            this.penalSum = BigDecimal.ZERO;
        }else {
            this.penalSum = penalSum;
        }
        if(managementSum == null){
            this.managementSum = BigDecimal.ZERO;
        }else {
            this.managementSum = managementSum;
        }
        if (overdueSum == null){
            this.overdueSum = BigDecimal.ZERO;
        }else {
            this.overdueSum = overdueSum;
        }
        if (sanitationSum == null){
            this.sanitationSum = BigDecimal.ZERO;
        }else {
            this.sanitationSum = sanitationSum;
        }
    }

    public BigDecimal getContactsRent() {
        return contactsRent;
    }

    public void setContactsRent(BigDecimal contactsRent) {
        this.contactsRent = contactsRent;
    }

    public BigDecimal getInvestmentRent() {
        return investmentRent;
    }

    public void setInvestmentRent(BigDecimal investmentRent) {
        this.investmentRent = investmentRent;
    }

    public BigDecimal getSiteRent() {
        return siteRent;
    }

    public void setSiteRent(BigDecimal siteRent) {
        this.siteRent = siteRent;
    }

    public BigDecimal getWaterRent() {
        return waterRent;
    }

    public void setWaterRent(BigDecimal waterRent) {
        this.waterRent = waterRent;
    }

    public BigDecimal getElectricityRent() {
        return electricityRent;
    }

    public void setElectricityRent(BigDecimal electricityRent) {
        this.electricityRent = electricityRent;
    }

    public BigDecimal getPropertyRent() {
        return propertyRent;
    }

    public void setPropertyRent(BigDecimal propertyRent) {
        this.propertyRent = propertyRent;
    }

    public BigDecimal getTaxCost() {
        return taxCost;
    }

    public void setTaxCost(BigDecimal taxCost) {
        this.taxCost = taxCost;
    }

    public BigDecimal getOtherRent() {
        return otherRent;
    }

    public void setOtherRent(BigDecimal other_rent) {
        this.otherRent = other_rent;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public BigDecimal getPevenueRentSum() {
        return pevenueRentSum;
    }

    public void setPevenueRentSum(BigDecimal pevenueRentSum) {
        this.pevenueRentSum = pevenueRentSum;
    }

    public BigDecimal getParkSum() {
        return parkSum;
    }

    public void setParkSum(BigDecimal parkSum) {
        this.parkSum = parkSum;
    }

    public BigDecimal getWagonSum() {
        return wagonSum;
    }

    public void setWagonSum(BigDecimal wagonSum) {
        this.wagonSum = wagonSum;
    }

    public BigDecimal getContactsSum() {
        return contactsSum;
    }

    public void setContactsSum(BigDecimal contactsSum) {
        this.contactsSum = contactsSum;
    }

    public BigDecimal getInvestmentSum() {
        return investmentSum;
    }

    public void setInvestmentSum(BigDecimal investmentSum) {
        this.investmentSum = investmentSum;
    }

    public BigDecimal getWaterSum() {
        return waterSum;
    }

    public void setWaterSum(BigDecimal waterSum) {
        this.waterSum = waterSum;
    }

    public BigDecimal getElectricSum() {
        return electricSum;
    }

    public void setElectricSum(BigDecimal electricSum) {
        this.electricSum = electricSum;
    }

    public BigDecimal getOtherSum() {
        return otherSum;
    }

    public void setOtherSum(BigDecimal otherSum) {
        this.otherSum = otherSum;
    }

    public BigDecimal getPropertySum() {
        return propertySum;
    }

    public void setPropertySum(BigDecimal propertySum) {
        this.propertySum = propertySum;
    }

    public BigDecimal getScotSum() {
        return scotSum;
    }

    public void setScotSum(BigDecimal scotSum) {
        this.scotSum = scotSum;
    }

    public BigDecimal getPenalSum() {
        return penalSum;
    }

    public void setPenalSum(BigDecimal penalSum) {
        this.penalSum = penalSum;
    }

    public BigDecimal getManagementSum() {
        return managementSum;
    }

    public void setManagementSum(BigDecimal managementSum) {
        this.managementSum = managementSum;
    }

    public BigDecimal getOverdueSum() {
        return overdueSum;
    }

    public void setOverdueSum(BigDecimal overdueSum) {
        this.overdueSum = overdueSum;
    }

    public BigDecimal getSanitationSum() {
        return sanitationSum;
    }

    public void setSanitationSum(BigDecimal sanitationSum) {
        this.sanitationSum = sanitationSum;
    }
}

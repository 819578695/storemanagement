package me.zhengjie.modules.finance.repository;

import me.zhengjie.modules.finance.domain.FinanceMaintarinDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

/**
* @author nmk
* @date 2019-08-29
*/

public interface FinanceMaintarinDetailRepository extends JpaRepository<FinanceMaintarinDetail, Long>, JpaSpecificationExecutor {
    //确认交易类型是否存在
    @Query(value = "select id from finance_maintarin_detail f where f.trad_type_id= ? AND f.maintain_id= ?" , nativeQuery = true)
    Long  getDetailByDeptAndAndMaintainId(long tardTypeId , Long maintainId);

    //查询金额总额
    @Query(value = "select sum(remaining) from finance_maintarin_detail where maintain_id = ? ",nativeQuery = true)
    BigDecimal findByMaintainId(Long maintainId);

    //根据部门查询详情
    @Query(value = "select id ,trad_type_id , maintain_id ,remaining , transaction_date , dept_id from finance_maintarin_detail where dept_id = ? " , nativeQuery = true)
    Page<FinanceMaintarinDetail> findAllByDept(Long deptId , Pageable pageable);

    FinanceMaintarinDetail findByTradTypeIdAndDeptId(Long typeId,Long deptId);
}

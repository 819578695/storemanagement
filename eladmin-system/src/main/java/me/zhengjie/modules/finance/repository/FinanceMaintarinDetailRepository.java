package me.zhengjie.modules.finance.repository;

import me.zhengjie.modules.finance.domain.FinanceMaintarinDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
* @author nmk
* @date 2019-08-29
*/

public interface FinanceMaintarinDetailRepository extends JpaRepository<FinanceMaintarinDetail, Long>, JpaSpecificationExecutor {
    @Query(value = "select id from finance_maintarin_detail f where f.trad_type_id= ? AND f.maintain_id= ?" , nativeQuery = true)
    Long  getDetailByDeptAndAndMaintainId(long tardTypeId , Long maintainId);
}

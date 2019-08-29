package me.zhengjie.modules.finance.repository;

import me.zhengjie.modules.finance.domain.FinanceMaintarinDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author nmk
* @date 2019-08-29
*/
public interface FinanceMaintarinDetailRepository extends JpaRepository<FinanceMaintarinDetail, Long>, JpaSpecificationExecutor {
}
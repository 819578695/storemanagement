package me.zhengjie.modules.finance.repository;

import me.zhengjie.modules.finance.domain.FinanceMaintain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author nmk
* @date 2019-08-29
*/
public interface FinanceMaintainRepository extends JpaRepository<FinanceMaintain, Long>, JpaSpecificationExecutor {
}
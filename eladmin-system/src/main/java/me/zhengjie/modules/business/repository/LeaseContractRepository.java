package me.zhengjie.modules.business.repository;

import me.zhengjie.modules.business.domain.LeaseContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author nmk
* @date 2019-08-29
*/
public interface LeaseContractRepository extends JpaRepository<LeaseContract, Long>, JpaSpecificationExecutor {
}
package me.zhengjie.modules.business.repository;

import me.zhengjie.modules.business.domain.RentContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author kang
* @date 2019-08-28
*/
public interface RentContractRepository extends JpaRepository<RentContract, Long>, JpaSpecificationExecutor {
}

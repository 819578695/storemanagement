package me.zhengjie.modules.business.repository;

import me.zhengjie.modules.business.domain.LeaseContract;
import me.zhengjie.modules.business.domain.RentContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
* @author nmk
* @date 2019-08-29
*/
public interface LeaseContractRepository extends JpaRepository<LeaseContract, Long>, JpaSpecificationExecutor {
    List<LeaseContract> findByDeptId(Long deptId);

    List<LeaseContract> findByArchivesmouthsmanagementId(Long id);

}

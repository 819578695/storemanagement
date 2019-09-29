package me.zhengjie.modules.business.repository;

import me.zhengjie.modules.business.domain.LeaseContract;
import me.zhengjie.modules.business.domain.RentContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
* @author nmk
* @date 2019-08-29
*/
public interface LeaseContractRepository extends JpaRepository<LeaseContract, Long>, JpaSpecificationExecutor {
    List<LeaseContract> findByDeptId(Long deptId);

    List<LeaseContract> findByArchivesmouthsmanagementId(Long id);

    /*查询最新的流水编号*/
    @Query(value = "select right(contract_no,4) FROM lease_contract where dept_id=?1 ORDER BY id DESC LIMIT 0,1  ",nativeQuery =true)
    String findByNewcontractNo(Long deptId);

}

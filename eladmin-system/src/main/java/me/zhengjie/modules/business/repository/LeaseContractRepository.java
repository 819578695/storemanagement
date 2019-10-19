package me.zhengjie.modules.business.repository;

import me.zhengjie.modules.business.domain.LeaseContract;
import me.zhengjie.modules.business.domain.RentContract;
import me.zhengjie.modules.business.service.dto.LeaseContractSmallDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
* @author nmk
* @date 2019-08-29
*/
public interface LeaseContractRepository extends JpaRepository<LeaseContract, Long>, JpaSpecificationExecutor {
    @Query(value = "select * FROM lease_contract where dept_id=?1 ",nativeQuery =true)
    List<LeaseContract> findByDeptId(Long deptId);

    List<LeaseContract> findByTenantinformationId(Long id);

    List<LeaseContract> findByArchivesmouthsmanagementId(Long id);

    /*查询最新的流水编号*/
    @Query(value = "select right(contract_no,4) FROM lease_contract where dept_id=?1 ORDER BY id DESC LIMIT 0,1  ",nativeQuery =true)
    String findByNewcontractNo(Long deptId);

    @Query(value = "select new me.zhengjie.modules.business.service.dto.LeaseContractSmallDTO(l.id,l.contractNo,l.contractName,l.remarks,l.startDate,l.endDate,l.rentFreeStartTime,l.rentFreeEndTime,t.id,t.linkman,s.id,s.housenumber) from LeaseContract l left join Archivesmouthsmanagement s on l.archivesmouthsmanagement.id=s.id left join Tenantinformation t on l.tenantinformation.id=t.id where l.dept.id=?1")
    List<LeaseContractSmallDTO> findbyleaseContractSmall(Long deptId);

}

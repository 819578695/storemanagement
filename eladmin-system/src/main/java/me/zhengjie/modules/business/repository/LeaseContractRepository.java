package me.zhengjie.modules.business.repository;

import me.zhengjie.modules.business.domain.LeaseContract;
import me.zhengjie.modules.business.service.dto.LeaseContractSmallDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
* @author nmk
* @date 2019-08-29
*/
public interface LeaseContractRepository extends JpaRepository<LeaseContract, Long>, JpaSpecificationExecutor {
    @Query(value = "select * FROM lease_contract where dept_id=?1 ",nativeQuery =true)
    List<LeaseContract> findByDeptId(Long deptId);
    @Query(value = "select * from lease_contract where stall_id=?1 ",nativeQuery = true)
    List<LeaseContract> findByStallId(Long stallId);

    List<LeaseContract> findByTenantinformationId(Long id);

    List<LeaseContract> findByArchivesmouthsmanagementId(Long id);

    @Query(value = "select * FROM lease_contract where stall_id=?1  and is_enable='1' ",nativeQuery =true)
    LeaseContract findByArchivesmouthsmanagementIdAndIsEnable(Long id);

    /*查询最新的流水编号*/
    @Query(value = "select right(contract_no,4) FROM lease_contract where dept_id=?1 ORDER BY id DESC LIMIT 0,1  ",nativeQuery =true)
    String findByNewcontractNo(Long deptId);

    //下拉框选择合同
    @Query(value = "select new me.zhengjie.modules.business.service.dto.LeaseContractSmallDTO(l.id,l.contractNo,l.contractName,l.remarks,l.startDate,l.endDate,l.rentFreeStartTime,l.rentFreeEndTime,t.id,t.linkman,s.id,s.housenumber) from LeaseContract l left join Archivesmouthsmanagement s on l.archivesmouthsmanagement.id=s.id left join Tenantinformation t on l.tenantinformation.id=t.id where l.dept.id=?1")
    List<LeaseContractSmallDTO> findbyleaseContractSmall(Long deptId);

    /*审核未通过*/
    @Modifying
    @Query(value = "update lease_contract set is_audit=1 where id=?1",nativeQuery = true)
    void updateByVertify1(Long id);

    /*审核通过*/
    @Modifying
    @Query(value = "update lease_contract set is_audit=2 where id=?1",nativeQuery = true)
    void updateByVertify2(Long id);
}

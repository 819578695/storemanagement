package me.zhengjie.modules.basic_management.Tenantinformation.repository;

import me.zhengjie.modules.basic_management.Tenantinformation.domain.Tenantinformation;
import me.zhengjie.modules.basic_management.Tenantinformation.service.dto.TenantinformationSmallDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
* @author zlk
* @date 2019-08-12
*/
public interface TenantinformationRepository extends JpaRepository<Tenantinformation, Long>, JpaSpecificationExecutor {

    List<Tenantinformation> findByDeptId(Long deptId);

    @Query(value = "select * from basics_tenement where stall_id=?1",nativeQuery = true)
    Tenantinformation findByArchivesmouthsmanagementId(Long archivesmouthsmanagementId);

    @Query(value = "select new me.zhengjie.modules.basic_management.Tenantinformation.service.dto.TenantinformationSmallDTO(l.id,l.linkman) from Tenantinformation l where l.dept.id=?1")
    List<TenantinformationSmallDTO> tenantinformationSmallDto(Long deptId);
}

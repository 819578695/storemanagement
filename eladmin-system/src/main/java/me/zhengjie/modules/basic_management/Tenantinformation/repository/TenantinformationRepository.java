package me.zhengjie.modules.basic_management.Tenantinformation.repository;

import me.zhengjie.modules.basic_management.Tenantinformation.domain.Tenantinformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
* @author zlk
* @date 2019-08-12
*/
public interface TenantinformationRepository extends JpaRepository<Tenantinformation, Long>, JpaSpecificationExecutor {

    List<Tenantinformation> findByDeptId(Long deptId);
}

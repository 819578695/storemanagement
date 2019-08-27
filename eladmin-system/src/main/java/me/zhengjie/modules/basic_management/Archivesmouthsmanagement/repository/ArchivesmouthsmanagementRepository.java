package me.zhengjie.modules.basic_management.Archivesmouthsmanagement.repository;

import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.domain.Archivesmouthsmanagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ArchivesmouthsmanagementRepository extends JpaRepository<Archivesmouthsmanagement, Long>, JpaSpecificationExecutor {
    List<Archivesmouthsmanagement> findByDeptId(Long deptId);
}

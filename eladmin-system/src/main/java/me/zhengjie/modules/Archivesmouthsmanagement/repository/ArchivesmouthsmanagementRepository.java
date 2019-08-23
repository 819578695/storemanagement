package me.zhengjie.modules.Archivesmouthsmanagement.repository;

import me.zhengjie.modules.Archivesmouthsmanagement.domain.Archivesmouthsmanagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ArchivesmouthsmanagementRepository extends JpaRepository<Archivesmouthsmanagement, Long>, JpaSpecificationExecutor {
}

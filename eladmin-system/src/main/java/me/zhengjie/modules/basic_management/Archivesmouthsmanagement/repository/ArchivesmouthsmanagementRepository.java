package me.zhengjie.modules.basic_management.Archivesmouthsmanagement.repository;

import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.domain.Archivesmouthsmanagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArchivesmouthsmanagementRepository extends JpaRepository<Archivesmouthsmanagement, Long>, JpaSpecificationExecutor {

   /*@Query(value = "select * from basics_stall where if(?1 =0,dept_id=?1,1=1)",nativeQuery = true)*/
     List<Archivesmouthsmanagement> findByDeptId(Long deptId);//根据部门id查询档口
     List<Archivesmouthsmanagement> findByDeptIdAndTenementNameIsNull(Long deptId);///根据部门id查询未出租档口
     List<Archivesmouthsmanagement> findByDeptIdAndTenementNameIsNotNull(Long deptId);///根据部门id查询已出租档口

      List<Archivesmouthsmanagement> findByTenementNameIsNull();//未出租档口
      List<Archivesmouthsmanagement> findByTenementNameIsNotNull();//已出租档口
     @Query(value = "SELECT * FROM basics_stall b  WHERE b.area_id = ?1", nativeQuery = true)
     List<Archivesmouthsmanagement> findByAreaId(Long id);
     @Query(value = "SELECT COUNT(1) num FROM basics_stall b  WHERE b.area_id = ?1", nativeQuery = true)
     int findBycoum(Long id);
     @Query(value = "SELECT * FROM basics_stall where id = ?1", nativeQuery = true)
     List<Archivesmouthsmanagement> findArchivesmouthsmanagementById(Long id);
}

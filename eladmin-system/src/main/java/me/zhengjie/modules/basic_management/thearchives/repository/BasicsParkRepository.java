package me.zhengjie.modules.basic_management.thearchives.repository;

import jdk.internal.org.objectweb.asm.tree.analysis.Value;
import me.zhengjie.modules.basic_management.thearchives.domain.BasicsPark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
* @author zlk
* @date 2019-08-26
*/
public interface BasicsParkRepository extends JpaRepository<BasicsPark, Long>, JpaSpecificationExecutor {
    List<BasicsPark> findByDeptId(Long deptId);

    @Query(value = "SELECT * FROM basics_park b WHERE b.dept_id = ?1", nativeQuery = true)
    BasicsPark findByDept(Long deptId);

}

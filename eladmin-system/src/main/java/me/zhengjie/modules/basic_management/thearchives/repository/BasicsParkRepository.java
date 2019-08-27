package me.zhengjie.modules.basic_management.thearchives.repository;

import me.zhengjie.modules.basic_management.thearchives.domain.BasicsPark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
* @author zlk
* @date 2019-08-26
*/
public interface BasicsParkRepository extends JpaRepository<BasicsPark, Long>, JpaSpecificationExecutor {
    List<BasicsPark> findByDeptId(Long deptId);
}

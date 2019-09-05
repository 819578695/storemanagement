package me.zhengjie.modules.business.repository;

import me.zhengjie.modules.business.domain.ParkCost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
* @author kang
* @date 2019-08-22
*/
public interface ParkCostRepository extends JpaRepository<ParkCost, Long>, JpaSpecificationExecutor {

    List<ParkCost> findByRentContractIdAndDeptId(Long id,Long deptId);

}

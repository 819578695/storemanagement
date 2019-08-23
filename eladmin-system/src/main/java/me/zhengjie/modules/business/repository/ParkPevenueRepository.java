package me.zhengjie.modules.business.repository;

import me.zhengjie.modules.business.domain.ParkPevenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author kang
* @date 2019-08-23
*/
public interface ParkPevenueRepository extends JpaRepository<ParkPevenue, Long>, JpaSpecificationExecutor {
}
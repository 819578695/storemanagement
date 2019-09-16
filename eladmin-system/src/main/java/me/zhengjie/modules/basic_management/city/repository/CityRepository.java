package me.zhengjie.modules.basic_management.city.repository;

import me.zhengjie.modules.basic_management.city.domain.City;
import me.zhengjie.modules.system.domain.DictDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author Zheng Jie
* @date 2019-04-10
*/
public interface CityRepository extends JpaRepository<City, Long>, JpaSpecificationExecutor {

}

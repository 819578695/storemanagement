package me.zhengjie.modules.business.repository;

import jdk.internal.org.objectweb.asm.tree.analysis.Value;
import me.zhengjie.modules.business.domain.ParkCost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

/**
* @author kang
* @date 2019-08-22
*/
public interface ParkCostRepository extends JpaRepository<ParkCost, Long>, JpaSpecificationExecutor {

    List<ParkCost> findByRentContractIdAndDeptId(Long id,Long deptId);

    @Query(value = "SELECT SUM(coalesce(site_rent,0)+coalesce(water_rent,0)+coalesce(electricity_rent,0)+coalesce(property_rent,0)+coalesce(tax_cost,0)+coalesce(other_rent,0)) as money FROM park_cost\n" +
            " WHERE   if(?1 !='',dept_id=?1,1=1)  and (PERIOD_DIFF( date_format( now( ) , '%Y%m' ) , date_format( create_time, '%Y%m' ) ) =1 or DATE_FORMAT( create_time, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' ))\n" +
            "GROUP BY MONTH(create_time)",nativeQuery = true)
    BigDecimal[] findByDeptIdSumRent(Long deptId);

}

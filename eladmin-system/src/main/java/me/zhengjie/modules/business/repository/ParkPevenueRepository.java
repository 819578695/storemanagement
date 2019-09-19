package me.zhengjie.modules.business.repository;

import me.zhengjie.modules.business.domain.ParkPevenue;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

/**
* @author kang
* @date 2019-08-23
*/
public interface ParkPevenueRepository extends JpaRepository<ParkPevenue, Long>, JpaSpecificationExecutor {

    List<ParkPevenue> findByLeaseContractIdAndDeptId(Long id,Long deptId);

    @Query(value = "SELECT SUM(coalesce(house_rent,0)+coalesce(property_rent,0)+coalesce(water_rent,0)+coalesce(electricity_rent,0)+coalesce(sanitation_rent,0)+coalesce(liquidated_rent,0)+coalesce(late_rent,0)+coalesce(ground_pound_rent,0)+coalesce(management_rent,0)+coalesce(parking_rent,0)) as money FROM park_pevenue\n" +
            " WHERE   if(?1 !='',dept_id=?1,1=1) and (PERIOD_DIFF( date_format( now( ) , '%Y%m' ) , date_format( create_time, '%Y%m' ) ) =1 or DATE_FORMAT( create_time, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' ))\n" +
            "GROUP BY MONTH(create_time)",nativeQuery = true)
    BigDecimal[] findByDeptIdSumRent(Long deptId);


    List<ParkPevenue> findByArchivesmouthsmanagementIdAndPayTypeId(Long archivesmouthsmanagementId,Long payTypeId);
}

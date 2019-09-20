package me.zhengjie.modules.business.repository;


import me.zhengjie.modules.business.domain.ParkCost;
import me.zhengjie.modules.business.service.dto.ParkCostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;

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

    /*@Query(value=" me.zhengjie.modules.business.service.dto.ParkCostDTO(p.id,b.id,p.siteRent,p.waterRent,p.electricityRent,p.propertyRent,p.taxCost,p.otherRent,dd.id,p.createTime,d.name,d.id,dd.label,b.housenumber,c.contractName,c.id,r.id,r.name) from ParkCost p" +
            "LEFT JOIN Dept d on p.deptId=d.id\n" +
            "LEFT JOIN Archivesmouthsmanagement b on p.archivesMouthsId=b.id\n" +
            "left join ReceiptPaymentAccount r on p.receiptPaymentAccountId=r.id\n" +
            "left join RentContract c on p.rentContractId=c.id\n" +
            "left join DictDetail dd on p.paymentType=dd.id")
    Page<ParkCostDTO> findAlls(@Nullable Specification<ParkCostDTO> spec, Pageable pageable);*/

}

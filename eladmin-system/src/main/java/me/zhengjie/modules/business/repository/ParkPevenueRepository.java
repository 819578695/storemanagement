package me.zhengjie.modules.business.repository;

import me.zhengjie.modules.business.domain.ParkPevenue;
import me.zhengjie.modules.business.service.dto.ParkPevenueDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

/**
* @author kang
* @date 2019-08-23
*/
public interface ParkPevenueRepository extends JpaRepository<ParkPevenue, Long>, JpaSpecificationExecutor {

    List<ParkPevenue> findByLeaseContractIdAndDeptId(Long id,Long deptId);
    //查询欠款金额
    @Query(value = "select p.* from park_pevenue p  left JOIN dict_detail d ON p.type=d.id where d.`value`='PEVENUE_UNDER'and p.lease_contract_id= ?1 ",nativeQuery = true)
    List<ParkPevenue> findByLeaseContractIdAndType(Long id);

    List<ParkPevenue> findByLeaseContractId(Long id);



    @Query(value = "SELECT SUM(coalesce(house_rent,0)+coalesce(property_rent,0)+coalesce(water_rent,0)+coalesce(electricity_rent,0)+coalesce(sanitation_rent,0)+coalesce(liquidated_rent,0)+coalesce(late_rent,0)+coalesce(ground_pound_rent,0)+coalesce(management_rent,0)+coalesce(parking_rent,0)) as money FROM park_pevenue\n" +
            " WHERE   if(?1 !='',dept_id=?1,1=1) and (PERIOD_DIFF( date_format( now( ) , '%Y%m' ) , date_format( create_time, '%Y%m' ) ) =1 or DATE_FORMAT( create_time, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' ))\n" +
            "GROUP BY MONTH(create_time)",nativeQuery = true)
    BigDecimal[] findByDeptIdSumRent(Long deptId);


    List<ParkPevenue> findByArchivesmouthsmanagementIdAndPayTypeId(Long archivesmouthsmanagementId,Long payTypeId);

    /*审核未通过*/
    @Modifying
    @Query(value = "update park_pevenue set is_vertify=1 where id=?1",nativeQuery = true)
    void updateByVertify1(Long id);

    /*审核通过*/
    @Modifying
    @Query(value = "update park_pevenue set is_vertify=2 where id=?1",nativeQuery = true)
    void updateByVertify2(Long id);

    @Query(value = "select * from park_pevenue where receipt_payment_account_id = ?1",nativeQuery = true)
    Object findByReceiptPaymentAccount (Long id);
}

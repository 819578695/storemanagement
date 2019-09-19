package me.zhengjie.modules.basic_management.Tenantinformation.repository;

import me.zhengjie.modules.basic_management.Tenantinformation.domain.Tenantinformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
* @author zlk
* @date 2019-08-12
*/
public interface TenantinformationRepository extends JpaRepository<Tenantinformation, Long>, JpaSpecificationExecutor {

    List<Tenantinformation> findByDeptId(Long deptId);

    /*@Query(value = "SELECT\n" +
            "(\n" +
            "  IFNULL(p.house_rent,0) +\n" +
            "\tIFNULL(p.property_rent,0) +\n" +
            "\tIFNULL(p.water_rent,0) +\n" +
            "\tIFNULL(p.electricity_rent,0) +\n" +
            "\tIFNULL(p.sanitation_rent,0) +\n" +
            "\tIFNULL(p.liquidated_rent,0) +\n" +
            "\tIFNULL(p.late_rent,0) +\n" +
            "\tIFNULL(p.ground_pound_rent,0) +\n" +
            "\tIFNULL(p.management_rent,0) +\n" +
            "\tIFNULL(p.parking_rent,0) \n" +
            ") num\n" +
            "FROM\n" +
            "\tbasics_tenement b\n" +
            "LEFT JOIN basics_stall s ON b.stall_id = s.id\n" +
            "LEFT JOIN dict_detail d ON b.stall_type = d.id\n" +
            "LEFT JOIN park_pevenue p ON p.archives_mouths_id = s.id AND p.type = 41",nativeQuery = true)
    List<Integer> findByamountinarear();*/
}

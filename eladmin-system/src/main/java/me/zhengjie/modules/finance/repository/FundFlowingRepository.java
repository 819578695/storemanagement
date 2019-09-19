package me.zhengjie.modules.finance.repository;


import me.zhengjie.modules.finance.domain.FundFlowing;
import me.zhengjie.modules.finance.service.dto.FundFlowingExportDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;


/**
* @author nmk
* @date 2019-08-22
*/
public interface FundFlowingRepository extends JpaRepository<FundFlowing, Long>, JpaSpecificationExecutor {

    FundFlowing findByTallyTypeIdAndTypeDictIdAndParkCostPevenueId(Long tallyTypeId, Long typeDictId, Long parkCostPevenueId);

    //根据收入支出Id和支付方式id
    List<FundFlowing> findByTypeDictIdAndParkCostPevenueId( Long typeDictId, Long parkCostPevenueId);

    @Query(value = "SELECT " +
            "  d.NAME AS deptName, " +
            "  ( SELECT SUM(money) FROM fund_flowing WHERE dept_id = :deptId AND tally_type_id = (SELECT id FROM dict_detail WHERE label = \"房租\") ) AS rentSum, " +
            "  ( SELECT SUM(money) FROM fund_flowing WHERE dept_id = :deptId AND tally_type_id = (SELECT id FROM dict_detail WHERE label = \"停车费\") ) AS parkSum, " +
            "  ( SELECT SUM(money) FROM fund_flowing WHERE dept_id = :deptId AND tally_type_id = (SELECT id FROM dict_detail WHERE label = \"地磅费\") ) AS wagonSum, " +
            "  ( SELECT SUM(money) FROM fund_flowing WHERE dept_id = :deptId AND tally_type_id = (SELECT id FROM dict_detail WHERE label = \"往来款\") ) AS contactsSum, " +
            "  ( SELECT SUM(money) FROM fund_flowing WHERE dept_id = :deptId AND tally_type_id = (SELECT id FROM dict_detail WHERE label = \"水费\") ) AS waterSum, " +
            "  ( SELECT SUM(money) FROM fund_flowing WHERE dept_id = :deptId AND tally_type_id = (SELECT id FROM dict_detail WHERE label = \"电费\") ) AS electricSum, " +
            "  ( SELECT SUM(money) FROM fund_flowing WHERE dept_id = :deptId AND tally_type_id = (SELECT id FROM dict_detail WHERE label = \"其他费用\") ) AS otherSum, " +
            "  ( SELECT SUM(money) FROM fund_flowing WHERE dept_id = :deptId AND tally_type_id = (SELECT id FROM dict_detail WHERE label = \"物业费\") ) AS propertySum, " +
            "  ( SELECT SUM(money) FROM fund_flowing WHERE dept_id = :deptId AND tally_type_id = (SELECT id FROM dict_detail WHERE label = \"税赋成本\") ) AS scotSum, " +
            "  ( SELECT SUM(money) FROM fund_flowing WHERE dept_id = :deptId AND tally_type_id = (SELECT id FROM dict_detail WHERE label = \"违约金\") ) AS penalSum, " +
            "  ( SELECT SUM(money) FROM fund_flowing WHERE dept_id = :deptId AND tally_type_id = (SELECT id FROM dict_detail WHERE label = \"管理费\") ) AS managementSum, " +
            "  ( SELECT SUM(money) FROM fund_flowing WHERE dept_id = :deptId AND tally_type_id = (SELECT id FROM dict_detail WHERE label = \"滞纳金\") ) AS overdueSum, " +
            "  ( SELECT SUM(money) FROM fund_flowing WHERE dept_id = :deptId AND tally_type_id = (SELECT id FROM dict_detail WHERE label = \"卫生费\") ) AS sanitationSum " +
            "FROM " +
            " fund_flowing f " +
            " LEFT JOIN dept d ON f.dept_id = d.id " +
            " WHERE f.dept_id = :deptId " +
            " and cast(f.trad_date AS date ) >= :tradDateStart "+
            " and cast(f.trad_date AS date ) <= :tradDateEnd "+
            " GROUP BY d.name" ,nativeQuery = true)
    List<FundFlowingExportDTO> findByDeptId(@Param("deptId") Long deptId , @Param("tradDateStart") Date tradDateStart , @Param("tradDateEnd") Date tradDateEnd);
}

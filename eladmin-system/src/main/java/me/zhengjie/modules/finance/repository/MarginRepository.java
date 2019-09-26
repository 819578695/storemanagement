package me.zhengjie.modules.finance.repository;

import me.zhengjie.modules.finance.domain.FundMargin;
import me.zhengjie.modules.finance.service.dto.FundMarginDTO;
import me.zhengjie.modules.finance.service.dto.MarginQueryCriteria;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MarginRepository extends JpaRepository<FundMargin,Long>, JpaSpecificationExecutor {

    @Query(value = "select new me.zhengjie.modules.finance.service.dto.FundMarginDTO( sum(f.money) , d.id , d.label) from FundMargin f left join DictDetail d on d.id = f.tallyType.id group by f.tallyType.id")
   List<FundMarginDTO> findByDeptIdAndAndMoney(@Param("deptId")Long deptId ,@Param("typeId")Long typeId );


    @Query(value = "select new me.zhengjie.modules.finance.service.dto.FundMarginDTO( sum(f.money) , d.id , d.label) from FundMargin f left join DictDetail d on d.id = f.tallyType.id where f.dept.id = :deptId and f.houseId = :houseId and f.tradType.id =:typeId group by f.tallyType.id")
    List<FundMarginDTO> findByPevenueAllByDeptIdAndHouseNumber(@Param("deptId")Long deptId ,@Param("houseId")Long houseId,@Param("typeId")Long typeId);
}

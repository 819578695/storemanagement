package me.zhengjie.modules.finance.repository;

import me.zhengjie.modules.finance.domain.FinanceMaintain;
import me.zhengjie.modules.finance.service.dto.FinanceMaintainDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
* @author nmk
* @date 2019-08-29
*/
public interface FinanceMaintainRepository extends JpaRepository<FinanceMaintain, Long>, JpaSpecificationExecutor {

    @Query(nativeQuery = true , value = " select d.name deptName , sum(md.remaining) remaining " +
    " from finance_maintain f " +
    " left join dept d on d.id = f.dept_id "+
    " left join finance_maintarin_detail md on md.maintain_id = f.id "+
    " where f.dept_id = ? ")
    FinanceMaintainDTO getNameAndRemaining(Long deptId);

}

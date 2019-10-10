package me.zhengjie.modules.business.repository;

import me.zhengjie.modules.business.domain.RentContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
* @author kang
* @date 2019-08-28
*/
public interface RentContractRepository extends JpaRepository<RentContract, Long>, JpaSpecificationExecutor {

    /*根据部门查询*/
    @Query(value = "select * FROM rent_contract where dept_id=?1 and is_enable=1  ",nativeQuery =true)
    List<RentContract> findByDeptId(Long deptId);

    /*查询最新的流水编号*/
    @Query(value = "select right(contract_no,4) FROM rent_contract where dept_id=?1 ORDER BY id DESC LIMIT 0,1  ",nativeQuery =true)
    String findByNewcontractNo(Long deptId);
}

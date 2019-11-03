package me.zhengjie.modules.business.repository;

import me.zhengjie.modules.business.domain.RentContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
* @author kang
* @date 2019-08-28
*/
public interface RentContractRepository extends JpaRepository<RentContract, Long>, JpaSpecificationExecutor {


    /*根据部门查询已审核通过的合同*/
    @Query(value = "select * FROM rent_contract where dept_id=?1 and is_enable=1 and is_audit=2",nativeQuery =true)
    List<RentContract> findByDeptId(Long deptId);

    /**
     * 查询所有审核通过且状态为启用的合同
     * @return
     */
    @Query(value = "select * FROM rent_contract where is_enable=1 and is_audit=2",nativeQuery =true)
    List<RentContract> findAllByAudit();

    /*查询最新的流水编号*/
    @Query(value = "select right(contract_no,4) FROM rent_contract where dept_id=?1 ORDER BY id DESC LIMIT 0,1  ",nativeQuery =true)
    String findByNewcontractNo(Long deptId);

    /*审核未通过*/
    @Modifying
    @Query(value = "update rent_contract set is_audit=1 where id=?1",nativeQuery = true)
    void updateByVertify1(Long id);

    /*审核通过*/
    @Modifying
    @Query(value = "update rent_contract set is_audit=2 where id=?1",nativeQuery = true)
    void updateByVertify2(Long id);
}

package me.zhengjie.modules.finance.repository;

import me.zhengjie.modules.finance.domain.MaintarinDetail;
import me.zhengjie.modules.finance.service.dto.DetailDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

/**
* @author nmk
* @date 2019-08-29
*/

public interface MaintarinDetailRepository extends JpaRepository<MaintarinDetail, Long>, JpaSpecificationExecutor {
    //确认交易类型是否存在
    @Query(value = "select id from fund_maintarin_detail f where f.trad_type_id= ? AND f.maintain_id= ?" , nativeQuery = true)
    Long  getDetailByDeptAndAndMaintainId(long tardTypeId , Long maintainId);

    //查询当前网点下总金额
    @Query(value = "select sum(remaining) from fund_maintarin_detail where dept_id = ? ",nativeQuery = true)
    BigDecimal findByMaintainId(Long deptId);

    //根据部门查询详情
    @Query(value = "select id ,trad_type_id , maintain_id , transaction_date , dept_id from fund_maintarin_detail where dept_id = ? " , nativeQuery = true)
    Page<MaintarinDetail> findAllByDeptId(Long deptId , Pageable pageable);

    MaintarinDetail findByTradTypeIdAndDeptId(Long typeId,Long deptId);

    List<MaintarinDetail> findByDeptId(Long deptId);

    //查询二级下金额总额
    @Query(value = "select new me.zhengjie.modules.finance.service.dto.DetailDTO(f.id , dt.name , d.label , sum(r.remaining) ) from MaintarinDetail f left join DictDetail d on d.id = f.tradType.id left join Dept dt on dt.id = f.dept.id left join ReceiptPaymentAccount r on r.detailId = f.id where f.dept.id = :deptId GROUP BY f.id")
    List<DetailDTO> queryRemainings(@Param("deptId")Long deptId );

}

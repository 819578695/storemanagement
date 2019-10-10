package me.zhengjie.modules.business.repository;

import me.zhengjie.modules.business.domain.ProcurementInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
* @author kang
* @date 2019-08-20
*/
public interface ProcurementInformationRepository extends JpaRepository<ProcurementInformation, Long>, JpaSpecificationExecutor {

   /* @Query(value="select new me.zhengjie.modules.business.service.dto.ProcurementInformationSumDTO(p.id,sum(applicationsAmount),p.deptId) from ProcurementInformation p  group by p.id")
    Page<ProcurementInformationSumDTO> list(@Nullable Specification<ProcurementInformationSumDTO> spec, Pageable pageable);*/

    /*查询最新的流水编号*/
    @Query(value = "select right(pno,4) FROM procurement_information where dept_id=?1 ORDER BY id DESC LIMIT 0,1  ",nativeQuery =true)
    String findByNewcontractNo(Long deptId);

}

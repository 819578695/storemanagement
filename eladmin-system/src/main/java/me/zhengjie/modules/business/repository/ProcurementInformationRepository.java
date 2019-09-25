package me.zhengjie.modules.business.repository;

import me.zhengjie.modules.business.domain.ProcurementInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
* @author kang
* @date 2019-08-20
*/
public interface ProcurementInformationRepository extends JpaRepository<ProcurementInformation, Long>, JpaSpecificationExecutor {

   /* @Query(value="select new me.zhengjie.modules.business.service.dto.ProcurementInformationSumDTO(p.id,sum(applicationsAmount),p.deptId) from ProcurementInformation p  group by p.id")
    Page<ProcurementInformationSumDTO> list(@Nullable Specification<ProcurementInformationSumDTO> spec, Pageable pageable);*/
}

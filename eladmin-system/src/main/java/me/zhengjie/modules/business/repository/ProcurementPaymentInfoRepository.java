package me.zhengjie.modules.business.repository;

import me.zhengjie.modules.business.domain.ProcurementPaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
* @author kang
* @date 2019-10-09
*/
public interface ProcurementPaymentInfoRepository extends JpaRepository<ProcurementPaymentInfo, Long>, JpaSpecificationExecutor {

   List<ProcurementPaymentInfo> findByProcurementInformationId(Long procurementInformationId);

}

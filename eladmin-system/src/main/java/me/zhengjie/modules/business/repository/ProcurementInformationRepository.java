package me.zhengjie.modules.business.repository;

import me.zhengjie.modules.business.domain.ProcurementInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author kang
* @date 2019-08-20
*/
public interface ProcurementInformationRepository extends JpaRepository<ProcurementInformation, Long>, JpaSpecificationExecutor {

}
package me.zhengjie.modules.finance.repository;

import me.zhengjie.modules.finance.domain.Maintain;
import me.zhengjie.modules.finance.service.dto.MaintainDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


/**
* @author nmk
* @date 2019-08-29
*/
public interface MaintainRepository extends JpaRepository<Maintain, Long>, JpaSpecificationExecutor<Maintain> {
}

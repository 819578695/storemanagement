package me.zhengjie.modules.system.repository;


import me.zhengjie.modules.system.domain.DictDetail;
import me.zhengjie.modules.system.service.dto.DictDetailDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import sun.awt.SunHints;

import java.util.List;

/**
* @author Zheng Jie
* @date 2019-04-10
*/
public interface DictDetailRepository extends JpaRepository<DictDetail, Long>, JpaSpecificationExecutor {

    //根据value
    DictDetail findByDictIdAndValue(Long id,String value);
    //根据字典ID查询对应详情信息
    List<DictDetail> findAllByDictId(Long dictId);
}

package me.zhengjie.modules.finance.service;

import me.zhengjie.modules.finance.service.dto.MarginQueryCriteria;
import me.zhengjie.modules.finance.service.dto.TreeDTO;
import me.zhengjie.modules.system.service.dto.DeptDTO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

/**
* @author nmk
* @date 2019-08-29
*/
//@CacheConfig(cacheNames = "finaceMargin")
public interface MarginService {

    /**
    * queryAll 分页
    * @param criteria
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(MarginQueryCriteria criteria);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
//    public Object queryAll(FinaceMarginQueryCriteria criteria);

    List<TreeDTO> buildTree();
}

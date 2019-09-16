package me.zhengjie.modules.finance.service;

import me.zhengjie.modules.finance.service.dto.MarginQueryCriteria;
import org.springframework.data.domain.Pageable;

/**
* @author nmk
* @date 2019-08-29
*/
//@CacheConfig(cacheNames = "finaceMargin")
public interface MarginService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(MarginQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
//    public Object queryAll(FinaceMarginQueryCriteria criteria);

}

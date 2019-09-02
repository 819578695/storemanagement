package me.zhengjie.modules.finance.service;

import me.zhengjie.modules.finance.domain.FinaceMargin;
import me.zhengjie.modules.finance.service.dto.FinaceMarginDTO;
import me.zhengjie.modules.finance.service.dto.FinaceMarginQueryCriteria;
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

/**
* @author nmk
* @date 2019-08-29
*/
//@CacheConfig(cacheNames = "finaceMargin")
public interface FinaceMarginService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(FinaceMarginQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(FinaceMarginQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    FinaceMarginDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    FinaceMarginDTO create(FinaceMargin resources);

    /**
     * update
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(FinaceMargin resources);

    /**
     * delete
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);
}

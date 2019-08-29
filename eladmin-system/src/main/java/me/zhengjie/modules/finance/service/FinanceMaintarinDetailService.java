package me.zhengjie.modules.finance.service;

import me.zhengjie.modules.finance.domain.FinanceMaintarinDetail;
import me.zhengjie.modules.finance.service.dto.FinanceMaintarinDetailDTO;
import me.zhengjie.modules.finance.service.dto.FinanceMaintarinDetailQueryCriteria;
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

/**
* @author nmk
* @date 2019-08-29
*/
//@CacheConfig(cacheNames = "financeMaintarinDetail")
public interface FinanceMaintarinDetailService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(FinanceMaintarinDetailQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(FinanceMaintarinDetailQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    FinanceMaintarinDetailDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    FinanceMaintarinDetailDTO create(FinanceMaintarinDetail resources);

    /**
     * update
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(FinanceMaintarinDetail resources);

    /**
     * delete
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);
}
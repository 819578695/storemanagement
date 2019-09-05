package me.zhengjie.modules.finance.service;

import me.zhengjie.modules.finance.domain.FinanceMaintain;
import me.zhengjie.modules.finance.service.dto.FinanceMaintainDTO;
import me.zhengjie.modules.finance.service.dto.FinanceMaintainQueryCriteria;
import org.springframework.data.domain.Pageable;

/**
* @author nmk
* @date 2019-08-29
*/
//@CacheConfig(cacheNames = "financeMaintain")
public interface FinanceMaintainService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(FinanceMaintainQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(FinanceMaintainQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    FinanceMaintainDTO findById(Long id);


    /**
     * create
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    FinanceMaintainDTO create(FinanceMaintain resources);

    /**
     * update
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(FinanceMaintain resources);

    /**
     * delete
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);
}

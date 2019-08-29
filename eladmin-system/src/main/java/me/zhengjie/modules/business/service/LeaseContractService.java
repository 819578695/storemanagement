package me.zhengjie.modules.business.service;

import me.zhengjie.modules.business.domain.LeaseContract;
import me.zhengjie.modules.business.service.dto.LeaseContractDTO;
import me.zhengjie.modules.business.service.dto.LeaseContractQueryCriteria;
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

/**
* @author nmk
* @date 2019-08-29
*/
//@CacheConfig(cacheNames = "leaseContract")
public interface LeaseContractService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(LeaseContractQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(LeaseContractQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    LeaseContractDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    LeaseContractDTO create(LeaseContract resources);

    /**
     * update
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(LeaseContract resources);

    /**
     * delete
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);
}
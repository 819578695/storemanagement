package me.zhengjie.modules.business.service;

import me.zhengjie.modules.business.domain.RentContract;
import me.zhengjie.modules.business.service.dto.RentContractDTO;
import me.zhengjie.modules.business.service.dto.RentContractQueryCriteria;
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

/**
* @author kang
* @date 2019-08-28
*/
//@CacheConfig(cacheNames = "rentContract")
public interface RentContractService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(RentContractQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(RentContractQueryCriteria criteria);


    Object findByDeptId(Long deptId);

    /**
     * findById
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    RentContractDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    RentContractDTO create(RentContract resources);

    /**
     * update
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(RentContract resources);

    /**
     * delete
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);
}

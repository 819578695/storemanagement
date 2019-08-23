package me.zhengjie.modules.business.service;

import me.zhengjie.modules.business.domain.ParkCost;
import me.zhengjie.modules.business.service.dto.ParkCostDTO;
import me.zhengjie.modules.business.service.dto.ParkCostQueryCriteria;
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

/**
* @author kang
* @date 2019-08-22
*/
//@CacheConfig(cacheNames = "parkCost")
public interface ParkCostService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(ParkCostQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(ParkCostQueryCriteria criteria);
    /**
     * queryAll 分页
     * @param pageable
     * @return
     */
    //@Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(Pageable pageable);

    /**
     * findById
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    ParkCostDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    ParkCostDTO create(ParkCost resources);

    /**
     * update
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(ParkCost resources);

    /**
     * delete
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);
}

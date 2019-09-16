package me.zhengjie.modules.business.service;

import me.zhengjie.modules.business.domain.ParkPevenue;
import me.zhengjie.modules.business.service.dto.ParkPevenueDTO;
import me.zhengjie.modules.business.service.dto.ParkPevenueQueryCriteria;
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

/**
* @author kang
* @date 2019-08-23
*/
//@CacheConfig(cacheNames = "parkPevenue")
public interface ParkPevenueService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(ParkPevenueQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(ParkPevenueQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    ParkPevenueDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    ParkPevenueDTO create(ParkPevenue resources);

    /**
     * update
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(ParkPevenue resources);

    /**
     * payBack
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    ParkPevenueDTO payBack(ParkPevenue resources);

    /**
     * delete
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);

    /**
     * 园区收入统计
     * @param deptId
     * @return
     */
    //@Cacheable(keyGenerator = "keyGenerator")
    public Object findPevenueMoney(Long deptId);
}

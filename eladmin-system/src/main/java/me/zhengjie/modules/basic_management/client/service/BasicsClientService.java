package me.zhengjie.modules.basic_management.client.service;

import me.zhengjie.modules.basic_management.client.domain.BasicsClient;
import me.zhengjie.modules.basic_management.client.service.dto.BasicsClientDTO;
import me.zhengjie.modules.basic_management.client.service.dto.BasicsClientQueryCriteria;
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

/**
* @author nmkzlk
* @date 2019-09-02
*/
//@CacheConfig(cacheNames = "basicsClient")
public interface BasicsClientService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(BasicsClientQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(BasicsClientQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    BasicsClientDTO findById(Integer id);

    /**
     * create
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    BasicsClientDTO create(BasicsClient resources);

    /**
     * update
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(BasicsClient resources);

    /**
     * delete
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Integer id);
}
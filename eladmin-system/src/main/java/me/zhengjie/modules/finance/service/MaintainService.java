package me.zhengjie.modules.finance.service;

import me.zhengjie.modules.finance.domain.Maintain;
import me.zhengjie.modules.finance.service.dto.MaintainDTO;
import me.zhengjie.modules.finance.service.dto.MaintainQueryCriteria;
import org.springframework.data.domain.Pageable;

/**
* @author nmk
* @date 2019-08-29
*/
//@CacheConfig(cacheNames = "financeMaintain")
public interface MaintainService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(MaintainQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(MaintainQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    MaintainDTO findById(Long id);


    /**
     * create
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    MaintainDTO create(Maintain resources);

    /**
     * update
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(Maintain resources);

    /**
     * delete
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);
}

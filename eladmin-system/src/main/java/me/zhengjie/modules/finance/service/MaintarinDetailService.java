package me.zhengjie.modules.finance.service;

import me.zhengjie.modules.finance.domain.MaintarinDetail;
import me.zhengjie.modules.finance.service.dto.MaintarinDetailDTO;
import me.zhengjie.modules.finance.service.dto.MaintarinDetailQueryCriteria;
import org.springframework.data.domain.Pageable;

/**
* @author nmk
* @date 2019-08-29
*/
//@CacheConfig(cacheNames = "financeMaintarinDetail")
public interface MaintarinDetailService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(MaintarinDetailQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(MaintarinDetailQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    MaintarinDetailDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    MaintarinDetailDTO create(MaintarinDetail resources);

    /**
     * update
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(MaintarinDetail resources);

    /**
     * delete
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);
}

package me.zhengjie.modules.business.service;

import me.zhengjie.modules.business.domain.ProcurementPaymentInfo;
import me.zhengjie.modules.business.service.dto.ProcurementPaymentInfoDTO;
import me.zhengjie.modules.business.service.dto.ProcurementPaymentInfoQueryCriteria;
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

/**
* @author kang
* @date 2019-10-09
*/
//@CacheConfig(cacheNames = "procurementPaymentInfo")
public interface ProcurementPaymentInfoService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(ProcurementPaymentInfoQueryCriteria criteria, Pageable pageable);


    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(ProcurementPaymentInfoQueryCriteria criteria);

    /**
     *
     *
     * @return
     */
    //@Cacheable(keyGenerator = "keyGenerator")
    public Object findByProcurement(Long procurementInformationId);

    /**
     * findById
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    ProcurementPaymentInfoDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    ProcurementPaymentInfoDTO create(ProcurementPaymentInfo resources);

    /**
     * update
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(ProcurementPaymentInfo resources);

    /**
     * delete
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);
}

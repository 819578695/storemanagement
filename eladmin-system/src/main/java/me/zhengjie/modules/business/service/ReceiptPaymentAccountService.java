package me.zhengjie.modules.business.service;

import me.zhengjie.modules.business.domain.ReceiptPaymentAccount;
import me.zhengjie.modules.business.service.dto.ReceiptPaymentAccountDTO;
import me.zhengjie.modules.business.service.dto.ReceiptPaymentAccountQueryCriteria;
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

/**
* @author kang
* @date 2019-08-21
*/
//@CacheConfig(cacheNames = "receiptPaymentAccount")
public interface ReceiptPaymentAccountService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(ReceiptPaymentAccountQueryCriteria criteria, Pageable pageable);
    /**
     * queryAll
     * @param pageable
     * @return
     */
    Object queryAll(Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(ReceiptPaymentAccountQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    ReceiptPaymentAccountDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    ReceiptPaymentAccountDTO create(ReceiptPaymentAccount resources);

    /**
     * update
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(ReceiptPaymentAccount resources);

    /**
     * delete
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);
}
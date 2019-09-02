package me.zhengjie.modules.finance.service;

import me.zhengjie.modules.business.domain.ParkCost;
import me.zhengjie.modules.business.domain.ParkPevenue;
import me.zhengjie.modules.finance.domain.JournalAccountOfCapital;
import me.zhengjie.modules.finance.service.dto.JournalAccountOfCapitalDTO;
import me.zhengjie.modules.finance.service.dto.JournalAccountOfCapitalQueryCriteria;
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

/**
* @author nmk
* @date 2019-08-22
*/
//@CacheConfig(cacheNames = "journalAccountOfCapital")
public interface JournalAccountOfCapitalService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(JournalAccountOfCapitalQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(JournalAccountOfCapitalQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    JournalAccountOfCapitalDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    JournalAccountOfCapitalDTO create(JournalAccountOfCapital resources);

    /**
     * create
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)新增成本
    JournalAccountOfCapitalDTO createByPostCost(ParkCost resources, String value, BigDecimal money);

    /**
     * create
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)新增收入
    JournalAccountOfCapitalDTO createByPostPevenue(ParkPevenue resources, String value, BigDecimal money);

    /**
     * update
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(JournalAccountOfCapital resources);

    /**
     * update
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void updateByPostCost(ParkCost resources, String value, BigDecimal money);

    /**
     * update
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void updateByPostPevenue(ParkPevenue resources, String value, BigDecimal money);

    /**
     * delete
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);
}

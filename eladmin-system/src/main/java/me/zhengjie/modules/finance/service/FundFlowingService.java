package me.zhengjie.modules.finance.service;

import me.zhengjie.modules.business.domain.ParkCost;
import me.zhengjie.modules.business.domain.ParkPevenue;
import me.zhengjie.modules.finance.domain.FundFlowing;
import me.zhengjie.modules.finance.service.dto.FundFlowingDTO;
import me.zhengjie.modules.finance.service.dto.FundFlowingQueryCriteria;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

/**
* @author nmk
* @date 2019-08-22
*/
//@CacheConfig(cacheNames = "journalAccountOfCapital")
public interface FundFlowingService {

    /**
     * 查询全部导出内容
     * @param criteria
     * @return
     */
    Object queryExportAll(FundFlowingQueryCriteria criteria);

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(FundFlowingQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(FundFlowingQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    FundFlowingDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    FundFlowingDTO create(FundFlowing resources);

    /**
     * create
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)新增成本
    FundFlowingDTO createByPostCost(ParkCost resources, String value, BigDecimal money,BigDecimal substactMoney);

    /**
     * create
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)新增收入
    FundFlowingDTO createByPostPevenue(ParkPevenue resources, String value, BigDecimal money,BigDecimal substactMoney);

    /**
     * update
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(FundFlowing resources);

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

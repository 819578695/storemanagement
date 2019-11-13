package me.zhengjie.modules.basic_management.Tenantinformation.service;

import me.zhengjie.modules.basic_management.Tenantinformation.domain.Tenantinformation;
import me.zhengjie.modules.basic_management.Tenantinformation.service.dto.ParticularsDTO;
import me.zhengjie.modules.basic_management.Tenantinformation.service.dto.TenantinformationDTO;
import me.zhengjie.modules.basic_management.Tenantinformation.service.dto.TenantinformationQueryCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
* @author zlk
* @date 2019-08-12
*/
//@CacheConfig(cacheNames = "tenantinformation")
public interface TenantinformationService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(TenantinformationQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(TenantinformationQueryCriteria criteria);

    /**
     * 全部导出
     * @param criteria
     * @return
     */
    Object gettenantinformationAll(TenantinformationQueryCriteria criteria);

    List<ParticularsDTO> queryParticulars(Long id);

    Object findByDeptId(Long deptId);

    Object findByArchivesmouthsmanagementId(Long id);

    /**
     * findById
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    TenantinformationDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    TenantinformationDTO create(Tenantinformation resources);

    /**
     * update
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(Tenantinformation resources);

    /**
     * delete
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);
}

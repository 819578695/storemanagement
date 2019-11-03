package me.zhengjie.modules.business.service;

import me.zhengjie.modules.business.domain.RentContract;
import me.zhengjie.modules.business.service.dto.RentContractDTO;
import me.zhengjie.modules.business.service.dto.RentContractQueryCriteria;
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;

/**
* @author kang
* @date 2019-08-28
*/
//@CacheConfig(cacheNames = "rentContract")
public interface RentContractService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(RentContractQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(RentContractQueryCriteria criteria);


    Object findByDeptId(Long deptId);

    /**
     * findById
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    RentContractDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    RentContractDTO create(RentContract resources);

    /**
     * update
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(RentContract resources);

    /**
     * delete
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);

    /**
     * 上传文件
     *
     * @param multipartRequest
     * @return
     * @throws IOException
     */
    String uploadFile(MultipartHttpServletRequest multipartRequest,String contractNo) throws Exception;

    /**
     * 审核
     * @param vertifys
     * @return
     */
    void vertify(Long[] vertifys,Integer  status);
}

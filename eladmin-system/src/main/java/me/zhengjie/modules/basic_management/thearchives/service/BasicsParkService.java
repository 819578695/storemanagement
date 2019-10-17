package me.zhengjie.modules.basic_management.thearchives.service;

import me.zhengjie.domain.Picture;
import me.zhengjie.modules.basic_management.thearchives.domain.BasicsPark;
import me.zhengjie.modules.basic_management.thearchives.service.dto.BasicsParkContractDTO;
import me.zhengjie.modules.basic_management.thearchives.service.dto.BasicsParkDTO;
import me.zhengjie.modules.basic_management.thearchives.service.dto.BasicsParkQueryCriteria;
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.List;

/**
* @author zlk
* @date 2019-08-26
*/
//@CacheConfig(cacheNames = "basicsPark")
public interface BasicsParkService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(BasicsParkQueryCriteria criteria, Pageable pageable);

    public List<BasicsParkContractDTO> basicsParkContract(Long deptId);

    Object findByDeptId(Long deptId);
    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(BasicsParkQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    BasicsParkDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    BasicsParkDTO create(BasicsPark resources);

    /**
     * update
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(BasicsPark resources);

    /**
     * update
     * @param
     */
    //@CacheEvict(allEntries = true)
    /*BasicsPark updatesc(MultipartFile file);*/

    /**
     * 上传文件
     *
     * @param multipartRequest
     * @return
     * @throws IOException
     */
    String uploadPicture(MultipartHttpServletRequest multipartRequest, String contractNo) throws Exception;

    /**
     * delete
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);
}

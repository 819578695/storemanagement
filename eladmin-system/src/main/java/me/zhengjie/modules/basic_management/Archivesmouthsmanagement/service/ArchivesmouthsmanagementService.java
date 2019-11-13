package me.zhengjie.modules.basic_management.Archivesmouthsmanagement.service;

import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.domain.Archivesmouthsmanagement;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.service.dto.ArchiveDto;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.service.dto.ArchiveUpLoadDto;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.service.dto.ArchivesmouthsmanagementDTO;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.service.dto.ArchivesmouthsmanagementQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

//@CacheConfig(cacheNames = "Archivesmouthsmanagement")
public interface ArchivesmouthsmanagementService {

    /**
     * queryAll 分页
     * @param criteria
     * @param pageable
     * @return
     */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(ArchivesmouthsmanagementQueryCriteria criteria, Pageable pageable);

    Map publiccity(Long id);
    List<ArchivesmouthsmanagementDTO> publicWechatId(ArchivesmouthsmanagementQueryCriteria criteria);

    /**
     * queryAll 不分页
     * @param criteria
     * @return
     */
    //@Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(ArchivesmouthsmanagementQueryCriteria criteria);

    Object findByDeptId(Long deptId);
    Object findByDeptIdAndTenementNameIsNull(Long deptId);
    Object findByDeptIdAndTenementNameIsNotNull(Long deptId);
    Object getarchivesmouthsmanagementAll(ArchivesmouthsmanagementQueryCriteria criteria);


    /**
     * findById
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    ArchivesmouthsmanagementDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    ArchivesmouthsmanagementDTO create(Archivesmouthsmanagement resources);

    /**
     * update
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(Archivesmouthsmanagement resources);

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
    String uploadPictureExamine(MultipartHttpServletRequest multipartRequest, String contractNo) throws Exception;

    /**
     * 计算出租率
     * @param deptId
     */
    Object queryOccupancyRate(Long deptId);

    /**
     * 文件批量上传
     * @param resource
     */
    void batchUpload(List<ArchiveUpLoadDto> resource);
}

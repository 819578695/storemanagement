package me.zhengjie.modules.basic_management.Archivesmouthsmanagement.service.impl;

import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.domain.Archivesmouthsmanagement;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.repository.ArchivesmouthsmanagementRepository;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.service.ArchivesmouthsmanagementService;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.service.dto.ArchiveDto;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.service.dto.ArchivesmouthsmanagementDTO;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.service.dto.ArchivesmouthsmanagementQueryCriteria;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.service.mapper.ArchiveMapper;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.service.mapper.ArchivesmouthsmanagementMapper;
import me.zhengjie.modules.basic_management.city.repository.CityRepository;
import me.zhengjie.modules.system.repository.DeptRepository;
import me.zhengjie.modules.system.repository.DictDetailRepository;
import me.zhengjie.utils.FileUtil;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import me.zhengjie.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ArchivesmouthsmanagementServiceImpl implements ArchivesmouthsmanagementService {

    @Autowired
    private ArchivesmouthsmanagementRepository archivesmouthsmanagementRepository;

    @Autowired
    @SuppressWarnings("all")
    private ArchivesmouthsmanagementMapper archivesmouthsmanagementMapper;

    @Autowired
    @SuppressWarnings("all")
    private ArchiveMapper archivesMapper;

    @Autowired
    private DictDetailRepository dictDetailRepository;

    @Autowired
    private DeptRepository deptRepository;
    @Autowired
    private CityRepository cityRepository;

    @Value("${httpUrl}")
    private String httpUrl; //服务器文件地址
    @Value("${filePath}")
    private String filePath; //文件路径

    @Override
    public  Object queryAll(ArchivesmouthsmanagementQueryCriteria criteria, Pageable pageable){
        Page<Archivesmouthsmanagement> page = archivesmouthsmanagementRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        List<ArchivesmouthsmanagementDTO> archivesmouthsmanagementDTOS = new ArrayList<>();
        for (Archivesmouthsmanagement archivesmouthsmanagement : page.getContent()){
            archivesmouthsmanagementDTOS.add(archivesmouthsmanagementMapper.toDto(archivesmouthsmanagement,deptRepository.findById(archivesmouthsmanagement.getDept().getId()).get(),dictDetailRepository.findById(archivesmouthsmanagement.getDictDetail().getId()).get()));
        }
        return PageUtil.toPage(archivesmouthsmanagementDTOS,page.getTotalElements());
    }

    @Override
    public  Map publiccity(Long id){
        //微信小程序获得所有信息和总和
        Map map = new HashMap();
        List<Archivesmouthsmanagement> list = archivesmouthsmanagementRepository.findByAreaId(id);
        /*int num = archivesmouthsmanagementRepository.findBycoum(id);*/
        int num = 0;
        List<ArchiveDto> list1 = new ArrayList<>();
        for (Archivesmouthsmanagement lists : list){
            if (lists.getTenementName()==null||lists.getTenementName().equals("")){
            list1.add(archivesMapper.toDtos(lists,dictDetailRepository.findById(lists.getDictDetail().getId()).get(),cityRepository.findById(lists.getCity().getAreaId()).get(),deptRepository.findById(lists.getDept().getId()).get()));
            num = num+1;
                }
        }
        map.put("list",list1);
        map.put("num",num);
        return map;
    }
    @Override
    public List<ArchivesmouthsmanagementDTO> publicWechatId(ArchivesmouthsmanagementQueryCriteria criteria){
        return archivesmouthsmanagementMapper.toDto(archivesmouthsmanagementRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public Object queryAll(ArchivesmouthsmanagementQueryCriteria criteria){
        return archivesmouthsmanagementMapper.toDto(archivesmouthsmanagementRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public ArchivesmouthsmanagementDTO findById(Long id) {
        Optional<Archivesmouthsmanagement> tenantinformation = archivesmouthsmanagementRepository.findById(id);
        ValidationUtil.isNull(tenantinformation,"Archivesmouthsmanagement","id",id);
        return archivesmouthsmanagementMapper.toDto(tenantinformation.get());
    }
    @Override
    public Object findByDeptId(Long deptId) {
        return archivesmouthsmanagementMapper.toDto(deptId==1?archivesmouthsmanagementRepository.findAll():archivesmouthsmanagementRepository.findByDeptId(deptId));
    }

    @Override
    public Object findByDeptIdAndTenementNameIsNull(Long deptId) {
        return archivesmouthsmanagementMapper.toDto(deptId==1?archivesmouthsmanagementRepository.findByTenementNameIsNull():archivesmouthsmanagementRepository.findByDeptIdAndTenementNameIsNull(deptId));
    }

    @Override
    public Object findByDeptIdAndTenementNameIsNotNull(Long deptId) {
        return archivesmouthsmanagementMapper.toDto(deptId==1?archivesmouthsmanagementRepository.findByTenementNameIsNotNull():archivesmouthsmanagementRepository.findByDeptIdAndTenementNameIsNotNull(deptId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ArchivesmouthsmanagementDTO create(Archivesmouthsmanagement resources) {
        //判断档口是否唯一
        try {
            return archivesmouthsmanagementMapper.toDto(archivesmouthsmanagementRepository.save(resources));
        }catch (DataIntegrityViolationException s){
            throw new BadRequestException("您输入的档口已经存在,无法进行添加");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Archivesmouthsmanagement resources) {
        //判断档口是否唯一
        try {
            Optional<Archivesmouthsmanagement> optionalTenantinformation = archivesmouthsmanagementRepository.findById(resources.getId());
            ValidationUtil.isNull( optionalTenantinformation,"Archivesmouthsmanagement","id",resources.getId());
            Archivesmouthsmanagement archivesmouthsmanagement = optionalTenantinformation.get();
            archivesmouthsmanagement.copy(resources);
            archivesmouthsmanagementRepository.save(archivesmouthsmanagement);
        }catch (DataIntegrityViolationException s){
            throw new BadRequestException("您输入的档口已经存在,无法进行修改");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        archivesmouthsmanagementRepository.deleteById(id);
    }

    @Override
    public String uploadPictureExamine(MultipartHttpServletRequest multipartRequest, String contractNo) throws Exception {
        //上传文件
        String imgUrl = FileUtil.uploadUtil(multipartRequest, httpUrl, filePath, "upfile", "/contract/pictureExamine",contractNo );
        try {
            return imgUrl;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

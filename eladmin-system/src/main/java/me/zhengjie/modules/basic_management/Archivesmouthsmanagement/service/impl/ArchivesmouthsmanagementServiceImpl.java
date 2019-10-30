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
import me.zhengjie.modules.basic_management.thearchives.domain.BasicsPark;
import me.zhengjie.modules.basic_management.thearchives.repository.BasicsParkRepository;
import me.zhengjie.modules.business.repository.LeaseContractRepository;
import me.zhengjie.modules.finance.repository.MarginRepository;
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
    @Autowired
    private MarginRepository marginRepository;
    @Autowired
    private LeaseContractRepository leaseContractRepository;
    @Autowired
    private BasicsParkRepository basicsParkRepository;

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
        BasicsPark basicsPark = basicsParkRepository.findByDept(id);
        /*int num = archivesmouthsmanagementRepository.findBycoum(id);*/
        int num = 0;
        List<ArchiveDto> list1 = new ArrayList<>();
        for (Archivesmouthsmanagement lists : list){
            if (lists.getTenementName()==null||lists.getTenementName().equals("")){
            list1.add(archivesMapper.toDtos(lists,dictDetailRepository.findById(lists.getDictDetail().getId()).get(),deptRepository.findById(lists.getDept().getId()).get()));
            num = num+1;
          }
        }
        map.put("list",list1);
        map.put("num",num);
        if(basicsPark!=null){
            map.put("picture",basicsPark.getFileName().equals("")?null:basicsPark.getFileName());
        }
        else{
            map.put("picture",null);
        }
        return map;
    }
    @Override
    public List<ArchivesmouthsmanagementDTO> publicWechatId(ArchivesmouthsmanagementQueryCriteria criteria){
        return archivesmouthsmanagementRepository.wechatStall(criteria.getId());
        //return archivesmouthsmanagementMapper.toDto(archivesmouthsmanagementRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
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
        Object o = archivesmouthsmanagementRepository.HousenumberExistDeptId(resources.getHousenumber(), resources.getDept().getId());
        if (o == null){
            return archivesmouthsmanagementMapper.toDto(archivesmouthsmanagementRepository.save(resources));
        }
        throw new BadRequestException("您输入的档口已经存在,无法进行添加");

        //判断档口是否唯一   注释后数据库索引已经删除
        /*try {
            return archivesmouthsmanagementMapper.toDto(archivesmouthsmanagementRepository.save(resources));
        }catch (DataIntegrityViolationException s){
            throw new BadRequestException("您输入的档口已经存在,无法进行添加");
        }*/
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
        if (leaseContractRepository.findByStallId(id).size() != 0){
            throw new BadRequestException("当前档口已出租,请解除绑定后删除!");
        }
        if( marginRepository.findByHouseId(id) != null ){
            throw new BadRequestException("当前档口已产生资金流水无法删除,请联系管理员删除!");
        }
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

    @Override
    public Object queryOccupancyRate(Long deptId) {
        Object rate;
        if (deptId!= 1){
            //如非总部根据公司ID进行查询
            rate = archivesmouthsmanagementRepository.findByOccupancyRate(deptId);
        }else {
            rate = archivesmouthsmanagementRepository.findByOccupancyRateAll();
        }
        return rate+"%";
    }

}

package me.zhengjie.modules.basic_management.Archivesmouthsmanagement.service.impl;

import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.domain.Archivesmouthsmanagement;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.repository.ArchivesmouthsmanagementRepository;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.service.ArchivesmouthsmanagementService;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.service.dto.ArchivesmouthsmanagementDTO;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.service.dto.ArchivesmouthsmanagementQueryCriteria;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.service.mapper.ArchivesmouthsmanagementMapper;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import me.zhengjie.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ArchivesmouthsmanagementServiceImpl implements ArchivesmouthsmanagementService {

    @Autowired
    private ArchivesmouthsmanagementRepository archivesmouthsmanagementRepository;

    @Autowired
    private ArchivesmouthsmanagementMapper archivesmouthsmanagementMapper;

    @Override
    public  Object queryAll(ArchivesmouthsmanagementQueryCriteria criteria, Pageable pageable){
        Page<Archivesmouthsmanagement> page = archivesmouthsmanagementRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(archivesmouthsmanagementMapper::toDto));
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
        return archivesmouthsmanagementMapper.toDto(deptId==0?archivesmouthsmanagementRepository.findAll():archivesmouthsmanagementRepository.findByDeptId(deptId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ArchivesmouthsmanagementDTO create(Archivesmouthsmanagement resources) {
        return archivesmouthsmanagementMapper.toDto(archivesmouthsmanagementRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Archivesmouthsmanagement resources) {
        Optional<Archivesmouthsmanagement> optionalTenantinformation = archivesmouthsmanagementRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalTenantinformation,"Archivesmouthsmanagement","id",resources.getId());
        Archivesmouthsmanagement archivesmouthsmanagement = optionalTenantinformation.get();
        archivesmouthsmanagement.copy(resources);
        archivesmouthsmanagementRepository.save(archivesmouthsmanagement);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        archivesmouthsmanagementRepository.deleteById(id);
    }

}

package me.zhengjie.modules.basic_management.Tenantinformation.service.impl;

import me.zhengjie.modules.basic_management.Tenantinformation.domain.Tenantinformation;
import me.zhengjie.modules.basic_management.Tenantinformation.service.mapper.TenantinformationMapper;
import me.zhengjie.modules.system.repository.DeptRepository;
import me.zhengjie.modules.system.repository.DictDetailRepository;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.basic_management.Tenantinformation.repository.TenantinformationRepository;
import me.zhengjie.modules.basic_management.Tenantinformation.service.TenantinformationService;
import me.zhengjie.modules.basic_management.Tenantinformation.service.dto.TenantinformationDTO;
import me.zhengjie.modules.basic_management.Tenantinformation.service.dto.TenantinformationQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;

/**
* @author zlk
* @date 2019-08-12
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TenantinformationServiceImpl implements TenantinformationService {

    @Autowired
    private TenantinformationRepository tenantinformationRepository;

    @Autowired
    private TenantinformationMapper tenantinformationMapper;

    @Autowired
    private DictDetailRepository dictDetailRepository;

    @Autowired
    private DeptRepository deptRepository;

    @Override
    public Object queryAll(TenantinformationQueryCriteria criteria, Pageable pageable){
        Page<Tenantinformation> page = tenantinformationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        List<TenantinformationDTO> tenantinformations = new ArrayList<>();
        for (Tenantinformation tenantinformation : page.getContent()){
            tenantinformations.add(tenantinformationMapper.toDto(tenantinformation,deptRepository.findById(tenantinformation.getDept().getId()).get(),dictDetailRepository.findById(tenantinformation.getDictDetail().getId()).get()));
        }
        return PageUtil.toPage(tenantinformations,page.getTotalElements());
    }

    @Override
    public Object queryAll(TenantinformationQueryCriteria criteria){
        return tenantinformationMapper.toDto(tenantinformationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }
    @Override
    public Object findByDeptId(Long deptId) {
        return tenantinformationMapper.toDto(deptId==1?tenantinformationRepository.findAll():tenantinformationRepository.findByDeptId(deptId));
    }
    @Override
    public TenantinformationDTO findById(Long id) {
        Optional<Tenantinformation> tenantinformation = tenantinformationRepository.findById(id);
        ValidationUtil.isNull(tenantinformation,"Tenantinformation","id",id);
        return tenantinformationMapper.toDto(tenantinformation.get());
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public TenantinformationDTO create(Tenantinformation resources) {
        return tenantinformationMapper.toDto(tenantinformationRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Tenantinformation resources) {
        Optional<Tenantinformation> optionalTenantinformation = tenantinformationRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalTenantinformation,"Tenantinformation","id",resources.getId());
        Tenantinformation tenantinformation = optionalTenantinformation.get();
        tenantinformation.copy(resources);
        tenantinformationRepository.save(tenantinformation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        tenantinformationRepository.deleteById(id);
    }
}

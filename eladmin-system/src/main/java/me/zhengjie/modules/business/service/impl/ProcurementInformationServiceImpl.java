package me.zhengjie.modules.business.service.impl;

import me.zhengjie.modules.business.domain.ProcurementInformation;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.business.repository.ProcurementInformationRepository;
import me.zhengjie.modules.business.service.ProcurementInformationService;
import me.zhengjie.modules.business.service.dto.ProcurementInformationDTO;
import me.zhengjie.modules.business.service.dto.ProcurementInformationQueryCriteria;
import me.zhengjie.modules.business.service.mapper.ProcurementInformationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;

/**
* @author kang
* @date 2019-08-20
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ProcurementInformationServiceImpl implements ProcurementInformationService {

    @Autowired
    private ProcurementInformationRepository procurementInformationRepository;

    @Autowired
    private ProcurementInformationMapper procurementInformationMapper;

    @Override
    public Object queryAll(ProcurementInformationQueryCriteria criteria, Pageable pageable){
        Page<ProcurementInformation> page = procurementInformationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(procurementInformationMapper::toDto));
    }

    @Override
    public Object queryAll(ProcurementInformationQueryCriteria criteria){
        return procurementInformationMapper.toDto(procurementInformationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public ProcurementInformationDTO findById(Integer id) {
        Optional<ProcurementInformation> procurementInformation = procurementInformationRepository.findById(id);
        ValidationUtil.isNull(procurementInformation,"ProcurementInformation","id",id);
        return procurementInformationMapper.toDto(procurementInformation.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProcurementInformationDTO create(ProcurementInformation resources) {
        return procurementInformationMapper.toDto(procurementInformationRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ProcurementInformation resources) {
        Optional<ProcurementInformation> optionalProcurementInformation = procurementInformationRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalProcurementInformation,"ProcurementInformation","id",resources.getId());
        ProcurementInformation procurementInformation = optionalProcurementInformation.get();
        procurementInformation.copy(resources);
        procurementInformationRepository.save(procurementInformation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        procurementInformationRepository.deleteById(id);
    }
}
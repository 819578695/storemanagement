package me.zhengjie.modules.business.service.impl;

import me.zhengjie.modules.business.domain.ProcurementInformation;
import me.zhengjie.modules.business.repository.ReceiptPaymentAccountRepository;
import me.zhengjie.modules.business.service.ReceiptPaymentAccountService;
import me.zhengjie.modules.business.service.dto.ReceiptPaymentAccountDTO;
import me.zhengjie.modules.business.service.mapper.ReceiptPaymentAccountMapper;
import me.zhengjie.modules.system.domain.Dept;
import me.zhengjie.modules.system.domain.Job;
import me.zhengjie.modules.system.repository.DeptRepository;
import me.zhengjie.modules.system.repository.DictDetailRepository;
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

import java.util.ArrayList;
import java.util.List;
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
    @Autowired
    private  ReceiptPaymentAccountRepository receiptPaymentAccountRepository;
    @Autowired
    private DictDetailRepository dictDetailRepository;
    @Autowired
    private DeptRepository deptRepository;

    @Override
    public Object queryAll(ProcurementInformationQueryCriteria criteria, Pageable pageable){
        Page<ProcurementInformation> page = procurementInformationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        List<ProcurementInformationDTO> procurementInformationDTOS = new ArrayList<>();
        for (ProcurementInformation procurementInformation : page.getContent()) {
            procurementInformationDTOS.add(procurementInformationMapper.toDto(procurementInformation,receiptPaymentAccountRepository.findAllById(procurementInformation.getReceiptPaymentAccount().getId()),dictDetailRepository.findById(procurementInformation.getDictDetail().getId()).get(),deptRepository.findById(procurementInformation.getDept().getId()).get()));
        }
        return PageUtil.toPage(procurementInformationDTOS,page.getTotalElements());
    }

    @Override
    public Object queryAll(ProcurementInformationQueryCriteria criteria){
        return procurementInformationMapper.toDto(procurementInformationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public ProcurementInformationDTO findById(Long id) {
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
    public void delete(Long id) {
        procurementInformationRepository.deleteById(id);
    }
}

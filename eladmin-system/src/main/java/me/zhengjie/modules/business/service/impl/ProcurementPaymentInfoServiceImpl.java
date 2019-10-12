package me.zhengjie.modules.business.service.impl;

import me.zhengjie.modules.business.domain.ProcurementPaymentInfo;
import me.zhengjie.modules.business.repository.ReceiptPaymentAccountRepository;
import me.zhengjie.modules.system.repository.DictDetailRepository;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.business.repository.ProcurementPaymentInfoRepository;
import me.zhengjie.modules.business.service.ProcurementPaymentInfoService;
import me.zhengjie.modules.business.service.dto.ProcurementPaymentInfoDTO;
import me.zhengjie.modules.business.service.dto.ProcurementPaymentInfoQueryCriteria;
import me.zhengjie.modules.business.service.mapper.ProcurementPaymentInfoMapper;
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
* @date 2019-10-09
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ProcurementPaymentInfoServiceImpl implements ProcurementPaymentInfoService {

    @Autowired
    private ProcurementPaymentInfoRepository procurementPaymentInfoRepository;

    @Autowired
    private ProcurementPaymentInfoMapper procurementPaymentInfoMapper;
    @Autowired
    private ReceiptPaymentAccountRepository receiptPaymentAccountRepository;
    @Autowired
    private DictDetailRepository dictDetailRepository;

    @Override
    public Object queryAll( ProcurementPaymentInfoQueryCriteria criteria, Pageable pageable){
        Page<ProcurementPaymentInfo> page = procurementPaymentInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        List<ProcurementPaymentInfoDTO> procurementInformationDTOS = new ArrayList<>();
        for (ProcurementPaymentInfo procurementInformation : page.getContent()) {
            procurementInformationDTOS.add(procurementPaymentInfoMapper.toDto(procurementInformation,procurementInformation.getReceiptPaymentAccount()==null?null:receiptPaymentAccountRepository.findById(procurementInformation.getReceiptPaymentAccount().getId()).get(),procurementInformation.getDictDetail()==null?null:dictDetailRepository.findById(procurementInformation.getDictDetail().getId()).get()));
        }
        return PageUtil.toPage(procurementInformationDTOS,page.getTotalElements());
    }
    @Override
    public Object queryAll(ProcurementPaymentInfoQueryCriteria criteria){
        return procurementPaymentInfoMapper.toDto(procurementPaymentInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public Object findByProcurement(Long procurementInformationId) {
        List<ProcurementPaymentInfo> page = procurementPaymentInfoRepository.findByProcurementInformationId(procurementInformationId);
        List<ProcurementPaymentInfoDTO> procurementInformationDTOS = new ArrayList<>();
        for (ProcurementPaymentInfo procurementInformation : page) {
            procurementInformationDTOS.add(procurementPaymentInfoMapper.toDto(procurementInformation,procurementInformation.getReceiptPaymentAccount()==null?null:receiptPaymentAccountRepository.findById(procurementInformation.getReceiptPaymentAccount().getId()).get(),procurementInformation.getDictDetail()==null?null:dictDetailRepository.findById(procurementInformation.getDictDetail().getId()).get()));
        }
        return PageUtil.toPage(procurementInformationDTOS,null);
    }

    @Override
    public ProcurementPaymentInfoDTO findById(Long id) {
        Optional<ProcurementPaymentInfo> procurementPaymentInfo = procurementPaymentInfoRepository.findById(id);
        ValidationUtil.isNull(procurementPaymentInfo,"ProcurementPaymentInfo","id",id);
        return procurementPaymentInfoMapper.toDto(procurementPaymentInfo.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProcurementPaymentInfoDTO create(ProcurementPaymentInfo resources) {
        return procurementPaymentInfoMapper.toDto(procurementPaymentInfoRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ProcurementPaymentInfo resources) {
        Optional<ProcurementPaymentInfo> optionalProcurementPaymentInfo = procurementPaymentInfoRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalProcurementPaymentInfo,"ProcurementPaymentInfo","id",resources.getId());
        ProcurementPaymentInfo procurementPaymentInfo = optionalProcurementPaymentInfo.get();
        procurementPaymentInfo.copy(resources);
        procurementPaymentInfoRepository.save(procurementPaymentInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        procurementPaymentInfoRepository.deleteById(id);
    }
}

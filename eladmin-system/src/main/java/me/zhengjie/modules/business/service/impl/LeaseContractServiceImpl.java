package me.zhengjie.modules.business.service.impl;

import me.zhengjie.modules.business.domain.LeaseContract;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.business.repository.LeaseContractRepository;
import me.zhengjie.modules.business.service.LeaseContractService;
import me.zhengjie.modules.business.service.dto.LeaseContractDTO;
import me.zhengjie.modules.business.service.dto.LeaseContractQueryCriteria;
import me.zhengjie.modules.business.service.mapper.LeaseContractMapper;
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
* @author nmk
* @date 2019-08-29
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class LeaseContractServiceImpl implements LeaseContractService {

    @Autowired
    private LeaseContractRepository leaseContractRepository;

    @Autowired
    private LeaseContractMapper leaseContractMapper;

    @Override
    public Object queryAll(LeaseContractQueryCriteria criteria, Pageable pageable){
        Page<LeaseContract> page = leaseContractRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(leaseContractMapper::toDto));
    }

    @Override
    public Object queryAll(LeaseContractQueryCriteria criteria){
        return leaseContractMapper.toDto(leaseContractRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public LeaseContractDTO findById(Long id) {
        Optional<LeaseContract> leaseContract = leaseContractRepository.findById(id);
        ValidationUtil.isNull(leaseContract,"LeaseContract","id",id);
        return leaseContractMapper.toDto(leaseContract.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LeaseContractDTO create(LeaseContract resources) {
        return leaseContractMapper.toDto(leaseContractRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(LeaseContract resources) {
        Optional<LeaseContract> optionalLeaseContract = leaseContractRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalLeaseContract,"LeaseContract","id",resources.getId());
        LeaseContract leaseContract = optionalLeaseContract.get();
        leaseContract.copy(resources);
        leaseContractRepository.save(leaseContract);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        leaseContractRepository.deleteById(id);
    }
}
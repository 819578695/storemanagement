package me.zhengjie.modules.business.service.impl;

import me.zhengjie.modules.business.domain.RentContract;
import me.zhengjie.modules.system.repository.DeptRepository;
import me.zhengjie.modules.system.service.mapper.DeptMapper;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.business.repository.RentContractRepository;
import me.zhengjie.modules.business.service.RentContractService;
import me.zhengjie.modules.business.service.dto.RentContractDTO;
import me.zhengjie.modules.business.service.dto.RentContractQueryCriteria;
import me.zhengjie.modules.business.service.mapper.RentContractMapper;
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
* @date 2019-08-28
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RentContractServiceImpl implements RentContractService {

    @Autowired
    private RentContractRepository rentContractRepository;

    @Autowired
    private RentContractMapper rentContractMapper;
    @Autowired
    private DeptRepository deptRepository;

    @Override
    public Object queryAll(RentContractQueryCriteria criteria, Pageable pageable){
        Page<RentContract> page = rentContractRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        List<RentContractDTO> rentContractDTOS = new ArrayList<>();
        for (RentContract rentContract : page.getContent()) {
            rentContractDTOS.add(rentContractMapper.toDto(rentContract,deptRepository.findAllById(rentContract.getDept().getId())));
        }
        return PageUtil.toPage(page.map(rentContractMapper::toDto));
    }

    @Override
    public Object queryAll(RentContractQueryCriteria criteria){
        return rentContractMapper.toDto(rentContractRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public RentContractDTO findById(Long id) {
        Optional<RentContract> rentContract = rentContractRepository.findById(id);
        ValidationUtil.isNull(rentContract,"RentContract","id",id);
        return rentContractMapper.toDto(rentContract.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RentContractDTO create(RentContract resources) {
        return rentContractMapper.toDto(rentContractRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(RentContract resources) {
        Optional<RentContract> optionalRentContract = rentContractRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalRentContract,"RentContract","id",resources.getId());
        RentContract rentContract = optionalRentContract.get();
        rentContract.copy(resources);
        rentContractRepository.save(rentContract);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        rentContractRepository.deleteById(id);
    }
}

package me.zhengjie.modules.business.service.impl;

import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.domain.Archivesmouthsmanagement;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.repository.ArchivesmouthsmanagementRepository;
import me.zhengjie.modules.basic_management.Tenantinformation.repository.TenantinformationRepository;
import me.zhengjie.modules.business.domain.LeaseContract;
import me.zhengjie.modules.business.domain.ParkPevenue;
import me.zhengjie.modules.business.repository.ParkPevenueRepository;
import me.zhengjie.modules.system.repository.DeptRepository;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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

    @Autowired
    private DeptRepository deptRepository;

    @Autowired
    private TenantinformationRepository tenantinformationRepository;

    @Autowired
    private ArchivesmouthsmanagementRepository archivesmouthsmanagementRepository;

    @Autowired
    private ParkPevenueRepository parkPevenueRepository;



    @Override
    public Object queryAll(LeaseContractQueryCriteria criteria, Pageable pageable){
        Page<LeaseContract> page = leaseContractRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        List<LeaseContractDTO> leaseContractDTOS = new ArrayList<>();
        for (LeaseContract leaseContract : page.getContent()) {
            List<ParkPevenue> parkPevenues =parkPevenueRepository.findByLeaseContractIdAndDeptId(leaseContract.getId(),leaseContract.getDept().getId());
            BigDecimal totalMoney = new BigDecimal(0);
            for(ParkPevenue parkPevenue : parkPevenues){
                //bigdecimal 求和(未缴费用)
                totalMoney = totalMoney.add(parkPevenue.getHouseRent());
                leaseContract.setPaymentedExpenses(totalMoney);

            }
            leaseContractDTOS.add(leaseContractMapper.toDto(leaseContract,archivesmouthsmanagementRepository.findById(leaseContract.getArchivesmouthsmanagement().getId()).get(),deptRepository.findById(leaseContract.getDept().getId()).get(),tenantinformationRepository.findById(leaseContract.getTenantinformation().getId()).get()));

        }
        return PageUtil.toPage(leaseContractDTOS,page.getTotalElements());
    }

    @Override
    public Object queryAll(LeaseContractQueryCriteria criteria){
        return leaseContractMapper.toDto(leaseContractRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public Object findByDeptId(Long deptId) {
        return leaseContractMapper.toDto(deptId==1?leaseContractRepository.findAll():leaseContractRepository.findByDeptId(deptId));
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

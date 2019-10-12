package me.zhengjie.modules.business.service.impl;

import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.domain.Archivesmouthsmanagement;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.repository.ArchivesmouthsmanagementRepository;
import me.zhengjie.modules.basic_management.Tenantinformation.domain.Tenantinformation;
import me.zhengjie.modules.basic_management.Tenantinformation.repository.TenantinformationRepository;
import me.zhengjie.modules.business.domain.LeaseContract;
import me.zhengjie.modules.business.domain.ParkPevenue;
import me.zhengjie.modules.business.domain.ProcurementPaymentInfo;
import me.zhengjie.modules.business.repository.ParkPevenueRepository;
import me.zhengjie.modules.business.repository.ProcurementPaymentInfoRepository;
import me.zhengjie.modules.security.security.JwtUser;
import me.zhengjie.modules.system.repository.DeptRepository;
import me.zhengjie.modules.system.repository.DictDetailRepository;
import me.zhengjie.utils.*;
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
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    @Autowired
    private DictDetailRepository dictDetailRepository;




    @Override
    public Object queryAll(LeaseContractQueryCriteria criteria, Pageable pageable){
        Page<LeaseContract> page = leaseContractRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        List<LeaseContractDTO> leaseContractDTOS = new ArrayList<>();
        for (LeaseContract leaseContract : page.getContent()) {
            List<ParkPevenue> parkPevenues =parkPevenueRepository.findByLeaseContractIdAndType(leaseContract.getId());
            BigDecimal totalMoney = new BigDecimal(0);
            for(ParkPevenue parkPevenue : parkPevenues){
                //bigdecimal 求和(未缴费用)
                    totalMoney = totalMoney.add(new BigDecimal(StringUtils.isNotNullBigDecimal(parkPevenue.getHouseRent())+StringUtils.isNotNullBigDecimal(parkPevenue.getPropertyRent())+StringUtils.isNotNullBigDecimal(parkPevenue.getWaterRent())+StringUtils.isNotNullBigDecimal(parkPevenue.getElectricityRent())+StringUtils.isNotNullBigDecimal(parkPevenue.getSanitationRent())+StringUtils.isNotNullBigDecimal(parkPevenue.getLiquidatedRent())+StringUtils.isNotNullBigDecimal(parkPevenue.getLateRent())+StringUtils.isNotNullBigDecimal(parkPevenue.getGroundPoundRent())+StringUtils.isNotNullBigDecimal(parkPevenue.getManagementRent())+StringUtils.isNotNullBigDecimal(parkPevenue.getParkingRent())));
                    leaseContract.setUnpaidExpenses(totalMoney);
            }
            leaseContractDTOS.add(leaseContractMapper.toDto(leaseContract,leaseContract.getArchivesmouthsmanagement()==null?null:archivesmouthsmanagementRepository.findById(leaseContract.getArchivesmouthsmanagement().getId()).get(),leaseContract.getDept()==null?null:deptRepository.findById(leaseContract.getDept().getId()).get(),leaseContract.getTenantinformation()==null?null:tenantinformationRepository.findById(leaseContract.getTenantinformation().getId()).get(),leaseContract.getPayCycle()==null?null:dictDetailRepository.findById(leaseContract.getPayCycle().getId()).get()));
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
        if (resources!=null){
             //新增合同會綁定租戶和檔口
            Archivesmouthsmanagement archivesmouthsmanagement = archivesmouthsmanagementRepository.findById(resources.getArchivesmouthsmanagement().getId()).get();
            Tenantinformation tenantinformation= tenantinformationRepository.findById(resources.getTenantinformation().getId()).get();
            //修改合同对应的档口和租户
           if (archivesmouthsmanagement!=null&&tenantinformation!=null){
                archivesmouthsmanagement.setTenementName(tenantinformation.getLinkman());
                tenantinformation.setArchivesmouthsmanagement(archivesmouthsmanagement);
                archivesmouthsmanagementRepository.save(archivesmouthsmanagement);
                tenantinformationRepository.save(tenantinformation);
           }
            JwtUser jwtUser =(JwtUser) SecurityUtils.getUserDetails();
            Long no = 0001l;
            if (leaseContractRepository.findByNewcontractNo(resources.getDept().getId())!=null){
                no=Long.valueOf(leaseContractRepository.findByNewcontractNo(resources.getDept().getId()))+0001l;
            }
            resources.setContractNo(jwtUser.getDeptNo()+ StringUtils.getCurentDate()+new DecimalFormat("0000").format(no));
        }
        return leaseContractMapper.toDto(leaseContractRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(LeaseContract resources) {
        Optional<LeaseContract> optionalLeaseContract = leaseContractRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalLeaseContract,"LeaseContract","id",resources.getId());
        LeaseContract leaseContract = optionalLeaseContract.get();
        if (resources!=null&&resources!=leaseContract){
            //新增合同會綁定租戶和檔口
            Archivesmouthsmanagement archivesmouthsmanagement = archivesmouthsmanagementRepository.findById(resources.getArchivesmouthsmanagement().getId()).get();
            Tenantinformation tenantinformation= tenantinformationRepository.findById(resources.getTenantinformation().getId()).get();
           if (resources.getArchivesmouthsmanagement()!=null){
               //修改合同对应的档口和租户
               //如果档口修改则将修改的id进行判断
               if(resources.getArchivesmouthsmanagement().getId()!=leaseContract.getArchivesmouthsmanagement().getId()){
                   List<LeaseContract> leaseContractList = leaseContractRepository.findByArchivesmouthsmanagementId(resources.getArchivesmouthsmanagement().getId());
                   if(leaseContractList.size()>0){
                       throw new BadRequestException("您选择的档口已经出租,无法进行修改");
                   }
                   else{
                       if(tenantinformation!=null){
                           tenantinformation.setArchivesmouthsmanagement(archivesmouthsmanagement);
                           tenantinformationRepository.save(tenantinformation);
                       }
                   }
               }
           }
           if (resources.getTenantinformation()!=null){
               //如果租户信息修改则将修改的id进行判断
               if(resources.getTenantinformation().getId()!=leaseContract.getTenantinformation().getId()){
                   if(archivesmouthsmanagement!=null){
                       archivesmouthsmanagement.setTenementName(tenantinformation.getLinkman());
                       archivesmouthsmanagementRepository.save(archivesmouthsmanagement);
                   }
               }
           }


        }
        leaseContract.copy(resources);
        leaseContractRepository.save(leaseContract);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        if(id!=null){
            List<ParkPevenue> parkPevenueList = parkPevenueRepository.findByLeaseContractId(id);
                if(parkPevenueList.size()>0){
                    throw new BadRequestException("该合同下还有相关的收入信息,请先删除相关收入信息");
                }
        }
        leaseContractRepository.deleteById(id);
    }

    @Override
    public Object findTask() {
        //修改合同信息
        List<LeaseContract> leaseContractList = leaseContractRepository.findAll();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        for (LeaseContract leaseContract : leaseContractList) {
            //合同启用才会生效
            if (leaseContract.getIsEnable().equals("1")){
                //合同截止时间早于当前时间 则将自动吧合同转为作废
                if (leaseContract.getEndDate().before(timestamp)){
                    leaseContract.setIsEnable("2");
                    leaseContractRepository.save(leaseContract);
                    //修改档口信息
                if (leaseContract.getArchivesmouthsmanagement().getId()!=null) {
                    Archivesmouthsmanagement archivesmouthsmanagement = archivesmouthsmanagementRepository.findById(leaseContract.getArchivesmouthsmanagement().getId()).get();
                     if(archivesmouthsmanagement!=null){
                         archivesmouthsmanagement.setTenementName(null);
                         archivesmouthsmanagementRepository.save(archivesmouthsmanagement);
                     }
                }
               /*//修改租户信息
                if (leaseContract.getTenantinformation().getId()!=null) {
                    Tenantinformation tenantinformation = tenantinformationRepository.findByArchivesmouthsmanagementId(leaseContract.getArchivesmouthsmanagement().getId());
                    if(tenantinformation!=null){
                        tenantinformationRepository.save(tenantinformation);
                    }
                }*/
              }
            }
        }
        return leaseContractList;
    }
}

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
import me.zhengjie.modules.business.service.dto.LeaseContractSmallDTO;
import me.zhengjie.modules.business.service.mapper.LeaseContractSmallMapper;
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
    private LeaseContractSmallMapper leaseContractSmallMapper;

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
            //查询合同的欠款
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
   //根据部门id查询
    public Object findByDeptId(Long deptId) {
        List<LeaseContract> leaseContractList = new ArrayList<>();
   List<LeaseContractSmallDTO> leaseContractDTOS = leaseContractRepository.findbyleaseContractSmall(deptId);
        //List<LeaseContractSmallDTO> leaseContractDTOS = new ArrayList<>();

       /* if (deptId==1){
            leaseContractList=leaseContractRepository.findAll();
        }
        else{
            leaseContractList=leaseContractRepository.findByDeptId(deptId);
        }
        for (LeaseContract leaseContract : leaseContractList) {
            leaseContractDTOS.add(leaseContractSmallMapper.toDto(leaseContract,leaseContract.getArchivesmouthsmanagement()==null?null:archivesmouthsmanagementRepository.findById(leaseContract.getArchivesmouthsmanagement().getId()).get(),leaseContract.getTenantinformation()==null?null:tenantinformationRepository.findById(leaseContract.getTenantinformation().getId()).get()));
        }*/
        return  leaseContractDTOS;
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
           //自动生成合同编号
            JwtUser jwtUser =(JwtUser) SecurityUtils.getUserDetails();
            Long no = 0001l;
            if (leaseContractRepository.findByNewcontractNo(resources.getDept().getId())!=null){
                no=Long.valueOf(leaseContractRepository.findByNewcontractNo(resources.getDept().getId()))+0001l;
            }
            //部门编号+当前时间+流水号
            resources.setContractNo(jwtUser.getDeptNo()+ StringUtils.getCurentDate()+new DecimalFormat("0000").format(no));
        }
        return leaseContractMapper.toDto(leaseContractRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(LeaseContract resources) {
        Optional<LeaseContract> optionalLeaseContract = leaseContractRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalLeaseContract,"LeaseContract","id",resources.getId());
        LeaseContract leaseContract = optionalLeaseContract.get();//数据库对应的对象

        if (resources!=null){
            //(建议写关联表)
            //修改后的档口信息
            //合同會綁定租戶和檔口
            Archivesmouthsmanagement archivesmouthsmanagementAfter = archivesmouthsmanagementRepository.findById(resources.getArchivesmouthsmanagement().getId()).get();//
            archivesmouthsmanagementAfter.setTenementName(tenantinformationRepository.findById(resources.getTenantinformation().getId()).get().getLinkman());
            //如果修改后档口不同应该将之后的档口更新，将之前的档口释放
            if (resources.getArchivesmouthsmanagement().getId()!=leaseContract.getArchivesmouthsmanagement().getId()){
                Archivesmouthsmanagement archivesmouthsmanagementBefore = archivesmouthsmanagementRepository.findById(leaseContract.getArchivesmouthsmanagement().getId()).get();//
                archivesmouthsmanagementBefore.setTenementName(null);
            }
            //当合同被改为禁用则需要修改合同状态
            if(resources.getIsEnable().equals("2")){
                archivesmouthsmanagementAfter.setTenementName(null);//合同修改为作废会将租户清空
            }

        }
        leaseContract.copy(resources);
        leaseContract.setIsAudit(0);
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
           LeaseContract leaseContract = leaseContractRepository.findById(id).get();
                //当合同启用是会释放档口
                if(leaseContract.getIsEnable().equals("1")){
                    Archivesmouthsmanagement archivesmouthsmanagement = archivesmouthsmanagementRepository.findById(leaseContract.getArchivesmouthsmanagement().getId()).get();
                    archivesmouthsmanagement.setTenementName(null);
                    archivesmouthsmanagementRepository.save(archivesmouthsmanagement);
                }
            leaseContractRepository.deleteById(id);
        }

    }

    @Override
    //合同到期的定时任务查询
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

    //审核
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void vertify(Long[] vertifys, Integer status) {
        if (vertifys.length>0){
            for (Long id : vertifys) {
                LeaseContract contract = leaseContractRepository.findById(id).get();
                /* 状态为审核中才能审核*/
                if (contract.getIsAudit()==0){
                    //审核未通过
                    if (status==1){
                        contract.setIsAudit(1);
                        leaseContractRepository.updateByVertify1(contract.getId());
                    }
                    //审核通过并且类型
                    if (status==2){
                        contract.setIsAudit(2);
                        leaseContractRepository.updateByVertify2(contract.getId());
                    }
                }
                else{
                    throw new BadRequestException("请勿重复审核");
                }
            }
        }
    }
}

package me.zhengjie.modules.basic_management.Tenantinformation.service.impl;

import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.repository.ArchivesmouthsmanagementRepository;
import me.zhengjie.modules.basic_management.Tenantinformation.domain.Tenantinformation;
import me.zhengjie.modules.basic_management.Tenantinformation.service.mapper.TenantinformationMapper;
import me.zhengjie.modules.business.domain.ParkPevenue;
import me.zhengjie.modules.business.repository.LeaseContractRepository;
import me.zhengjie.modules.business.repository.ParkPevenueRepository;
import me.zhengjie.modules.system.domain.DictDetail;
import me.zhengjie.modules.system.repository.DeptRepository;
import me.zhengjie.modules.system.repository.DictDetailRepository;
import me.zhengjie.modules.system.repository.DictRepository;
import me.zhengjie.utils.StringUtils;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.basic_management.Tenantinformation.repository.TenantinformationRepository;
import me.zhengjie.modules.basic_management.Tenantinformation.service.TenantinformationService;
import me.zhengjie.modules.basic_management.Tenantinformation.service.dto.TenantinformationDTO;
import me.zhengjie.modules.basic_management.Tenantinformation.service.dto.TenantinformationQueryCriteria;
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
import sun.nio.ch.IOStatus;

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
    private DictRepository dictRepository;

    @Autowired
    private DeptRepository deptRepository;

    @Autowired
    private LeaseContractRepository LeaseContractRepository;

    @Autowired
    private ArchivesmouthsmanagementRepository ArchivesmouthsmanagementRepository;

    @Autowired
    private  ParkPevenueRepository parkPevenueRepository;

    @Override
    public Object queryAll(TenantinformationQueryCriteria criteria, Pageable pageable){
        Page<Tenantinformation> page = tenantinformationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        List<TenantinformationDTO> tenantinformations = new ArrayList<>();
        //欠付
        DictDetail underDict =dictDetailRepository.findByDictIdAndValue(dictRepository.findByName("pevenue_status").getId(),"PEVENUE_UNDER");

        for (Tenantinformation tenantinformation : page.getContent()){
            /*if (tenantinformation.getDictDetail()==null){
                long is = 38;
                tenantinformation.getDictDetail().setId(is);
            }*/
            List<ParkPevenue> parkPevenueList = parkPevenueRepository.findByArchivesmouthsmanagementIdAndPayTypeId(tenantinformation.getArchivesmouthsmanagement().getId(),underDict.getId());
            BigDecimal totalMoney = new BigDecimal(0);
            for (ParkPevenue parkPevenue : parkPevenueList){
                //bigdecimal 求和(未缴费用)
                totalMoney = totalMoney.add(new BigDecimal(StringUtils.isNotNullBigDecimal(parkPevenue.getHouseRent())+StringUtils.isNotNullBigDecimal(parkPevenue.getPropertyRent())+StringUtils.isNotNullBigDecimal(parkPevenue.getWaterRent())+StringUtils.isNotNullBigDecimal(parkPevenue.getElectricityRent())+StringUtils.isNotNullBigDecimal(parkPevenue.getSanitationRent())+StringUtils.isNotNullBigDecimal(parkPevenue.getLiquidatedRent())+StringUtils.isNotNullBigDecimal(parkPevenue.getLateRent())+StringUtils.isNotNullBigDecimal(parkPevenue.getGroundPoundRent())+StringUtils.isNotNullBigDecimal(parkPevenue.getManagementRent())+StringUtils.isNotNullBigDecimal(parkPevenue.getParkingRent())));
                tenantinformation.setAmountinarear(totalMoney);
            }
            /*if (tenantinformation.getDictDetail()==null){*/
                /*long is = 10;
                findById(tenantinformation.getDictDetail().getId()).setDeptId(is);*/
                /*DictDetail dictDetail = new DictDetail();*/
               /* tenantinformations.add(tenantinformationMapper.toDtos(tenantinformation,deptRepository.findById(tenantinformation.getDept().getId()).get(),LeaseContractRepository.findById(tenantinformation.getLeaseContract().getId()).get(),ArchivesmouthsmanagementRepository.findById(tenantinformation.getArchivesmouthsmanagement().getId()).get()));*/
            /*}*/
            tenantinformations.add(tenantinformationMapper.toDto(tenantinformation,deptRepository.findById(tenantinformation.getDept().getId()).get(),dictDetailRepository.findById(tenantinformation.getDictDetail().getId())==null?null:dictDetailRepository.findById(tenantinformation.getDictDetail().getId()).get(),LeaseContractRepository.findById(tenantinformation.getLeaseContract().getId()).get(),ArchivesmouthsmanagementRepository.findById(tenantinformation.getArchivesmouthsmanagement().getId()).get()));
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
    public Object findByArchivesmouthsmanagementId(Long id) {
        return tenantinformationMapper.toDto(tenantinformationRepository.findByArchivesmouthsmanagementId(id));

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
        /*List<Tenantinformation> list = new ArrayList<>();
        for (Tenantinformation tenantinformation : list){
        dictDetailRepository.findById(tenantinformation.getDictDetail().getId()).get();
        }*/
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

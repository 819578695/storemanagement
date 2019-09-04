package me.zhengjie.modules.business.service.impl;

import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.repository.ArchivesmouthsmanagementRepository;
import me.zhengjie.modules.basic_management.thearchives.domain.BasicsPark;
import me.zhengjie.modules.basic_management.thearchives.repository.BasicsParkRepository;
import me.zhengjie.modules.business.domain.ParkPevenue;
import me.zhengjie.modules.business.repository.LeaseContractRepository;
import me.zhengjie.modules.business.repository.ReceiptPaymentAccountRepository;
import me.zhengjie.modules.finance.domain.FinanceMaintarinDetail;
import me.zhengjie.modules.finance.repository.FinanceMaintarinDetailRepository;
import me.zhengjie.modules.finance.service.JournalAccountOfCapitalService;
import me.zhengjie.modules.system.repository.DeptRepository;
import me.zhengjie.modules.system.repository.DictDetailRepository;
import me.zhengjie.utils.StringUtils;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.business.repository.ParkPevenueRepository;
import me.zhengjie.modules.business.service.ParkPevenueService;
import me.zhengjie.modules.business.service.dto.ParkPevenueDTO;
import me.zhengjie.modules.business.service.dto.ParkPevenueQueryCriteria;
import me.zhengjie.modules.business.service.mapper.ParkPevenueMapper;
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
* @author kang
* @date 2019-08-23
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ParkPevenueServiceImpl implements ParkPevenueService {

    @Autowired
    private ParkPevenueRepository parkPevenueRepository;

    @Autowired
    private ParkPevenueMapper parkPevenueMapper;
    @Autowired
    private ArchivesmouthsmanagementRepository archivesmouthsmanagementRepository;
    @Autowired
    private DeptRepository deptRepository;
    @Autowired
    private ReceiptPaymentAccountRepository receiptPaymentAccountRepository;
    @Autowired
    private DictDetailRepository dictDetailRepository;
    @Autowired
    private BasicsParkRepository basicsParkRepository;
    @Autowired
    private LeaseContractRepository leaseContractRepository;
    @Autowired
    private JournalAccountOfCapitalService journalAccountOfCapitalService;
    @Autowired
    private FinanceMaintarinDetailRepository financeMaintainDetailRepository;


    @Override
    public Object queryAll(ParkPevenueQueryCriteria criteria, Pageable pageable){
        Page<ParkPevenue> page = parkPevenueRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        List<ParkPevenueDTO> parkPevenueDTOS = new ArrayList<>();
        for (ParkPevenue parkPevenue : page.getContent()) {
            parkPevenueDTOS.add(parkPevenueMapper.toDto(parkPevenue,archivesmouthsmanagementRepository.findById(parkPevenue.getArchivesmouthsmanagement().getId()).get(),deptRepository.findAllById(parkPevenue.getDept().getId()),receiptPaymentAccountRepository.findById(parkPevenue.getReceiptPaymentAccount().getId()).get(),dictDetailRepository.findById(parkPevenue.getDictDetail().getId()).get(),leaseContractRepository.findById(parkPevenue.getLeaseContract().getId()).get()));
        }

        return PageUtil.toPage(parkPevenueDTOS,page.getTotalElements());
    }

    @Override
    public Object queryAll(ParkPevenueQueryCriteria criteria){
        return parkPevenueMapper.toDto(parkPevenueRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public ParkPevenueDTO findById(Long id) {
        Optional<ParkPevenue> parkPevenue = parkPevenueRepository.findById(id);
        ValidationUtil.isNull(parkPevenue,"ParkPevenue","id",id);
        return parkPevenueMapper.toDto(parkPevenue.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ParkPevenueDTO create(ParkPevenue resources) {
        ParkPevenue p =resources;
        if (p!=null)
        {
            //根据支付方式和部门查询账户详情
            FinanceMaintarinDetail financeMaintarinDetails =financeMaintainDetailRepository.findByTradTypeIdAndDeptId(p.getDictDetail().getId(),p.getDept().getId());
            if(financeMaintarinDetails!=null){
                //修改账户详情的余额
                Double price=(StringUtils.isNotNullBigDecimal(resources.getArrersRent())+
                        StringUtils.isNotNullBigDecimal(resources.getHouseRent())
                        +StringUtils.isNotNullBigDecimal(resources.getElectricityRent())
                        +StringUtils.isNotNullBigDecimal(resources.getGroundPoundRent())
                        +StringUtils.isNotNullBigDecimal(resources.getLateRent())
                        +StringUtils.isNotNullBigDecimal(resources.getLiquidatedRent())
                        +StringUtils.isNotNullBigDecimal(resources.getManagementRent())
                        +StringUtils.isNotNullBigDecimal(resources.getParkingRent())
                        +StringUtils.isNotNullBigDecimal(resources.getPropertyRent())
                        +StringUtils.isNotNullBigDecimal(resources.getSanitationRent())
                        +StringUtils.isNotNullBigDecimal(resources.getWaterRent()));
                    BigDecimal Difference = new BigDecimal(financeMaintarinDetails.getRemaining().doubleValue() + price );
                    financeMaintarinDetails.setRemaining(Difference);
                    parkPevenueRepository.save(resources);
                    financeMaintainDetailRepository.save(financeMaintarinDetails);
                //房租
                if ( resources.getHouseRent()!=null){
                    if (StringUtils.iseqBigDecimal(resources.getHouseRent())){
                        journalAccountOfCapitalService.createByPostPevenue(p,"1",resources.getHouseRent());
                    }
                }
                //水费
                if (resources.getWaterRent()!=null ){
                    if (StringUtils.iseqBigDecimal(resources.getWaterRent())){
                        journalAccountOfCapitalService.createByPostPevenue(p,"7",resources.getWaterRent());
                    }
                }
                //电费
                if (resources.getElectricityRent()!=null){
                    if (StringUtils.iseqBigDecimal(resources.getElectricityRent())){
                        journalAccountOfCapitalService.createByPostPevenue(p,"8",resources.getElectricityRent());
                    }
                }
                //物业费
                if (resources.getPropertyRent()!=null){
                    if (StringUtils.iseqBigDecimal(resources.getPropertyRent())){
                        journalAccountOfCapitalService.createByPostPevenue(p,"10",resources.getPropertyRent());
                    }
                }
                //卫生费
                if (resources.getSanitationRent()!=null) {
                    if (StringUtils.iseqBigDecimal(resources.getSanitationRent())) {
                        journalAccountOfCapitalService.createByPostPevenue(p, "15", resources.getSanitationRent());
                    }
                }
                //违约金
                if (resources.getLiquidatedRent()!=null){
                    if (StringUtils.iseqBigDecimal(resources.getLiquidatedRent())) {
                        journalAccountOfCapitalService.createByPostPevenue(p, "12", resources.getLiquidatedRent());
                    }
                }
                //滞纳金
                if (resources.getLateRent()!=null){
                    if (StringUtils.iseqBigDecimal(resources.getLateRent())) {
                        journalAccountOfCapitalService.createByPostPevenue(p, "14", resources.getLateRent());
                    }
                }
                //地磅费
                if (resources.getGroundPoundRent()!=null){
                    if (StringUtils.iseqBigDecimal(resources.getGroundPoundRent())) {
                        journalAccountOfCapitalService.createByPostPevenue(p, "3", resources.getGroundPoundRent());
                    }
                }
                //管理费
                if (resources.getManagementRent()!=null){
                    if (StringUtils.iseqBigDecimal(resources.getManagementRent())) {
                        journalAccountOfCapitalService.createByPostPevenue(p, "13", resources.getManagementRent());
                    }
                }
                //停车费
                if (resources.getParkingRent()!=null){
                    if (StringUtils.iseqBigDecimal(resources.getParkingRent())) {
                        journalAccountOfCapitalService.createByPostPevenue(p, "2", resources.getParkingRent());
                    }
                }
            }
            else{
                throw new BadRequestException("请先新建账户余额");
            }

        }
        return parkPevenueMapper.toDto(p);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ParkPevenue resources) {
        ParkPevenue parkPevenueBefore = parkPevenueRepository.findById(resources.getId()).get();
        Optional<ParkPevenue> optionalParkPevenue = parkPevenueRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalParkPevenue,"ParkPevenue","id",resources.getId());
        ParkPevenue parkPevenue = optionalParkPevenue.get();

        if(parkPevenue!=null) {
            //根据支付方式和账户id查询账户详情
            FinanceMaintarinDetail financeMaintarinDetails = financeMaintainDetailRepository.findByTradTypeIdAndDeptId(parkPevenue.getDictDetail().getId(), parkPevenue.getDept().getId());
            if (financeMaintarinDetails != null) {
                //修改之后的余额
                Double price = (StringUtils.isNotNullBigDecimal(resources.getArrersRent()) + StringUtils.isNotNullBigDecimal(resources.getHouseRent()) + StringUtils.isNotNullBigDecimal(resources.getElectricityRent()) + StringUtils.isNotNullBigDecimal(resources.getGroundPoundRent()) + StringUtils.isNotNullBigDecimal(resources.getLateRent()) + StringUtils.isNotNullBigDecimal(resources.getLiquidatedRent()) + StringUtils.isNotNullBigDecimal(resources.getManagementRent()) + StringUtils.isNotNullBigDecimal(resources.getParkingRent()) + StringUtils.isNotNullBigDecimal(resources.getPropertyRent()) + StringUtils.isNotNullBigDecimal(resources.getSanitationRent()) + StringUtils.isNotNullBigDecimal(resources.getWaterRent()));                //修改之前的余额
                //修改之前的余额
                Double beforePrice = (StringUtils.isNotNullBigDecimal(parkPevenueBefore.getArrersRent()) + StringUtils.isNotNullBigDecimal(parkPevenueBefore.getHouseRent()) + StringUtils.isNotNullBigDecimal(parkPevenueBefore.getElectricityRent()) + StringUtils.isNotNullBigDecimal(parkPevenueBefore.getGroundPoundRent()) + StringUtils.isNotNullBigDecimal(parkPevenueBefore.getLateRent()) + StringUtils.isNotNullBigDecimal(parkPevenueBefore.getLiquidatedRent()) + StringUtils.isNotNullBigDecimal(parkPevenueBefore.getManagementRent()) + StringUtils.isNotNullBigDecimal(parkPevenueBefore.getParkingRent()) + StringUtils.isNotNullBigDecimal(parkPevenueBefore.getPropertyRent()) + StringUtils.isNotNullBigDecimal(parkPevenueBefore.getSanitationRent()) + StringUtils.isNotNullBigDecimal(parkPevenueBefore.getWaterRent()));

                BigDecimal Difference = new BigDecimal(financeMaintarinDetails.getRemaining().doubleValue() + (price - beforePrice));
                //判断当前帐户余额和修改后的金额,如果为负数就不做修改
                if (Difference.signum() != -1) {
                    //修改账户详情的余额
                    financeMaintarinDetails.setRemaining(Difference);
                    financeMaintainDetailRepository.save(financeMaintarinDetails);
                    parkPevenue.copy(resources);
                    parkPevenueRepository.save(parkPevenue);
                    //修改资金流水
                    //房租
                    if (resources.getHouseRent() != null) {
                        if (StringUtils.iseqBigDecimal(resources.getHouseRent())) {
                            journalAccountOfCapitalService.createByPostPevenue(parkPevenue, "1", resources.getHouseRent());
                        }
                    }
                    //水费
                    if (resources.getWaterRent() != null) {
                        if (StringUtils.iseqBigDecimal(resources.getWaterRent())) {
                            journalAccountOfCapitalService.createByPostPevenue(parkPevenue, "7", resources.getWaterRent());
                        }
                    }
                    //电费
                    if (resources.getElectricityRent() != null) {
                        if (StringUtils.iseqBigDecimal(resources.getElectricityRent())) {
                            journalAccountOfCapitalService.createByPostPevenue(parkPevenue, "8", resources.getElectricityRent());
                        }
                    }
                    //物业费
                    if (resources.getPropertyRent() != null) {
                        if (StringUtils.iseqBigDecimal(resources.getPropertyRent())) {
                            journalAccountOfCapitalService.createByPostPevenue(parkPevenue, "10", resources.getPropertyRent());
                        }
                    }
                    //卫生费
                    if (resources.getSanitationRent() != null) {
                        if (StringUtils.iseqBigDecimal(resources.getSanitationRent())) {
                            journalAccountOfCapitalService.createByPostPevenue(parkPevenue, "15", resources.getSanitationRent());
                        }
                    }
                    //违约金
                    if (resources.getLiquidatedRent() != null) {
                        if (StringUtils.iseqBigDecimal(resources.getLiquidatedRent())) {
                            journalAccountOfCapitalService.createByPostPevenue(parkPevenue, "12", resources.getLiquidatedRent());
                        }
                    }
                    //滞纳金
                    if (resources.getLateRent() != null) {
                        if (StringUtils.iseqBigDecimal(resources.getLateRent())) {
                            journalAccountOfCapitalService.createByPostPevenue(parkPevenue, "14", resources.getLateRent());
                        }
                    }
                    //地磅费
                    if (resources.getGroundPoundRent() != null) {
                        if (StringUtils.iseqBigDecimal(resources.getGroundPoundRent())) {
                            journalAccountOfCapitalService.createByPostPevenue(parkPevenue, "3", resources.getGroundPoundRent());
                        }
                    }
                    //管理费
                    if (resources.getManagementRent() != null) {
                        if (StringUtils.iseqBigDecimal(resources.getManagementRent())) {
                            journalAccountOfCapitalService.createByPostPevenue(parkPevenue, "13", resources.getManagementRent());
                        }
                    }
                    //停车费
                    if (resources.getParkingRent() != null) {
                        if (StringUtils.iseqBigDecimal(resources.getParkingRent())) {
                            journalAccountOfCapitalService.createByPostPevenue(parkPevenue, "2", resources.getParkingRent());
                        }
                    }
                } else {
                    throw new BadRequestException("账户余额不足,请更换支付方式");
                }
            } else {
                throw new BadRequestException("请先新建账户余额");
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        parkPevenueRepository.deleteById(id);
    }
}

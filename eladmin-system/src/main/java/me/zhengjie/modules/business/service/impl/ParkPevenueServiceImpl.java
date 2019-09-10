package me.zhengjie.modules.business.service.impl;

import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.repository.ArchivesmouthsmanagementRepository;
import me.zhengjie.modules.basic_management.thearchives.repository.BasicsParkRepository;
import me.zhengjie.modules.business.domain.ParkPevenue;
import me.zhengjie.modules.business.repository.LeaseContractRepository;
import me.zhengjie.modules.business.repository.ReceiptPaymentAccountRepository;
import me.zhengjie.modules.finance.domain.MaintarinDetail;
import me.zhengjie.modules.finance.repository.MaintarinDetailRepository;
import me.zhengjie.modules.finance.service.FundFlowingService;
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
    private FundFlowingService fundFlowingService;
    @Autowired
    private MaintarinDetailRepository maintainDetailRepository;


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
            MaintarinDetail maintarinDetails =maintainDetailRepository.findByTradTypeIdAndDeptId(p.getDictDetail().getId(),p.getDept().getId());
            if(maintarinDetails!=null){
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
                    parkPevenueRepository.save(resources);
                //房租
                if ( resources.getHouseRent()!=null){
                    if (StringUtils.iseqBigDecimal(resources.getHouseRent())){
                        fundFlowingService.createByPostPevenue(p,"1",resources.getHouseRent(),new BigDecimal(0.00));
                    }
                }
                //水费
                if (resources.getWaterRent()!=null ){
                    if (StringUtils.iseqBigDecimal(resources.getWaterRent())){
                        fundFlowingService.createByPostPevenue(p,"7",resources.getWaterRent(),new BigDecimal(0.00));
                    }
                }
                //电费
                if (resources.getElectricityRent()!=null){
                    if (StringUtils.iseqBigDecimal(resources.getElectricityRent())){
                        fundFlowingService.createByPostPevenue(p,"8",resources.getElectricityRent(),new BigDecimal(0.00));
                    }
                }
                //物业费
                if (resources.getPropertyRent()!=null){
                    if (StringUtils.iseqBigDecimal(resources.getPropertyRent())){
                        fundFlowingService.createByPostPevenue(p,"10",resources.getPropertyRent(),new BigDecimal(0.00));
                    }
                }
                //卫生费
                if (resources.getSanitationRent()!=null) {
                    if (StringUtils.iseqBigDecimal(resources.getSanitationRent())) {
                        fundFlowingService.createByPostPevenue(p, "15", resources.getSanitationRent(),new BigDecimal(0.00));
                    }
                }
                //违约金
                if (resources.getLiquidatedRent()!=null){
                    if (StringUtils.iseqBigDecimal(resources.getLiquidatedRent())) {
                        fundFlowingService.createByPostPevenue(p, "12", resources.getLiquidatedRent(),new BigDecimal(0.00));
                    }
                }
                //滞纳金
                if (resources.getLateRent()!=null){
                    if (StringUtils.iseqBigDecimal(resources.getLateRent())) {
                        fundFlowingService.createByPostPevenue(p, "14", resources.getLateRent(),new BigDecimal(0.00));
                    }
                }
                //地磅费
                if (resources.getGroundPoundRent()!=null){
                    if (StringUtils.iseqBigDecimal(resources.getGroundPoundRent())) {
                        fundFlowingService.createByPostPevenue(p, "3", resources.getGroundPoundRent(),new BigDecimal(0.00));
                    }
                }
                //管理费
                if (resources.getManagementRent()!=null){
                    if (StringUtils.iseqBigDecimal(resources.getManagementRent())) {
                        fundFlowingService.createByPostPevenue(p, "13", resources.getManagementRent(),new BigDecimal(0.00));
                    }
                }
                //停车费
                if (resources.getParkingRent()!=null){
                    if (StringUtils.iseqBigDecimal(resources.getParkingRent())) {
                        fundFlowingService.createByPostPevenue(p, "2", resources.getParkingRent(),new BigDecimal(0.00));
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
            MaintarinDetail maintarinDetails = maintainDetailRepository.findByTradTypeIdAndDeptId(parkPevenue.getDictDetail().getId(), parkPevenue.getDept().getId());
            if (maintarinDetails != null) {
                //修改之后的余额
                // Double price = (StringUtils.isNotNullBigDecimal(resources.getArrersRent()) + StringUtils.isNotNullBigDecimal(resources.getHouseRent()) + StringUtils.isNotNullBigDecimal(resources.getElectricityRent()) + StringUtils.isNotNullBigDecimal(resources.getGroundPoundRent()) + StringUtils.isNotNullBigDecimal(resources.getLateRent()) + StringUtils.isNotNullBigDecimal(resources.getLiquidatedRent()) + StringUtils.isNotNullBigDecimal(resources.getManagementRent()) + StringUtils.isNotNullBigDecimal(resources.getParkingRent()) + StringUtils.isNotNullBigDecimal(resources.getPropertyRent()) + StringUtils.isNotNullBigDecimal(resources.getSanitationRent()) + StringUtils.isNotNullBigDecimal(resources.getWaterRent()));                //修改之前的余额
                //修改之前的余额
                //Double beforePrice = (StringUtils.isNotNullBigDecimal(parkPevenueBefore.getArrersRent()) + StringUtils.isNotNullBigDecimal(parkPevenueBefore.getHouseRent()) + StringUtils.isNotNullBigDecimal(parkPevenueBefore.getElectricityRent()) + StringUtils.isNotNullBigDecimal(parkPevenueBefore.getGroundPoundRent()) + StringUtils.isNotNullBigDecimal(parkPevenueBefore.getLateRent()) + StringUtils.isNotNullBigDecimal(parkPevenueBefore.getLiquidatedRent()) + StringUtils.isNotNullBigDecimal(parkPevenueBefore.getManagementRent()) + StringUtils.isNotNullBigDecimal(parkPevenueBefore.getParkingRent()) + StringUtils.isNotNullBigDecimal(parkPevenueBefore.getPropertyRent()) + StringUtils.isNotNullBigDecimal(parkPevenueBefore.getSanitationRent()) + StringUtils.isNotNullBigDecimal(parkPevenueBefore.getWaterRent()));

                // BigDecimal Difference = new BigDecimal(maintarinDetails.getRemaining().doubleValue() + (price - beforePrice));
                    //修改资金流水
                    //房租
                    if (resources.getHouseRent() != null&&resources.getHouseRent().compareTo(parkPevenue.getHouseRent()==null?new BigDecimal(0.00):parkPevenue.getHouseRent())!=0) {
                            BigDecimal Difference = new BigDecimal( (StringUtils.isNotNullBigDecimal(parkPevenueBefore.getHouseRent()) - (StringUtils.isNotNullBigDecimal(resources.getHouseRent()))));
                            fundFlowingService.createByPostPevenue(resources, "1", resources.getHouseRent(),Difference);
                    }
                    //水费
                    if (resources.getWaterRent() != null&&resources.getWaterRent().compareTo(parkPevenue.getWaterRent()==null?new BigDecimal(0.00):parkPevenue.getWaterRent())!=0) {
                            BigDecimal Difference = new BigDecimal( (StringUtils.isNotNullBigDecimal(parkPevenueBefore.getWaterRent()) - (StringUtils.isNotNullBigDecimal(resources.getWaterRent()))));
                            fundFlowingService.createByPostPevenue(resources, "7", resources.getWaterRent(),Difference);

                    }
                    //电费
                    if (resources.getElectricityRent() != null&&resources.getElectricityRent().compareTo(parkPevenue.getElectricityRent()==null?new BigDecimal(0.00):parkPevenue.getElectricityRent())!=0) {
                            BigDecimal Difference = new BigDecimal( (StringUtils.isNotNullBigDecimal(parkPevenueBefore.getElectricityRent()) - (StringUtils.isNotNullBigDecimal(resources.getElectricityRent()))));
                            fundFlowingService.createByPostPevenue(resources, "8", resources.getElectricityRent(),Difference);
                    }
                    //物业费
                    if (resources.getPropertyRent() != null&&resources.getPropertyRent().compareTo(parkPevenue.getPropertyRent()==null?new BigDecimal(0.00):parkPevenue.getPropertyRent())!=0) {
                            BigDecimal Difference = new BigDecimal( (StringUtils.isNotNullBigDecimal(parkPevenueBefore.getPropertyRent()) - (StringUtils.isNotNullBigDecimal(resources.getPropertyRent()))));
                            fundFlowingService.createByPostPevenue(resources, "10", resources.getPropertyRent(),Difference);
                    }
                    //卫生费
                    if (resources.getSanitationRent() != null&&resources.getSanitationRent().compareTo(parkPevenue.getSanitationRent()==null?new BigDecimal(0.00):parkPevenue.getSanitationRent())!=0) {
                            BigDecimal Difference = new BigDecimal( (StringUtils.isNotNullBigDecimal(parkPevenueBefore.getSanitationRent()) - (StringUtils.isNotNullBigDecimal(resources.getSanitationRent()))));
                            fundFlowingService.createByPostPevenue(parkPevenue, "15", resources.getSanitationRent(),Difference);
                    }
                    //违约金
                    if (resources.getLiquidatedRent() != null&&resources.getLiquidatedRent().compareTo(parkPevenue.getLiquidatedRent()==null?new BigDecimal(0.00):parkPevenue.getLiquidatedRent())!=0) {
                        BigDecimal Difference = new BigDecimal( (StringUtils.isNotNullBigDecimal(parkPevenueBefore.getLiquidatedRent()) - (StringUtils.isNotNullBigDecimal(resources.getLiquidatedRent()))));
                        fundFlowingService.createByPostPevenue(parkPevenue, "12", resources.getLiquidatedRent(),Difference);
                    }
                    //滞纳金
                    if (resources.getLateRent() != null&&resources.getLateRent().compareTo(parkPevenue.getLateRent()==null?new BigDecimal(0.00):parkPevenue.getLateRent())!=0) {
                            BigDecimal Difference = new BigDecimal( (StringUtils.isNotNullBigDecimal(parkPevenueBefore.getLateRent()) - (StringUtils.isNotNullBigDecimal(resources.getLateRent()))));
                            fundFlowingService.createByPostPevenue(parkPevenue, "14", resources.getLateRent(),Difference);
                    }
                    //地磅费
                    if (resources.getGroundPoundRent() != null&&resources.getGroundPoundRent().compareTo(parkPevenue.getGroundPoundRent()==null?new BigDecimal(0.00):parkPevenue.getGroundPoundRent())!=0) {
                            BigDecimal Difference = new BigDecimal( (StringUtils.isNotNullBigDecimal(parkPevenueBefore.getGroundPoundRent()) - (StringUtils.isNotNullBigDecimal(resources.getGroundPoundRent()))));
                            fundFlowingService.createByPostPevenue(parkPevenue, "3", resources.getGroundPoundRent(),Difference);
                    }
                    //管理费
                    if (resources.getManagementRent() != null&&resources.getManagementRent().compareTo(parkPevenue.getManagementRent()==null?new BigDecimal(0.00):parkPevenue.getManagementRent())!=0) {
                        BigDecimal Difference = new BigDecimal( (StringUtils.isNotNullBigDecimal(parkPevenueBefore.getManagementRent()) - (StringUtils.isNotNullBigDecimal(resources.getManagementRent()))));
                        fundFlowingService.createByPostPevenue(parkPevenue, "13", resources.getManagementRent(),Difference);
                    }
                    //停车费
                    if (resources.getParkingRent() != null&&resources.getParkingRent().compareTo(parkPevenue.getParkingRent()==null?new BigDecimal(0.00):parkPevenue.getParkingRent())!=0) {
                        BigDecimal Difference = new BigDecimal( (StringUtils.isNotNullBigDecimal(parkPevenueBefore.getParkingRent()) - (StringUtils.isNotNullBigDecimal(resources.getParkingRent()))));
                        fundFlowingService.createByPostPevenue(parkPevenue, "2", resources.getParkingRent(),Difference);

                    }
                    parkPevenue.copy(resources);
                    parkPevenueRepository.save(parkPevenue);

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

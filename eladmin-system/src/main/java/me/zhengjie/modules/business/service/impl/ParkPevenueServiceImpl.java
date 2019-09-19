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
import me.zhengjie.modules.system.domain.DictDetail;
import me.zhengjie.modules.system.repository.DeptRepository;
import me.zhengjie.modules.system.repository.DictDetailRepository;
import me.zhengjie.modules.system.repository.DictRepository;
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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
    @Autowired
    private DictRepository dictRepository;


    @Override
    public Object queryAll(ParkPevenueQueryCriteria criteria, Pageable pageable){
        Page<ParkPevenue> page = parkPevenueRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        List<ParkPevenueDTO> parkPevenueDTOS = new ArrayList<>();
        for (ParkPevenue parkPevenue : page.getContent()) {
            parkPevenueDTOS.add(parkPevenueMapper.toDto(parkPevenue,archivesmouthsmanagementRepository.findById(parkPevenue.getArchivesmouthsmanagement().getId())==null?null:archivesmouthsmanagementRepository.findById(parkPevenue.getArchivesmouthsmanagement().getId()).get(),deptRepository.findAllById(parkPevenue.getDept().getId()),receiptPaymentAccountRepository.findById(parkPevenue.getReceiptPaymentAccount().getId()).get(),dictDetailRepository.findById(parkPevenue.getDictDetail().getId()).get(),leaseContractRepository.findById(parkPevenue.getLeaseContract().getId()).get(),dictDetailRepository.findById(parkPevenue.getPayType().getId()).get()));
        }

        return PageUtil.toPage(parkPevenueDTOS,page.getTotalElements());
    }

    @Override
    public Object queryAll(ParkPevenueQueryCriteria criteria){
        List<ParkPevenue> list = parkPevenueRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder));
        List<ParkPevenueDTO> parkPevenueDTOS = new ArrayList<>();
        for (ParkPevenue parkPevenue : list) {
            parkPevenueDTOS.add(parkPevenueMapper.toDto(parkPevenue,archivesmouthsmanagementRepository.findById(parkPevenue.getArchivesmouthsmanagement().getId())==null?null:archivesmouthsmanagementRepository.findById(parkPevenue.getArchivesmouthsmanagement().getId()).get(),deptRepository.findAllById(parkPevenue.getDept().getId()),receiptPaymentAccountRepository.findById(parkPevenue.getReceiptPaymentAccount().getId()).get(),dictDetailRepository.findById(parkPevenue.getDictDetail().getId()).get(),leaseContractRepository.findById(parkPevenue.getLeaseContract().getId()).get(),dictDetailRepository.findById(parkPevenue.getPayType().getId()).get()));
        }
        return PageUtil.toPage(parkPevenueDTOS,null);
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
        DictDetail underDict =dictDetailRepository.findByDictIdAndValue(dictRepository.findByName("pevenue_status").getId(),"PEVENUE_UNDER");//欠付
        if (p!=null)
        {
            //根据支付方式和部门查询账户详情
            MaintarinDetail maintarinDetails =maintainDetailRepository.findByTradTypeIdAndDeptId(p.getDictDetail().getId(),p.getDept().getId());
            if(maintarinDetails!=null){
                //修改账户详情的余额
                Double price=(
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
                    //为欠款类型补添加到账户余额
              if(resources.getPayType().getId()!=underDict.getId() ){
                  addFinance(resources);
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
                  //
                   if(!resources.getPayType().getValue().equals("PEVENUE_UNDER")){
                       //修改资金流水
                       updateFinance(resources,parkPevenueBefore);
                   }


                    parkPevenue.copy(resources);
                    parkPevenue.setUpdateTime(new Timestamp(System.currentTimeMillis()));//修改时间为当前日期
                    parkPevenueRepository.save(parkPevenue);

            } else {
                throw new BadRequestException("请先新建账户余额");
            }
        }
    }

    @Override
    public ParkPevenueDTO payBack(ParkPevenue resources) {
        Optional<ParkPevenue> optionalParkPevenue = parkPevenueRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalParkPevenue,"ParkPevenue","id",resources.getId());
        ParkPevenue parkPevenue = optionalParkPevenue.get();
        //当欠付变为补缴时
        if (resources.getPayType().getValue().equals("PEVENUE_UNDER")) {
            if (parkPevenue != null) {
                //根据支付方式和账户id查询账户详情
                MaintarinDetail maintarinDetails = maintainDetailRepository.findByTradTypeIdAndDeptId(parkPevenue.getDictDetail().getId(), parkPevenue.getDept().getId());
                DictDetail payBackDict =dictDetailRepository.findByDictIdAndValue(dictRepository.findByName("pevenue_status").getId(),"PEVENUE_PAYBACK");//补缴
                DictDetail underDict =dictDetailRepository.findByDictIdAndValue(dictRepository.findByName("pevenue_status").getId(),"PEVENUE_UNDER");//欠付
                if (maintarinDetails != null) {
                    if (resources.getPayType().getValue().equals("PEVENUE_UNDER")) {
                        Double houseRent = StringUtils.isNotNullBigDecimal(resources.getHouseRent())-StringUtils.isNotNullBigDecimal(parkPevenue.getHouseRent());
                        Double waterRent = StringUtils.isNotNullBigDecimal(resources.getWaterRent())-(StringUtils.isNotNullBigDecimal(parkPevenue.getWaterRent()));
                        Double electricityRent= StringUtils.isNotNullBigDecimal(resources.getElectricityRent())-(StringUtils.isNotNullBigDecimal(parkPevenue.getElectricityRent()));
                        Double propertyRent= StringUtils.isNotNullBigDecimal(resources.getPropertyRent())-(StringUtils.isNotNullBigDecimal(parkPevenue.getPropertyRent()));
                        Double sanitationRent= StringUtils.isNotNullBigDecimal(resources.getSanitationRent())-(StringUtils.isNotNullBigDecimal(parkPevenue.getSanitationRent()));
                        Double liquidatedRent= StringUtils.isNotNullBigDecimal(resources.getLiquidatedRent())-(StringUtils.isNotNullBigDecimal(parkPevenue.getLiquidatedRent()));
                        Double lateRent= StringUtils.isNotNullBigDecimal(resources.getLateRent())-(StringUtils.isNotNullBigDecimal(parkPevenue.getLateRent()));
                        Double groundPoundRent= StringUtils.isNotNullBigDecimal(resources.getGroundPoundRent())-(StringUtils.isNotNullBigDecimal(parkPevenue.getGroundPoundRent()));
                        Double managementRent= StringUtils.isNotNullBigDecimal(resources.getManagementRent())-(StringUtils.isNotNullBigDecimal(parkPevenue.getManagementRent()));
                        Double parkingRent= StringUtils.isNotNullBigDecimal(resources.getParkingRent())-(StringUtils.isNotNullBigDecimal(parkPevenue.getParkingRent()));

                        //如果补缴金额和欠付金额相同则直接修改
                        if(houseRent==0.0 &&waterRent==0.0 &&electricityRent==0.0 &&propertyRent==0.0 &&sanitationRent==0.0 &&liquidatedRent==0.0 &&lateRent==0.0 &&groundPoundRent==0.0 &&managementRent==0.0 &&parkingRent==0.0
                        ){
                            parkPevenue.setPayType(payBackDict);
                            parkPevenue.setUpdateTime(new Timestamp(System.currentTimeMillis()));//修改时间为当前日期
                            parkPevenueRepository.save(parkPevenue);
                            addFinance(parkPevenue);
                        }
                        //如果补缴部分或其他未补缴的时候需新增
                        else{
                            //新增新的补缴项
                            ParkPevenue payBack = new ParkPevenue();
                            payBack.setPayType(payBackDict);
                            payBack.setArchivesmouthsmanagement(resources.getArchivesmouthsmanagement());
                            payBack.setBasicsPark(resources.getBasicsPark());
                            payBack.setDept(resources.getDept());
                            payBack.setDictDetail(resources.getDictDetail());
                            payBack.setElectricityRent(resources.getElectricityRent());
                            payBack.setGroundPoundRent(resources.getGroundPoundRent());
                            payBack.setHouseRent(resources.getHouseRent());
                            payBack.setLateRent(resources.getLateRent());
                            payBack.setLeaseContract(resources.getLeaseContract());
                            payBack.setLiquidatedRent(resources.getLiquidatedRent());
                            payBack.setManagementRent(resources.getManagementRent());
                            payBack.setParkingRent(resources.getParkingRent());
                            payBack.setPropertyRent(resources.getPropertyRent());
                            payBack.setReceiptPaymentAccount(resources.getReceiptPaymentAccount());
                            payBack.setSanitationRent(resources.getSanitationRent());
                            payBack.setWaterRent(resources.getWaterRent());
                            payBack.setUpdateTime(new Timestamp(System.currentTimeMillis()));//修改时间为当前日期
                            parkPevenueRepository.save(payBack);
                            addFinance(payBack);
                            //然后修改原有的补缴费用
                            parkPevenue.setPayType(underDict);
                            parkPevenue.setElectricityRent(resources.getElectricityRent()==null?null:new BigDecimal(Math.abs(electricityRent.doubleValue())));
                            parkPevenue.setGroundPoundRent(resources.getGroundPoundRent()==null?null:new BigDecimal(Math.abs(groundPoundRent.doubleValue())));
                            parkPevenue.setHouseRent(resources.getHouseRent()==null?null:new BigDecimal(Math.abs(houseRent.doubleValue())));
                            parkPevenue.setLateRent(resources.getLateRent()==null?null:new BigDecimal(Math.abs(lateRent.doubleValue())));
                            parkPevenue.setLiquidatedRent(resources.getLiquidatedRent()==null?null:new BigDecimal(Math.abs(liquidatedRent.doubleValue())));
                            parkPevenue.setManagementRent(resources.getManagementRent()==null?null:new BigDecimal(Math.abs(managementRent.doubleValue())));
                            parkPevenue.setParkingRent(resources.getParkingRent()==null?null:new BigDecimal(Math.abs(parkingRent.doubleValue())));
                            parkPevenue.setPropertyRent(resources.getPropertyRent()==null?null:new BigDecimal(Math.abs(propertyRent.doubleValue())));
                            parkPevenue.setSanitationRent(resources.getSanitationRent()==null?null:new BigDecimal(Math.abs(sanitationRent.doubleValue())));
                            parkPevenue.setWaterRent(resources.getWaterRent()==null?null:new BigDecimal(Math.abs(waterRent.doubleValue())));
                            payBack.setUpdateTime(new Timestamp(System.currentTimeMillis()));//修改时间为当前日期
                            parkPevenueRepository.save(parkPevenue);
                        }
                    }

                } else {
                    throw new BadRequestException("请先新建账户余额");
                }
            }
        }
        return parkPevenueMapper.toDto(parkPevenue,null,null,null,null,null,parkPevenue.getPayType());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        parkPevenueRepository.deleteById(id);
    }

    @Override
    public Object findPevenueMoney(Long deptId) {
        return parkPevenueRepository.findByDeptIdSumRent(deptId);
    }


    public void updateFinance(ParkPevenue resources,ParkPevenue parkPevenue){
        //修改资金流水
        //房租
        if (resources.getHouseRent() != null ) {
            BigDecimal Difference = new BigDecimal((StringUtils.isNotNullBigDecimal(parkPevenue.getHouseRent()) - (StringUtils.isNotNullBigDecimal(resources.getHouseRent()))));
            fundFlowingService.createByPostPevenue(resources, "1", resources.getHouseRent(),new BigDecimal(0.00));
        }
        //水费
        if (resources.getWaterRent() != null ) {
            BigDecimal Difference = new BigDecimal((StringUtils.isNotNullBigDecimal(parkPevenue.getWaterRent()) - (StringUtils.isNotNullBigDecimal(resources.getWaterRent()))));
            fundFlowingService.createByPostPevenue(resources, "7", resources.getWaterRent(), Difference);

        }
        //电费
        if (resources.getElectricityRent() != null ) {
            BigDecimal Difference = new BigDecimal((StringUtils.isNotNullBigDecimal(parkPevenue.getElectricityRent()) - (StringUtils.isNotNullBigDecimal(resources.getElectricityRent()))));
            fundFlowingService.createByPostPevenue(resources, "8", resources.getElectricityRent(), Difference);
        }
        //物业费
        if (resources.getPropertyRent() != null ) {
            BigDecimal Difference = new BigDecimal((StringUtils.isNotNullBigDecimal(parkPevenue.getPropertyRent()) - (StringUtils.isNotNullBigDecimal(resources.getPropertyRent()))));
            fundFlowingService.createByPostPevenue(resources, "10", resources.getPropertyRent(), Difference);
        }
        //卫生费
        if (resources.getSanitationRent() != null ) {
            BigDecimal Difference = new BigDecimal((StringUtils.isNotNullBigDecimal(parkPevenue.getSanitationRent()) - (StringUtils.isNotNullBigDecimal(resources.getSanitationRent()))));
            fundFlowingService.createByPostPevenue(parkPevenue, "15", resources.getSanitationRent(), Difference);
        }
        //违约金
        if (resources.getLiquidatedRent() != null ) {
            BigDecimal Difference = new BigDecimal((StringUtils.isNotNullBigDecimal(parkPevenue.getLiquidatedRent()) - (StringUtils.isNotNullBigDecimal(resources.getLiquidatedRent()))));
            fundFlowingService.createByPostPevenue(parkPevenue, "12", resources.getLiquidatedRent(), Difference);
        }
        //滞纳金
        if (resources.getLateRent() != null ) {
            BigDecimal Difference = new BigDecimal((StringUtils.isNotNullBigDecimal(parkPevenue.getLateRent()) - (StringUtils.isNotNullBigDecimal(resources.getLateRent()))));
            fundFlowingService.createByPostPevenue(parkPevenue, "14", resources.getLateRent(), Difference);
        }
        //地磅费
        if (resources.getGroundPoundRent() != null ) {
            BigDecimal Difference = new BigDecimal((StringUtils.isNotNullBigDecimal(parkPevenue.getGroundPoundRent()) - (StringUtils.isNotNullBigDecimal(resources.getGroundPoundRent()))));
            fundFlowingService.createByPostPevenue(parkPevenue, "3", resources.getGroundPoundRent(), Difference);
        }
        //管理费
        if (resources.getManagementRent() != null) {
            BigDecimal Difference = new BigDecimal((StringUtils.isNotNullBigDecimal(parkPevenue.getManagementRent()) - (StringUtils.isNotNullBigDecimal(resources.getManagementRent()))));
            fundFlowingService.createByPostPevenue(parkPevenue, "13", resources.getManagementRent(), Difference);
        }
        //停车费
        if (resources.getParkingRent() != null ) {
            BigDecimal Difference = new BigDecimal((StringUtils.isNotNullBigDecimal(parkPevenue.getParkingRent()) - (StringUtils.isNotNullBigDecimal(resources.getParkingRent()))));
            fundFlowingService.createByPostPevenue(parkPevenue, "2", resources.getParkingRent(), Difference);
        }
    }

        public void addFinance(ParkPevenue resources){
        //新增资金流水
        //房租
        if ( resources.getHouseRent()!=null){
            if (StringUtils.iseqBigDecimal(resources.getHouseRent())){
                fundFlowingService.createByPostPevenue(resources,"1",resources.getHouseRent(),new BigDecimal(0.00));
            }
        }
        //水费
        if (resources.getWaterRent()!=null ){
            if (StringUtils.iseqBigDecimal(resources.getWaterRent())){
                fundFlowingService.createByPostPevenue(resources,"7",resources.getWaterRent(),new BigDecimal(0.00));
            }
        }
        //电费
        if (resources.getElectricityRent()!=null){
            if (StringUtils.iseqBigDecimal(resources.getElectricityRent())){
                fundFlowingService.createByPostPevenue(resources,"8",resources.getElectricityRent(),new BigDecimal(0.00));
            }
        }
        //物业费
        if (resources.getPropertyRent()!=null){
            if (StringUtils.iseqBigDecimal(resources.getPropertyRent())){
                fundFlowingService.createByPostPevenue(resources,"10",resources.getPropertyRent(),new BigDecimal(0.00));
            }
        }
        //卫生费
        if (resources.getSanitationRent()!=null) {
            if (StringUtils.iseqBigDecimal(resources.getSanitationRent())) {
                fundFlowingService.createByPostPevenue(resources, "15", resources.getSanitationRent(),new BigDecimal(0.00));
            }
        }
        //违约金
        if (resources.getLiquidatedRent()!=null){
            if (StringUtils.iseqBigDecimal(resources.getLiquidatedRent())) {
                fundFlowingService.createByPostPevenue(resources, "12", resources.getLiquidatedRent(),new BigDecimal(0.00));
            }
        }
        //滞纳金
        if (resources.getLateRent()!=null){
            if (StringUtils.iseqBigDecimal(resources.getLateRent())) {
                fundFlowingService.createByPostPevenue(resources, "14", resources.getLateRent(),new BigDecimal(0.00));
            }
        }
        //地磅费
        if (resources.getGroundPoundRent()!=null){
            if (StringUtils.iseqBigDecimal(resources.getGroundPoundRent())) {
                fundFlowingService.createByPostPevenue(resources, "3", resources.getGroundPoundRent(),new BigDecimal(0.00));
            }
        }
        //管理费
        if (resources.getManagementRent()!=null){
            if (StringUtils.iseqBigDecimal(resources.getManagementRent())) {
                fundFlowingService.createByPostPevenue(resources, "13", resources.getManagementRent(),new BigDecimal(0.00));
            }
        }
        //停车费
        if (resources.getParkingRent()!=null){
            if (StringUtils.iseqBigDecimal(resources.getParkingRent())) {
                fundFlowingService.createByPostPevenue(resources, "2", resources.getParkingRent(),new BigDecimal(0.00));
            }
        }
    }
}

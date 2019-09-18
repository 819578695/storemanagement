package me.zhengjie.modules.business.service.impl;

import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.repository.ArchivesmouthsmanagementRepository;
import me.zhengjie.modules.basic_management.thearchives.repository.BasicsParkRepository;
import me.zhengjie.modules.business.domain.ParkCost;
import me.zhengjie.modules.business.domain.RentContract;
import me.zhengjie.modules.business.repository.ReceiptPaymentAccountRepository;
import me.zhengjie.modules.business.repository.RentContractRepository;
import me.zhengjie.modules.finance.domain.MaintarinDetail;
import me.zhengjie.modules.finance.repository.FundFlowingRepository;
import me.zhengjie.modules.finance.repository.MaintainRepository;
import me.zhengjie.modules.finance.repository.MaintarinDetailRepository;
import me.zhengjie.modules.finance.service.FundFlowingService;
import me.zhengjie.modules.system.repository.DeptRepository;
import me.zhengjie.modules.system.repository.DictDetailRepository;
import me.zhengjie.modules.system.repository.DictRepository;
import me.zhengjie.utils.StringUtils;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.business.repository.ParkCostRepository;
import me.zhengjie.modules.business.service.ParkCostService;
import me.zhengjie.modules.business.service.dto.ParkCostDTO;
import me.zhengjie.modules.business.service.dto.ParkCostQueryCriteria;
import me.zhengjie.modules.business.service.mapper.ParkCostMapper;
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
* @date 2019-08-22
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ParkCostServiceImpl implements ParkCostService {

    @Autowired
    private ParkCostRepository parkCostRepository;

    @Autowired
    private ParkCostMapper parkCostMapper;
    @Autowired
    private DeptRepository deptRepository;
    @Autowired
    private DictDetailRepository dictDetailRepository;
    @Autowired
    private BasicsParkRepository basicsParkRepository;
    @Autowired
    private RentContractRepository rentContractRepository;
    @Autowired
    private FundFlowingRepository fundFlowingRepository;
    @Autowired
    private  DictRepository dictRepository;
    @Autowired
    private MaintainRepository maintainRepository;
    @Autowired
    private MaintarinDetailRepository maintainDetailRepository;
    @Autowired
    private ReceiptPaymentAccountRepository receiptPaymentAccountRepository;
    @Autowired
    private FundFlowingService fundFlowingService;
    @Autowired
    private ArchivesmouthsmanagementRepository archivesmouthsmanagementRepository;

    @Override
    public Object queryAll(ParkCostQueryCriteria criteria, Pageable pageable){
        Page<ParkCost> page = parkCostRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        List<ParkCostDTO> parkCostDTOS = new ArrayList<>();
        for (ParkCost parkCost : page.getContent()) {
        Optional<RentContract> rentContract =  rentContractRepository.findById(parkCost.getRentContract().getId());
            parkCostDTOS.add(parkCostMapper.toDto(parkCost,deptRepository.findAllById(parkCost.getDept().getId()),dictDetailRepository.findById(parkCost.getDictDetail().getId()).get(),archivesmouthsmanagementRepository.findById(parkCost.getArchivesmouthsmanagement().getId()).get(),rentContract.get(),receiptPaymentAccountRepository.findById(parkCost.getReceiptPaymentAccount().getId()).get()));
        }
        return PageUtil.toPage(parkCostDTOS,page.getTotalElements());
    }
    @Override
    public Object queryAll( Pageable pageable){

        return parkCostMapper.toDto(parkCostRepository.findAll(pageable).getContent());
    }
    @Override
    public Object queryAll(ParkCostQueryCriteria criteria){
        return parkCostMapper.toDto(parkCostRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public ParkCostDTO findById(Long id) {
        Optional<ParkCost> parkCost = parkCostRepository.findById(id);
        ValidationUtil.isNull(parkCost,"ParkCost","id",id);
        return parkCostMapper.toDto(parkCost.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ParkCostDTO create(ParkCost resources) {
      ParkCost p =resources ;
      if (resources!=null){
           //根据支付方式和部门查询账户详情
            MaintarinDetail financeMaintarinDetails =maintainDetailRepository.findByTradTypeIdAndDeptId(p.getDictDetail().getId(),p.getDept().getId());
            if (financeMaintarinDetails!=null){
                //修改账户详情的余额
                Double price=(StringUtils.isNotNullBigDecimal(resources.getElectricityRent())+StringUtils.isNotNullBigDecimal(resources.getOtherRent())+StringUtils.isNotNullBigDecimal(resources.getPropertyRent())+StringUtils.isNotNullBigDecimal(resources.getSiteRent())+StringUtils.isNotNullBigDecimal(resources.getWaterRent())+StringUtils.isNotNullBigDecimal(resources.getTaxCost()));
                if(financeMaintarinDetails.getRemaining().doubleValue()>=price){
                    parkCostRepository.save(resources);

                    //房租
                    if ( resources.getSiteRent()!=null){
                        if (StringUtils.iseqBigDecimal(resources.getSiteRent())){
                            fundFlowingService.createByPostCost(p,"1",resources.getSiteRent(),new BigDecimal(0.00));
                        }
                    }
                    //水费
                    if (resources.getWaterRent()!=null ){
                        if (StringUtils.iseqBigDecimal(resources.getWaterRent())){
                            fundFlowingService.createByPostCost(p,"7",resources.getWaterRent(),new BigDecimal(0.00));
                        }
                    }
                    //电费
                    if (resources.getElectricityRent()!=null){
                        if (StringUtils.iseqBigDecimal(resources.getElectricityRent())){
                            fundFlowingService.createByPostCost(p,"8",resources.getElectricityRent(),new BigDecimal(0.00));
                        }
                    }
                    //物业费
                    if (resources.getPropertyRent()!=null){
                        if (StringUtils.iseqBigDecimal(resources.getPropertyRent())){
                            fundFlowingService.createByPostCost(p,"10",resources.getPropertyRent(),new BigDecimal(0.00));
                        }
                    }
                    //税赋成本
                    if (resources.getTaxCost()!=null) {
                        if (StringUtils.iseqBigDecimal(resources.getTaxCost())) {
                            fundFlowingService.createByPostCost(p, "11", resources.getTaxCost(),new BigDecimal(0.00));
                        }
                    }
                    //其他费用
                    if (resources.getOtherRent()!=null){
                        if (StringUtils.iseqBigDecimal(resources.getOtherRent())) {
                            fundFlowingService.createByPostCost(p, "9", resources.getOtherRent(),new BigDecimal(0.00));
                        }
                    }
                    /*financeMaintarinDetails.setRemaining(new BigDecimal(financeMaintarinDetails.getRemaining().doubleValue()-price));
                    maintainDetailRepository.save(financeMaintarinDetails);*/
                }
                else{
                    throw new BadRequestException("账户余额不足,请更换支付方式");
                }
            }
            else{
                throw new BadRequestException("请先新建账户余额");
            }

      }
        return parkCostMapper.toDto(p);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ParkCost resources) {
        ParkCost parkCostBefore = parkCostRepository.findById(resources.getId()).get();
        Optional<ParkCost> optionalParkCost = parkCostRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalParkCost,"ParkCost","id",resources.getId());
        ParkCost parkCost = optionalParkCost.get();

        if(parkCost!=null){
            //根据支付方式和账户id查询账户详情
            MaintarinDetail maintarinDetails =maintainDetailRepository.findByTradTypeIdAndDeptId(resources.getDictDetail().getId(),resources.getDept().getId());
            if (maintarinDetails!=null){
                //修改之后的余额
                //Double price=(StringUtils.isNotNullBigDecimal(resources.getElectricityRent())+StringUtils.isNotNullBigDecimal(resources.getOtherRent())+StringUtils.isNotNullBigDecimal(resources.getPropertyRent())+StringUtils.isNotNullBigDecimal(resources.getSiteRent())+StringUtils.isNotNullBigDecimal(resources.getWaterRent())+StringUtils.isNotNullBigDecimal(resources.getTaxCost()));
                //修改之前的余额
                //Double beforePrice=(StringUtils.isNotNullBigDecimal(parkCostBefore.getElectricityRent())+StringUtils.isNotNullBigDecimal(parkCostBefore.getOtherRent())+StringUtils.isNotNullBigDecimal(parkCostBefore.getPropertyRent())+StringUtils.isNotNullBigDecimal(parkCostBefore.getSiteRent())+StringUtils.isNotNullBigDecimal(parkCostBefore.getWaterRent())+StringUtils.isNotNullBigDecimal(parkCostBefore.getTaxCost()));
                //判断当前帐户余额和修改后的金额,如果为负数就不做修改
                    //修改资金流水(判断金额如果相同则不做修改)
                    if ( resources.getSiteRent()!=null&&resources.getSiteRent().compareTo(parkCost.getSiteRent()==null?new BigDecimal(0.00):parkCost.getSiteRent())!=0){
                            BigDecimal Difference = new BigDecimal( (StringUtils.isNotNullBigDecimal(resources.getSiteRent()) - (StringUtils.isNotNullBigDecimal(parkCostBefore.getSiteRent()))));
                            fundFlowingService.createByPostCost(resources,"1",resources.getSiteRent(),Difference);

                    }
                    //水费
                    if (resources.getWaterRent()!=null&&resources.getWaterRent().compareTo(parkCost.getWaterRent()==null?new BigDecimal(0.00):parkCost.getWaterRent())!=0 ){
                            BigDecimal Difference = new BigDecimal( (StringUtils.isNotNullBigDecimal(resources.getWaterRent()) - (StringUtils.isNotNullBigDecimal(parkCostBefore.getWaterRent()))));
                            fundFlowingService.createByPostCost(resources,"7",resources.getWaterRent(),Difference);

                    }
                    //电费
                    if (resources.getElectricityRent()!=null&&resources.getElectricityRent().compareTo(parkCost.getElectricityRent()==null?new BigDecimal(0.00):parkCost.getElectricityRent())!=0){
                        if (StringUtils.iseqBigDecimal(resources.getElectricityRent())){
                            BigDecimal Difference = new BigDecimal( (StringUtils.isNotNullBigDecimal(resources.getElectricityRent()) - (StringUtils.isNotNullBigDecimal(parkCostBefore.getElectricityRent()))));
                            fundFlowingService.createByPostCost(resources,"8",resources.getElectricityRent(),Difference);

                        }
                    }
                    //物业费
                    if (resources.getPropertyRent()!=null&&resources.getPropertyRent().compareTo(parkCost.getPropertyRent()==null?new BigDecimal(0.00):parkCost.getPropertyRent())!=0){
                            BigDecimal Difference = new BigDecimal( (StringUtils.isNotNullBigDecimal(resources.getPropertyRent()) - (StringUtils.isNotNullBigDecimal(parkCostBefore.getPropertyRent()))));
                            fundFlowingService.createByPostCost(resources,"10",resources.getPropertyRent(),Difference);
                    }
                    //税赋成本
                    if (resources.getTaxCost()!=null&&resources.getTaxCost().compareTo(parkCost.getTaxCost()==null?new BigDecimal(0.00):parkCost.getTaxCost())!=0) {
                            BigDecimal Difference = new BigDecimal( (StringUtils.isNotNullBigDecimal(resources.getTaxCost()) - (StringUtils.isNotNullBigDecimal(parkCostBefore.getTaxCost()))));
                            fundFlowingService.createByPostCost(resources,"11",resources.getTaxCost(),Difference);

                    }
                    //其他费用
                    if (resources.getOtherRent()!=null&&resources.getOtherRent().compareTo(parkCost.getOtherRent()==null?new BigDecimal(0.00):parkCost.getOtherRent())!=0){
                            BigDecimal Difference = new BigDecimal( (StringUtils.isNotNullBigDecimal(resources.getOtherRent()) - (StringUtils.isNotNullBigDecimal(parkCostBefore.getOtherRent()))));
                            fundFlowingService.createByPostCost(resources,"9",resources.getOtherRent(),Difference);

                    }
                    parkCost.copy(resources);
                    parkCostRepository.save(parkCost);
            }
            else{
                    throw new BadRequestException("请先新建账户余额");
            }

        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        parkCostRepository.deleteById(id);
    }

    @Override
    public Object findCostsMoney(Long deptId) {

        return parkCostRepository.findByDeptIdSumRent(deptId);
    }
}

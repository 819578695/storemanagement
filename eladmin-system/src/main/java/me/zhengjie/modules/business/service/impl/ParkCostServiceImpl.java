package me.zhengjie.modules.business.service.impl;

import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.domain.Archivesmouthsmanagement;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.repository.ArchivesmouthsmanagementRepository;
import me.zhengjie.modules.basic_management.thearchives.repository.BasicsParkRepository;
import me.zhengjie.modules.business.domain.ParkCost;
import me.zhengjie.modules.business.domain.ReceiptPaymentAccount;
import me.zhengjie.modules.business.domain.RentContract;
import me.zhengjie.modules.business.repository.ReceiptPaymentAccountRepository;
import me.zhengjie.modules.business.repository.RentContractRepository;
import me.zhengjie.modules.business.service.dto.ReceiptPaymentAccountDTO;
import me.zhengjie.modules.finance.domain.FundFlowing;
import me.zhengjie.modules.finance.domain.MaintarinDetail;
import me.zhengjie.modules.finance.repository.FundFlowingRepository;
import me.zhengjie.modules.finance.repository.MaintainRepository;
import me.zhengjie.modules.finance.repository.MaintarinDetailRepository;
import me.zhengjie.modules.finance.service.FundFlowingService;
import me.zhengjie.modules.finance.service.MarginService;
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
    @Autowired
    private MarginService marginService;

    @Override
    public Object queryAll(ParkCostQueryCriteria criteria, Pageable pageable){
        Page<ParkCost> page = parkCostRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        List<ParkCostDTO> parkCostDTOS = new ArrayList<>();
        for (ParkCost parkCost : page.getContent()) {
            parkCostDTOS.add(parkCostMapper.toDto(parkCost,parkCost.getDept()==null?null:deptRepository.findAllById(parkCost.getDept().getId()),parkCost.getDictDetail()==null?null:dictDetailRepository.findById(parkCost.getDictDetail().getId()).get(),parkCost.getRentContract()==null?null:rentContractRepository.findById(parkCost.getRentContract().getId()).get(),parkCost.getReceiptPaymentAccount()==null?null:receiptPaymentAccountRepository.findById(parkCost.getReceiptPaymentAccount().getId()).get()));
        }
        return PageUtil.toPage(parkCostDTOS,page.getTotalElements());
    }
    @Override
    public Object queryAll( Pageable pageable){

        return parkCostMapper.toDto(parkCostRepository.findAll(pageable).getContent());
    }
    @Override
    public Object queryAll(ParkCostQueryCriteria criteria){
        List<ParkCost> list = parkCostRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder));
        List<ParkCostDTO> parkCostDTOS = new ArrayList<>();
        for (ParkCost parkCost : list) {
            parkCostDTOS.add(parkCostMapper.toDto(parkCost,parkCost.getDept()==null?null:deptRepository.findAllById(parkCost.getDept().getId()),parkCost.getDictDetail()==null?null:dictDetailRepository.findById(parkCost.getDictDetail().getId()).get(),parkCost.getRentContract()==null?null:rentContractRepository.findById(parkCost.getRentContract().getId()).get(),parkCost.getReceiptPaymentAccount()==null?null:receiptPaymentAccountRepository.findById(parkCost.getReceiptPaymentAccount().getId()).get()));
        }
        return PageUtil.toPage(parkCostDTOS,null);
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
//      MaintarinDetail financeMaintarinDetails =maintainDetailRepository.findByTradTypeIdAndDeptId(p.getDictDetail().getId(),p.getDept().getId());
          ReceiptPaymentAccount receiptPaymentAccount = receiptPaymentAccountRepository.findById(resources.getReceiptPaymentAccount().getId()).get();
          if (receiptPaymentAccount!=null){
                //修改账户详情的余额
                Double price=(StringUtils.isNotNullBigDecimal(resources.getElectricityRent())+StringUtils.isNotNullBigDecimal(resources.getOtherRent())+StringUtils.isNotNullBigDecimal(resources.getPropertyRent())+StringUtils.isNotNullBigDecimal(resources.getSiteRent())+StringUtils.isNotNullBigDecimal(resources.getWaterRent())+StringUtils.isNotNullBigDecimal(resources.getTaxCost()));
                if(receiptPaymentAccount.getRemaining().doubleValue()>=price){
                    parkCostRepository.save(resources);
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
            ReceiptPaymentAccount receiptPaymentAccount = receiptPaymentAccountRepository.findById(resources.getReceiptPaymentAccount().getId()).get();

            if (receiptPaymentAccount!=null){
                //修改账户详情的余额
                Double price=(StringUtils.isNotNullBigDecimal(resources.getElectricityRent())+StringUtils.isNotNullBigDecimal(resources.getOtherRent())+StringUtils.isNotNullBigDecimal(resources.getPropertyRent())+StringUtils.isNotNullBigDecimal(resources.getSiteRent())+StringUtils.isNotNullBigDecimal(resources.getWaterRent())+StringUtils.isNotNullBigDecimal(resources.getTaxCost()));
                if(receiptPaymentAccount.getRemaining().doubleValue()>=price){
                    parkCost.copy(resources);
                    parkCostRepository.save(parkCost);
                }
                else{
                    throw new BadRequestException("账户余额不足,请更换支付方式");
                }
            }
            else{
                    throw new BadRequestException("请先新建账户余额");
            }

        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        /*ParkCost p = parkCostRepository.findById(id).get();
        //根据支付类型和支出id查询对应的资金流水后
        Long typeId = dictDetailRepository.findByDictIdAndValue(dictRepository.findByName("trade_type").getId(),"1").getId();
      if (typeId!=null){
          //删除资金流水
          List<FundFlowing> fundFlowingList= fundFlowingRepository.findByTypeDictIdAndParkCostPevenueId(typeId,id);
          //当前账户支付方式
//          MaintarinDetail maintarinDetail=maintainDetailRepository.findByTradTypeIdAndDeptId(p.getDictDetail().getId(),p.getDept().getId());
          //当前账户支付方式详情
          ReceiptPaymentAccount receiptPaymentAccount = receiptPaymentAccountRepository.findById(p.getReceiptPaymentAccount().getId()).get();
          BigDecimal totalMoney = new BigDecimal(0);
          if(receiptPaymentAccount!=null){
              for (FundFlowing fundFlowing : fundFlowingList) {
                  if (fundFlowing.getMoney()!=null){
                      totalMoney = totalMoney.add(fundFlowing.getMoney());
                  }
                  if (fundFlowing!=null){
                      fundFlowingRepository.delete(fundFlowing);
                  }
              }
              receiptPaymentAccount.setRemaining(receiptPaymentAccount.getRemaining().add(totalMoney));
              receiptPaymentAccountRepository.save(receiptPaymentAccount);
          }
          else{
              throw new BadRequestException("请先新建账户余额");
          }
      }*/
        parkCostRepository.deleteById(id);
    }

    /*图表查询*/
    @Override
    public Object findCostsMoney(Long deptId) {

        return parkCostRepository.findByDeptIdSumRent(deptId);
    }

    /*审核*/
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void vertify(Long[] vertifys,Integer status) {
            if (vertifys.length>0){
                for (Long id : vertifys) {
                    ParkCost resources = parkCostRepository.findById(id).get();
                    /* 状态为审核中才能审核*/
                   if (resources.getIsVertify()==0){
                       //审核未通过
                       if (status==1){
                           resources.setIsVertify(1);
                           parkCostRepository.updateByVertify1(resources.getId());
                       }
                       //审核通过
                       if (status==2){
                           resources.setIsVertify(2);
                           parkCostRepository.updateByVertify2(resources.getId());
                           //需要插入资金流水
                           addFinance(resources);
                           addFundMargin(resources);//默认会向毛利表插入
                       }
                   }
                   else{
                       throw new BadRequestException("请勿重复审核");
                   }
                }
            }
    }

    /*新增资金流水*/
    public void addFinance (ParkCost resources){
        //房租
        if ( resources.getSiteRent()!=null){
            if (StringUtils.iseqBigDecimal(resources.getSiteRent())){
                fundFlowingService.createByPostCost(resources,"1",resources.getSiteRent(),new BigDecimal(0.00));
            }
        }
        //水费
        if (resources.getWaterRent()!=null ){
            if (StringUtils.iseqBigDecimal(resources.getWaterRent())){
                fundFlowingService.createByPostCost(resources,"7",resources.getWaterRent(),new BigDecimal(0.00));
            }
        }
        //电费
        if (resources.getElectricityRent()!=null){
            if (StringUtils.iseqBigDecimal(resources.getElectricityRent())){
                fundFlowingService.createByPostCost(resources,"8",resources.getElectricityRent(),new BigDecimal(0.00));
            }
        }
        //物业费
        if (resources.getPropertyRent()!=null){
            if (StringUtils.iseqBigDecimal(resources.getPropertyRent())){
                fundFlowingService.createByPostCost(resources,"10",resources.getPropertyRent(),new BigDecimal(0.00));
            }
        }
        //税赋成本
        if (resources.getTaxCost()!=null) {
            if (StringUtils.iseqBigDecimal(resources.getTaxCost())) {
                fundFlowingService.createByPostCost(resources, "11", resources.getTaxCost(),new BigDecimal(0.00));
            }
        }
        //其他费用
        if (resources.getOtherRent()!=null){
            if (StringUtils.iseqBigDecimal(resources.getOtherRent())) {
                fundFlowingService.createByPostCost(resources, "9", resources.getOtherRent(),new BigDecimal(0.00));
            }
        }
    }

    /*新增毛利表*/
    public void addFundMargin (ParkCost resources){
        //房租
        if ( resources.getSiteRent()!=null){
            if (StringUtils.iseqBigDecimal(resources.getSiteRent())){
                marginService.createByParkCost(resources,"1",resources.getSiteRent());
            }
        }
        //水费
        if (resources.getWaterRent()!=null ){
            if (StringUtils.iseqBigDecimal(resources.getWaterRent())){
                marginService.createByParkCost(resources,"7",resources.getWaterRent());
            }
        }
        //电费
        if (resources.getElectricityRent()!=null){
            if (StringUtils.iseqBigDecimal(resources.getElectricityRent())){
                marginService.createByParkCost(resources,"8",resources.getElectricityRent());
            }
        }
        //物业费
        if (resources.getPropertyRent()!=null){
            if (StringUtils.iseqBigDecimal(resources.getPropertyRent())){
                marginService.createByParkCost(resources,"10",resources.getPropertyRent());
            }
        }
        //税赋成本
        if (resources.getTaxCost()!=null) {
            if (StringUtils.iseqBigDecimal(resources.getTaxCost())) {
                marginService.createByParkCost(resources, "11", resources.getTaxCost());
            }
        }
        //其他费用
        if (resources.getOtherRent()!=null){
            if (StringUtils.iseqBigDecimal(resources.getOtherRent())) {
                marginService.createByParkCost(resources, "9", resources.getOtherRent());
            }
        }
    }
}

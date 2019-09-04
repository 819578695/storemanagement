package me.zhengjie.modules.business.service.impl;

import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.basic_management.thearchives.repository.BasicsParkRepository;
import me.zhengjie.modules.business.domain.ParkCost;
import me.zhengjie.modules.business.domain.RentContract;
import me.zhengjie.modules.business.repository.ReceiptPaymentAccountRepository;
import me.zhengjie.modules.business.repository.RentContractRepository;
import me.zhengjie.modules.finance.domain.FinanceMaintain;
import me.zhengjie.modules.finance.domain.FinanceMaintarinDetail;
import me.zhengjie.modules.finance.domain.JournalAccountOfCapital;
import me.zhengjie.modules.finance.repository.FinanceMaintainRepository;
import me.zhengjie.modules.finance.repository.FinanceMaintarinDetailRepository;
import me.zhengjie.modules.finance.repository.JournalAccountOfCapitalRepository;
import me.zhengjie.modules.finance.service.JournalAccountOfCapitalService;
import me.zhengjie.modules.system.domain.Dept;
import me.zhengjie.modules.system.domain.Dict;
import me.zhengjie.modules.system.domain.DictDetail;
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

import javax.sound.midi.SysexMessage;
import javax.xml.transform.Source;

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
    private JournalAccountOfCapitalRepository journalAccountOfCapitalRepository;
    @Autowired
    private  DictRepository dictRepository;
    @Autowired
    private FinanceMaintainRepository financeMaintainRepository;
    @Autowired
    private FinanceMaintarinDetailRepository financeMaintainDetailRepository;
    @Autowired
    private ReceiptPaymentAccountRepository receiptPaymentAccountRepository;
    @Autowired
    private JournalAccountOfCapitalService journalAccountOfCapitalService;

    @Override
    public Object queryAll(ParkCostQueryCriteria criteria, Pageable pageable){
        Page<ParkCost> page = parkCostRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        List<ParkCostDTO> parkCostDTOS = new ArrayList<>();
        for (ParkCost parkCost : page.getContent()) {
        Optional<RentContract> rentContract =  rentContractRepository.findById(parkCost.getRentContract().getId());
            parkCostDTOS.add(parkCostMapper.toDto(parkCost,deptRepository.findAllById(parkCost.getDept().getId()),dictDetailRepository.findById(parkCost.getDictDetail().getId()).get(),basicsParkRepository.findById(parkCost.getBasicsPark().getId()).get(),rentContract.get(),receiptPaymentAccountRepository.findById(parkCost.getReceiptPaymentAccount().getId()).get()));
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
            FinanceMaintarinDetail financeMaintarinDetails =financeMaintainDetailRepository.findByTradTypeIdAndDeptId(p.getDictDetail().getId(),p.getDept().getId());
            if (financeMaintarinDetails!=null){
                //修改账户详情的余额
                Double price=(StringUtils.isNotNullBigDecimal(resources.getElectricityRent())+StringUtils.isNotNullBigDecimal(resources.getOtherRent())+StringUtils.isNotNullBigDecimal(resources.getPropertyRent())+StringUtils.isNotNullBigDecimal(resources.getSiteRent())+StringUtils.isNotNullBigDecimal(resources.getWaterRent())+StringUtils.isNotNullBigDecimal(resources.getTaxCost()));
                if(financeMaintarinDetails.getRemaining().doubleValue()>=price){
                    financeMaintarinDetails.setRemaining(new BigDecimal(financeMaintarinDetails.getRemaining().doubleValue()-price));
                    parkCostRepository.save(resources);
                    financeMaintainDetailRepository.save(financeMaintarinDetails);
                    //房租
                    if ( resources.getSiteRent()!=null){
                        if (StringUtils.iseqBigDecimal(resources.getSiteRent())){
                            journalAccountOfCapitalService.createByPostCost(p,"1",resources.getSiteRent());
                        }
                    }
                    //水费
                    if (resources.getWaterRent()!=null ){
                        if (StringUtils.iseqBigDecimal(resources.getWaterRent())){
                            journalAccountOfCapitalService.createByPostCost(p,"7",resources.getWaterRent());
                        }
                    }
                    //电费
                    if (resources.getElectricityRent()!=null){
                        if (StringUtils.iseqBigDecimal(resources.getElectricityRent())){
                            journalAccountOfCapitalService.createByPostCost(p,"8",resources.getElectricityRent());
                        }
                    }
                    //物业费
                    if (resources.getPropertyRent()!=null){
                        if (StringUtils.iseqBigDecimal(resources.getPropertyRent())){
                            journalAccountOfCapitalService.createByPostCost(p,"10",resources.getPropertyRent());
                        }
                    }
                    //税赋成本
                    if (resources.getTaxCost()!=null) {
                        if (StringUtils.iseqBigDecimal(resources.getTaxCost())) {
                            journalAccountOfCapitalService.createByPostCost(p, "11", resources.getTaxCost());
                        }
                    }
                    //其他费用
                    if (resources.getOtherRent()!=null){
                        if (StringUtils.iseqBigDecimal(resources.getOtherRent())) {
                            journalAccountOfCapitalService.createByPostCost(p, "9", resources.getOtherRent());
                        }
                    }
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
            FinanceMaintarinDetail financeMaintarinDetails =financeMaintainDetailRepository.findByTradTypeIdAndDeptId(parkCost.getDictDetail().getId(),parkCost.getDept().getId());
            if (financeMaintarinDetails!=null){
                //修改之后的余额
                Double price=(StringUtils.isNotNullBigDecimal(resources.getElectricityRent())+StringUtils.isNotNullBigDecimal(resources.getOtherRent())+StringUtils.isNotNullBigDecimal(resources.getPropertyRent())+StringUtils.isNotNullBigDecimal(resources.getSiteRent())+StringUtils.isNotNullBigDecimal(resources.getWaterRent())+StringUtils.isNotNullBigDecimal(resources.getTaxCost()));
                //修改之前的余额
                Double beforePrice=(StringUtils.isNotNullBigDecimal(parkCostBefore.getElectricityRent())+StringUtils.isNotNullBigDecimal(parkCostBefore.getOtherRent())+StringUtils.isNotNullBigDecimal(parkCostBefore.getPropertyRent())+StringUtils.isNotNullBigDecimal(parkCostBefore.getSiteRent())+StringUtils.isNotNullBigDecimal(parkCostBefore.getWaterRent())+StringUtils.isNotNullBigDecimal(parkCostBefore.getTaxCost()));
                BigDecimal Difference = new BigDecimal(financeMaintarinDetails.getRemaining().doubleValue() + (price - beforePrice));
                //判断当前帐户余额和修改后的金额,如果为负数就不做修改
                if (Difference.signum()!=-1) {
                    //修改账户详情的余额
                    financeMaintarinDetails.setRemaining(Difference);
                    financeMaintainDetailRepository.save(financeMaintarinDetails);
                    parkCost.copy(resources);
                    parkCostRepository.save(parkCost);
                    //修改资金流水
                    if ( resources.getSiteRent()!=null){
                        if (StringUtils.iseqBigDecimal(resources.getSiteRent())){
                            journalAccountOfCapitalService.createByPostCost(parkCost,"1",resources.getSiteRent());
                        }
                    }
                    //水费
                    if (resources.getWaterRent()!=null ){
                        if (StringUtils.iseqBigDecimal(resources.getWaterRent())){
                            journalAccountOfCapitalService.createByPostCost(parkCost,"7",resources.getWaterRent());

                        }
                    }
                    //电费
                    if (resources.getElectricityRent()!=null){
                        if (StringUtils.iseqBigDecimal(resources.getElectricityRent())){
                            journalAccountOfCapitalService.createByPostCost(parkCost,"8",resources.getElectricityRent());

                        }
                    }
                    //物业费
                    if (resources.getPropertyRent()!=null){
                        if (StringUtils.iseqBigDecimal(resources.getPropertyRent())){
                            journalAccountOfCapitalService.createByPostCost(parkCost,"10",resources.getPropertyRent());

                        }
                    }
                    //税赋成本
                    if (resources.getTaxCost()!=null) {
                        if (StringUtils.iseqBigDecimal(resources.getTaxCost())) {
                            journalAccountOfCapitalService.createByPostCost(parkCost,"11",resources.getTaxCost());

                        }
                    }
                    //其他费用
                    if (resources.getOtherRent()!=null){
                        if (StringUtils.iseqBigDecimal(resources.getOtherRent())) {
                            journalAccountOfCapitalService.createByPostCost(parkCost,"9",resources.getOtherRent());

                        }
                    }

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
        parkCostRepository.deleteById(id);
    }
}

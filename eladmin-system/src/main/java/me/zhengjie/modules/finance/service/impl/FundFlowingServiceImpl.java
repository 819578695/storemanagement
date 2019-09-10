package me.zhengjie.modules.finance.service.impl;

import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.business.domain.ParkCost;
import me.zhengjie.modules.business.domain.ParkPevenue;
import me.zhengjie.modules.business.domain.ReceiptPaymentAccount;
import me.zhengjie.modules.business.repository.ReceiptPaymentAccountRepository;
import me.zhengjie.modules.finance.domain.FundFlowing;
import me.zhengjie.modules.finance.domain.MaintarinDetail;
import me.zhengjie.modules.finance.repository.MaintarinDetailRepository;
import me.zhengjie.modules.finance.service.dto.FundFlowingDTO;
import me.zhengjie.modules.finance.service.mapper.FundFlowingMapper;
import me.zhengjie.modules.system.domain.Dept;
import me.zhengjie.modules.system.domain.DictDetail;
import me.zhengjie.modules.system.repository.DeptRepository;
import me.zhengjie.modules.system.repository.DictDetailRepository;
import me.zhengjie.modules.system.repository.DictRepository;
import me.zhengjie.utils.StringUtils;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.finance.repository.FundFlowingRepository;
import me.zhengjie.modules.finance.service.FundFlowingService;
import me.zhengjie.modules.finance.service.dto.FundFlowingQueryCriteria;
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
* @date 2019-08-22
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class FundFlowingServiceImpl implements FundFlowingService {

    @Autowired
    private FundFlowingRepository fundFlowingRepository;

    @Autowired
    private FundFlowingMapper fundFlowingMapper;

    @Autowired
    private  DictDetailRepository dictDetailRepository;

    @Autowired
    private DeptRepository deptRepository;

    @Autowired
    private DictRepository dictRepository;

    @Autowired
    private ReceiptPaymentAccountRepository receiptPaymentAccountRepository;
    @Autowired
    private MaintarinDetailRepository maintarinDetailRepository;

    @Override
    public Object queryAll(FundFlowingQueryCriteria criteria, Pageable pageable){
        Page<FundFlowing> page = fundFlowingRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        List<FundFlowingDTO> fundFlowingDTOS = new ArrayList<>();
        for (FundFlowing fundFlowing : page.getContent()) {
            fundFlowingDTOS.add(fundFlowingMapper.toDTO(fundFlowing , dictDetailRepository.findById(fundFlowing.getTradType().getId()).get() , dictDetailRepository.findById(fundFlowing.getTallyType().getId()).get() , dictDetailRepository.findById(fundFlowing.getTypeDict().getId()).get() , deptRepository.findById(fundFlowing.getDept().getId()).get() ));
        }
        return PageUtil.toPage(fundFlowingDTOS,page.getTotalElements());
    }

    @Override
    public Object queryAll(FundFlowingQueryCriteria criteria){
        return fundFlowingMapper.toDto(fundFlowingRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public FundFlowingDTO findById(Long id) {
        Optional<FundFlowing> fundFlowing = fundFlowingRepository.findById(id);
        ValidationUtil.isNull( fundFlowing,"FundFlowing","id",id);
        return fundFlowingMapper.toDto(fundFlowing.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FundFlowingDTO create(FundFlowing resources) {
        return fundFlowingMapper.toDto(fundFlowingRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FundFlowingDTO createByPostCost(ParkCost resources, String value, BigDecimal money,BigDecimal substactMoney) {
        Dept dept = resources.getDept();//部门
        DictDetail TradType = resources.getDictDetail();//支付方式
        DictDetail typeDict =dictDetailRepository.findByDictIdAndValue(dictRepository.findByName("trade_type").getId(),"1");//支出
        DictDetail tallyType =dictDetailRepository.findByDictIdAndValue(dictRepository.findByName("transaction_type").getId(),value);//支出
        //根据部门id和支付方式查询账户金额
        MaintarinDetail maintarinDetail=maintarinDetailRepository.findByTradTypeIdAndDeptId(resources.getDictDetail().getId(),resources.getDept().getId());
        FundFlowing fundFlowing= fundFlowingRepository.findByTallyTypeIdAndTypeDictIdAndParkCostPevenueId(dictDetailRepository.findByDictIdAndValue(dictRepository.findByName("transaction_type").getId(),value).getId(),dictDetailRepository.findByDictIdAndValue(dictRepository.findByName("trade_type").getId(),"1").getId(),resources.getId());
        if (maintarinDetail!=null)
        {
            //默认修改(根据资金流水里的金额和修改之后的金额并修改后的账户余额)
            BigDecimal a = new BigDecimal(0);
            //账户余额和支出金额判断
            if (maintarinDetail.getRemaining().doubleValue()>=substactMoney.doubleValue())
                {
                    //根据交易类型以及成本id查询是否有对应费用
                    //如果为空则添加
                    if (fundFlowing==null){
                        a = maintarinDetail.getRemaining().subtract(money);
                        fundFlowing=new FundFlowing();
                        maintarinDetail.setRemaining(maintarinDetail.getRemaining().subtract(money));
                    }
                    else{
                        a = fundFlowing.getMoney().subtract(money).add(fundFlowing.getUrrentBalance());
                        if (maintarinDetail.getRemaining().subtract(substactMoney).signum()!=-1) {
                            maintarinDetail.setRemaining(maintarinDetail.getRemaining().subtract(substactMoney));
                        }
                        else{
                            throw new BadRequestException("账户余额不足,无法操作");
                        }

                    }
                    //查询收付款信息
                    ReceiptPaymentAccount receiptPaymentAccount = receiptPaymentAccountRepository.findById(resources.getReceiptPaymentAccount().getId()).get();
                    fundFlowing.setUrrentBalance(a);//发生交易后的金额
                    fundFlowing.setDept(dept);//部门
                    fundFlowing.setParkCostPevenueId(resources.getId());//成本收入id
                    fundFlowing.setMoney(money);//交易金额
                    fundFlowing.setTradType(TradType);//支付方式
                    fundFlowing.setTypeDict(typeDict);//交易类型
                    fundFlowing.setTallyType(tallyType);//收入支出项
                    fundFlowing.setReceiptPaymentName(receiptPaymentAccount.getPaymentAccount());//付款信息
                    fundFlowing.setBackAccount(receiptPaymentAccount.getPaymentAccountNum());//银行账号
                    fundFlowing.setBackNum(receiptPaymentAccount.getPaymentAccount());//开户名
                    maintarinDetailRepository.save(maintarinDetail);
                }
            else{
                throw new BadRequestException("账户余额不足,无法操作");
            }
        }
        else{
            throw new BadRequestException("请先新建账户余额");
        }
        return fundFlowingMapper.toDto(fundFlowingRepository.save(fundFlowing));
    }

    @Override
    public FundFlowingDTO createByPostPevenue(ParkPevenue resources, String value, BigDecimal money) {
        Dept dept = resources.getDept();//部门
        DictDetail TradType = resources.getDictDetail();//支付方式
        DictDetail typeDict =dictDetailRepository.findByDictIdAndValue(dictRepository.findByName("trade_type").getId(),"0");//收入
        DictDetail tallyType =dictDetailRepository.findByDictIdAndValue(dictRepository.findByName("transaction_type").getId(),value);
        //根据部门id和支付方式查询账户金额
        MaintarinDetail maintarinDetail=maintarinDetailRepository.findByTradTypeIdAndDeptId(resources.getDictDetail().getId(),resources.getDept().getId());
        //根据交易类型以及收入id查询是否有对应费用
        FundFlowing fundFlowing= fundFlowingRepository.findByTallyTypeIdAndTypeDictIdAndParkCostPevenueId(dictDetailRepository.findByDictIdAndValue(dictRepository.findByName("transaction_type").getId(),value).getId(),dictDetailRepository.findByDictIdAndValue(dictRepository.findByName("trade_type").getId(),"0").getId(),resources.getId());
        if (maintarinDetail!=null){
            //默认修改(根据资金流水里的金额和修改之后的金额并修改后的账户余额)
            BigDecimal a= fundFlowing.getMoney().subtract(money).add(fundFlowing.getUrrentBalance());
            //如果为空则添加
            if (fundFlowing==null){
                //账户余额添加金额
                a = maintarinDetail.getRemaining().add(money);
                fundFlowing=new FundFlowing();
            }
            ReceiptPaymentAccount receiptPaymentAccount = receiptPaymentAccountRepository.findById(resources.getReceiptPaymentAccount().getId()).get();
            fundFlowing.setReceiptPaymentName(receiptPaymentAccount.getReceiptAccount());//收付款信息
            fundFlowing.setBackAccount(receiptPaymentAccount.getReceiptAccountNum());//银行账号
            fundFlowing.setBackNum(receiptPaymentAccount.getReceiptBank());//开户名

            fundFlowing.setUrrentBalance(a);//发生交易后的金额
            fundFlowing.setDept(dept);//部门
            fundFlowing.setParkCostPevenueId(resources.getId());//成本收入id
            fundFlowing.setMoney(money);//交易金额
            fundFlowing.setTradType(TradType);//支付方式
            fundFlowing.setTypeDict(typeDict);//交易类型
            fundFlowing.setTallyType(tallyType);//收入支出项
        }
        else{
            throw new BadRequestException("请先新建账户余额");
        }

        return fundFlowingMapper.toDto(fundFlowingRepository.save(fundFlowing));
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(FundFlowing resources) {
        Optional<FundFlowing> optionalFundFlowing = fundFlowingRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalFundFlowing,"FundFlowing","id",resources.getId());
        FundFlowing fundFlowing = optionalFundFlowing.get();
        fundFlowing.copy(resources);
        fundFlowingRepository.save(fundFlowing);
    }

    @Override
    public void updateByPostCost(ParkCost resources, String value, BigDecimal money) {

    }

    @Override
    public void updateByPostPevenue(ParkPevenue resources, String value, BigDecimal money) {

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        fundFlowingRepository.deleteById(id);
    }
}

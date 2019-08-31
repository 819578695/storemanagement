package me.zhengjie.modules.finance.service.impl;

import me.zhengjie.modules.business.domain.ParkCost;
import me.zhengjie.modules.business.domain.ParkPevenue;
import me.zhengjie.modules.finance.domain.JournalAccountOfCapital;
import me.zhengjie.modules.system.domain.Dept;
import me.zhengjie.modules.system.domain.DictDetail;
import me.zhengjie.modules.system.repository.DeptRepository;
import me.zhengjie.modules.system.repository.DictDetailRepository;
import me.zhengjie.modules.system.repository.DictRepository;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.finance.repository.JournalAccountOfCapitalRepository;
import me.zhengjie.modules.finance.service.JournalAccountOfCapitalService;
import me.zhengjie.modules.finance.service.dto.JournalAccountOfCapitalDTO;
import me.zhengjie.modules.finance.service.dto.JournalAccountOfCapitalQueryCriteria;
import me.zhengjie.modules.finance.service.mapper.JournalAccountOfCapitalMapper;
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
public class JournalAccountOfCapitalServiceImpl implements JournalAccountOfCapitalService {

    @Autowired
    private JournalAccountOfCapitalRepository journalAccountOfCapitalRepository;

    @Autowired
    private JournalAccountOfCapitalMapper journalAccountOfCapitalMapper;

    @Autowired
    private  DictDetailRepository dictDetailRepository;

    @Autowired
    private DeptRepository deptRepository;

    @Autowired
    private DictRepository dictRepository;

    @Override
    public Object queryAll(JournalAccountOfCapitalQueryCriteria criteria, Pageable pageable){
        Page<JournalAccountOfCapital> page = journalAccountOfCapitalRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        List<JournalAccountOfCapitalDTO> procurementInformationDTOS = new ArrayList<>();
        for (JournalAccountOfCapital journalAccountOfCapital : page.getContent()) {
            procurementInformationDTOS.add(journalAccountOfCapitalMapper.toDTO(journalAccountOfCapital , dictDetailRepository.findById(journalAccountOfCapital.getTradType().getId()).get() , dictDetailRepository.findById(journalAccountOfCapital.getTallyType().getId()).get() , dictDetailRepository.findById(journalAccountOfCapital.getTypeDict().getId()).get() , deptRepository.findById(journalAccountOfCapital.getDept().getId()).get() ));
        }
        return PageUtil.toPage(procurementInformationDTOS,page.getTotalElements());
    }

    @Override
    public Object queryAll(JournalAccountOfCapitalQueryCriteria criteria){
        return journalAccountOfCapitalMapper.toDto(journalAccountOfCapitalRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public JournalAccountOfCapitalDTO findById(Long id) {
        Optional<JournalAccountOfCapital> journalAccountOfCapital = journalAccountOfCapitalRepository.findById(id);
        ValidationUtil.isNull(journalAccountOfCapital,"JournalAccountOfCapital","id",id);
        return journalAccountOfCapitalMapper.toDto(journalAccountOfCapital.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JournalAccountOfCapitalDTO create(JournalAccountOfCapital resources) {
        return journalAccountOfCapitalMapper.toDto(journalAccountOfCapitalRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JournalAccountOfCapitalDTO createByPostCost(ParkCost resources, String value, BigDecimal money) {
        Dept dept = resources.getDept();//部门
        DictDetail TradType = resources.getDictDetail();//支付方式
        DictDetail typeDict =dictDetailRepository.findByDictIdAndValue(dictRepository.findByName("trade_type").getId(),"1");//支出
        DictDetail tallyType =dictDetailRepository.findByDictIdAndValue(dictRepository.findByName("transaction_type").getId(),value);//支出

        //根据交易类型以及成本id查询是否有对应费用
        JournalAccountOfCapital journalAccountOfCapital=journalAccountOfCapitalRepository.findByTallyTypeIdAndTypeDictIdAndParkCostPevenueId(dictDetailRepository.findByDictIdAndValue(dictRepository.findByName("transaction_type").getId(),value).getId(),dictDetailRepository.findByDictIdAndValue(dictRepository.findByName("trade_type").getId(),"1").getId(),resources.getId());
        //如果为空则添加
        if (journalAccountOfCapital==null){
              journalAccountOfCapital=new JournalAccountOfCapital();
          }
        journalAccountOfCapital.setDept(dept);//部门
        journalAccountOfCapital.setParkCostPevenueId(resources.getId());//成本收入id
        journalAccountOfCapital.setMoney(money);//交易金额
        journalAccountOfCapital.setTradType(TradType);//支付方式
        journalAccountOfCapital.setTypeDict(typeDict);//交易类型
        journalAccountOfCapital.setTallyType(tallyType);//收入支出项
        journalAccountOfCapitalRepository.save(journalAccountOfCapital);



        return journalAccountOfCapitalMapper.toDto(journalAccountOfCapitalRepository.save(journalAccountOfCapital));
    }

    @Override
    public JournalAccountOfCapitalDTO createByPostPevenue(ParkPevenue resources, String value, BigDecimal money) {
        Dept dept = resources.getDept();//部门
        DictDetail TradType = resources.getDictDetail();//支付方式
        DictDetail typeDict =dictDetailRepository.findByDictIdAndValue(dictRepository.findByName("trade_type").getId(),"0");//收入
        DictDetail tallyType =dictDetailRepository.findByDictIdAndValue(dictRepository.findByName("transaction_type").getId(),value);
        JournalAccountOfCapital journalAccountOfCapital = new JournalAccountOfCapital();
        journalAccountOfCapital.setReceiptPaymentName(resources.getReceiptPaymentAccount().getReceiptAccount());//收付款信息
        journalAccountOfCapital.setBackAccount(resources.getReceiptPaymentAccount().getReceiptAccountNum());//银行账号
        journalAccountOfCapital.setBackNum(resources.getReceiptPaymentAccount().getReceiptBank());//开户名
        journalAccountOfCapital.setDept(dept);//部门
        journalAccountOfCapital.setParkCostPevenueId(resources.getId());//成本收入id
        journalAccountOfCapital.setMoney(money);//交易金额
        journalAccountOfCapital.setTradType(TradType);//支付方式
        journalAccountOfCapital.setTypeDict(typeDict);//交易类型
        journalAccountOfCapital.setTallyType(tallyType);//收入支出项
        journalAccountOfCapitalRepository.save(journalAccountOfCapital);

        return journalAccountOfCapitalMapper.toDto(journalAccountOfCapitalRepository.save(journalAccountOfCapital));
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(JournalAccountOfCapital resources) {
        Optional<JournalAccountOfCapital> optionalJournalAccountOfCapital = journalAccountOfCapitalRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalJournalAccountOfCapital,"JournalAccountOfCapital","id",resources.getId());
        JournalAccountOfCapital journalAccountOfCapital = optionalJournalAccountOfCapital.get();
        journalAccountOfCapital.copy(resources);
        journalAccountOfCapitalRepository.save(journalAccountOfCapital);
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
        journalAccountOfCapitalRepository.deleteById(id);
    }
}

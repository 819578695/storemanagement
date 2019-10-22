package me.zhengjie.modules.finance.service.impl;

import me.zhengjie.exception.BadRequestException;
import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.business.domain.ReceiptPaymentAccount;
import me.zhengjie.modules.business.domain.RentContract;
import me.zhengjie.modules.business.service.dto.ReceiptPaymentAccountDTO;
import me.zhengjie.modules.business.service.impl.ReceiptPaymentAccountServiceImpl;
import me.zhengjie.modules.business.service.impl.RentContractServiceImpl;
import me.zhengjie.modules.business.service.mapper.ReceiptPaymentAccountMapper;
import me.zhengjie.modules.finance.domain.FundFlowing;
import me.zhengjie.modules.finance.domain.MaintarinDetail;
import me.zhengjie.modules.finance.repository.MaintarinDetailRepository;
import me.zhengjie.modules.finance.service.MaintarinDetailService;
import me.zhengjie.modules.finance.service.dto.*;
import me.zhengjie.modules.finance.service.mapper.FundFlowingMapper;
import me.zhengjie.modules.finance.service.mapper.MaintarinDetailMapper;
import me.zhengjie.modules.system.domain.Dict;
import me.zhengjie.modules.system.domain.DictDetail;
import me.zhengjie.modules.system.repository.DeptRepository;
import me.zhengjie.modules.system.repository.DictDetailRepository;
import me.zhengjie.modules.system.repository.DictRepository;
import me.zhengjie.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;

/**
* @author nmk
* @date 2019-08-29
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MaintarinDetailServiceImpl implements MaintarinDetailService {

    @Autowired
    private MaintarinDetailRepository maintarinDetailRepository;

    @Autowired
    private MaintarinDetailMapper maintarinDetailMapper;

    @Autowired
    private ReceiptPaymentAccountServiceImpl receiptPaymentAccountService;

    @Autowired
    private ReceiptPaymentAccountMapper receiptPaymentAccountMapper;

    @Autowired
    private FundFlowingServiceImpl fundFlowingService;

    @Autowired
    private DictRepository DictRepository;

    @Autowired
    private DictDetailRepository DictDetailRepository;

    @Autowired
    private DeptRepository deptRepository;
    @Override
    public Object queryAll(MaintarinDetailQueryCriteria criteria, Pageable pageable){
        Page<MaintarinDetail> page = maintarinDetailRepository.findAllByDeptId(criteria.getDeptId(),pageable);
        List<DetailDTO> detailDTOS = maintarinDetailRepository.queryRemainings(criteria.getDeptId());
        PageUtil.toPage(detailDTOS,page.getTotalElements());
        return PageUtil.toPage(detailDTOS,page.getTotalElements());
    }

    @Override
    public Object queryAll(MaintarinDetailQueryCriteria criteria){
        return maintarinDetailMapper.toDto(maintarinDetailRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public MaintarinDetailDTO findById(Long id) {
        Optional<MaintarinDetail> maintarinDetail = maintarinDetailRepository.findById(id);
        ValidationUtil.isNull(maintarinDetail,"MaintarinDetail","id",id);
        return maintarinDetailMapper.toDto(maintarinDetail.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(MaintarinDetail resources) {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        if (queryExist(resources)){
            resources.setId(snowflake.nextId());
        }else {
            throw new BadRequestException("该支付方式已存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MaintarinDetail resources) {
        Optional<MaintarinDetail> optionalMaintarinDetail = maintarinDetailRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalMaintarinDetail,"MaintarinDetail","id",resources.getId());
        MaintarinDetail maintarinDetail = optionalMaintarinDetail.get();
        maintarinDetail.copy(resources);
        maintarinDetailRepository.save(maintarinDetail);
    }

    //校验是否存在
    public boolean queryExist(MaintarinDetail resources){
        Boolean b = false;
        if (maintarinDetailRepository.getDetailByDeptAndAndMaintainId(resources.getTradType().getId(),resources.getMaintainId()) != null){
            b=true;
        }
        return b;
    };

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        maintarinDetailRepository.deleteById(id);
    }


    @Override
    public Object getMoney(MaintarinDetailQueryCriteria criteria) {
        return maintarinDetailRepository.findByMaintainId(criteria.getDeptId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createDetail(AccountAllotDTO resources) {
        //源账号扣除金额
        detail(resources.getRemaining(),resources.getOriginId(),"subtract" , resources.getDeptId());
        //目标账户添加金额
        detail(resources.getRemaining(),resources.getTargetId(),"add" ,resources.getDeptId());

    }

    public void detail(BigDecimal remaing , List<Long> id ,String s ,Long deptId){
        //资金流水
        FundFlowing flowing = new FundFlowing();
        //获取账户信息
        ReceiptPaymentAccountDTO origin = receiptPaymentAccountService.findById(id.get(1));
        BigDecimal remaining = new BigDecimal("0");
        //扣除源账户金额
        if (s == "subtract" ){
            remaining = origin.getRemaining().subtract(remaing);
            if (remaining.doubleValue() < 0 ){
                throw new BadRequestException("源目标账户余额不足");
            }
            DictDetail tradeType = DictDetailRepository.findByDictIdAndValue(DictRepository.findByName("trade_type").getId(), "1");

            flowing.setTypeDict(tradeType);
        }else {
            //添加金额
            remaining =origin.getRemaining().add(remaing);
            DictDetail tradeType = DictDetailRepository.findByDictIdAndValue(DictRepository.findByName("trade_type").getId(), "0");
            flowing.setTypeDict(tradeType);
        }
        origin.setRemaining(remaining);
        //修改金额
        receiptPaymentAccountService.update(receiptPaymentAccountMapper.toEntity(origin));

        //向资金流水中添加操作记录
        MaintarinDetail maintarinDetail = maintarinDetailRepository.findById(id.get(0)).get();
        flowing.setTradType(maintarinDetail.getTradType());
        flowing.setMoney(remaing);
        //查询资金调拨状态
        flowing.setTallyType(DictDetailRepository.findByDictIdAndValue(DictRepository.findByName("transaction_type").getId(), "FUND_ALLOT"));
        //当前账户余额
        flowing.setUrrentBalance(origin.getRemaining());
        flowing.setReceiptPaymentName(origin.getAccountName());
        flowing.setBackNum(origin.getBankName());
        flowing.setBackAccount(origin.getBankName());
        flowing.setDept(deptRepository.findAllById(deptId));
        fundFlowingService.create(flowing);
    }

}



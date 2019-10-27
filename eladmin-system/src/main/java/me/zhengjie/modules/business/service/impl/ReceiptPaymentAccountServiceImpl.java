package me.zhengjie.modules.business.service.impl;

import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.business.domain.ParkCost;
import me.zhengjie.modules.business.domain.ParkPevenue;
import me.zhengjie.modules.business.domain.ReceiptPaymentAccount;
import me.zhengjie.modules.business.repository.ParkCostRepository;
import me.zhengjie.modules.business.repository.ParkPevenueRepository;
import me.zhengjie.modules.business.service.dto.AccountsDTO;
import me.zhengjie.modules.business.service.dto.ReceiptPaymentAccountSmallDTO;
import me.zhengjie.modules.finance.domain.MaintarinDetail;
import me.zhengjie.modules.finance.repository.MaintarinDetailRepository;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.business.repository.ReceiptPaymentAccountRepository;
import me.zhengjie.modules.business.service.ReceiptPaymentAccountService;
import me.zhengjie.modules.business.service.dto.ReceiptPaymentAccountDTO;
import me.zhengjie.modules.business.service.dto.ReceiptPaymentAccountQueryCriteria;
import me.zhengjie.modules.business.service.mapper.ReceiptPaymentAccountMapper;
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
* @date 2019-08-21
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ReceiptPaymentAccountServiceImpl implements ReceiptPaymentAccountService {

    @Autowired
    private ReceiptPaymentAccountRepository receiptPaymentAccountRepository;

    @Autowired
    private ReceiptPaymentAccountMapper receiptPaymentAccountMapper;
    @Autowired
    private MaintarinDetailRepository maintarinDetailRepository;
    @Autowired
    private ParkCostRepository parkCostRepository;
    @Autowired
    private ParkPevenueRepository parkPevenueRepository;
    @Override
    public Object queryAll(Pageable pageable) {
        return receiptPaymentAccountMapper.toDto(receiptPaymentAccountRepository.findAll(pageable).getContent());
    }


    @Override
    public Object queryAll(ReceiptPaymentAccountQueryCriteria criteria, Pageable pageable){
        Page<ReceiptPaymentAccount> page = receiptPaymentAccountRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(receiptPaymentAccountMapper::toDto));
    }

    @Override
    public Object queryAll(ReceiptPaymentAccountQueryCriteria criteria){
        return receiptPaymentAccountMapper.toDto(receiptPaymentAccountRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }
    @Override
    public Object findByDeptId(Long dictailId,Long deptId) {
        List<ReceiptPaymentAccountSmallDTO> receiptPaymentAccount=new ArrayList<>();
        if (dictailId!=null&&deptId!=null){
            receiptPaymentAccount=receiptPaymentAccountRepository.findByTradTypeIdAndDeptId(dictailId,deptId);
        }
        else{
            throw new BadRequestException("请先选择支付方式");
        }

        return receiptPaymentAccount;
    }
    @Override
    public ReceiptPaymentAccountDTO findById(Long id) {
        Optional<ReceiptPaymentAccount> receiptPaymentAccount = receiptPaymentAccountRepository.findById(id);
        ValidationUtil.isNull(receiptPaymentAccount,"ReceiptPaymentAccount","id",id);
        return receiptPaymentAccountMapper.toDto(receiptPaymentAccount.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReceiptPaymentAccountDTO create(ReceiptPaymentAccount resources) {
        return receiptPaymentAccountMapper.toDto(receiptPaymentAccountRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ReceiptPaymentAccount resources) {
        Optional<ReceiptPaymentAccount> optionalReceiptPaymentAccount = receiptPaymentAccountRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalReceiptPaymentAccount,"ReceiptPaymentAccount","id",resources.getId());
        ReceiptPaymentAccount receiptPaymentAccount = optionalReceiptPaymentAccount.get();
        receiptPaymentAccount.copy(resources);
        receiptPaymentAccountRepository.save(receiptPaymentAccount);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        ReceiptPaymentAccount receiptPaymentAccount = receiptPaymentAccountRepository.findById(id).get();
        if (receiptPaymentAccount.getRemaining().compareTo(BigDecimal.ZERO)==0 && parkCostRepository.findByReceiptPaymentAccount(id)==null && parkPevenueRepository.findByReceiptPaymentAccount(id) ==null ) {
            receiptPaymentAccountRepository.deleteById(id);
        }else {
            throw new BadRequestException("账户余额不为空或存在关联关系,无法删除");
        }
    }

    @Override
    public Object queryByDeptId(Long deptId) {
        List<MaintarinDetail> byDeptId = maintarinDetailRepository.findByDeptId(deptId);
        List list = new ArrayList<>();
        for (MaintarinDetail maintarinDetail :byDeptId){
            AccountsDTO accountsDTO = new AccountsDTO();
            accountsDTO.setValue(maintarinDetail.getId());
            accountsDTO.setLabel(maintarinDetail.getTradType().getLabel());
            List<AccountsDTO>childrens = new ArrayList<>();
            List<ReceiptPaymentAccount> byDetailId = receiptPaymentAccountRepository.findByDetailId(maintarinDetail.getId());
            for (ReceiptPaymentAccount receiptPaymentAccount : byDetailId) {
                AccountsDTO childrenAccountsDTO = new AccountsDTO();
                childrenAccountsDTO.setValue(receiptPaymentAccount.getId());
                childrenAccountsDTO.setLabel(receiptPaymentAccount.getName());
                childrens.add(childrenAccountsDTO);
            }
            accountsDTO.setChildren(childrens);
            list.add(accountsDTO);
        }
        return list;
    }
}

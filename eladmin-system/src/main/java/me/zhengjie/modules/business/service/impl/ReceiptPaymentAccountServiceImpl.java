package me.zhengjie.modules.business.service.impl;

import me.zhengjie.modules.business.domain.ReceiptPaymentAccount;
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

    @Override
    public Object queryAll(Pageable pageable ) {
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
    public Object findByDeptId(Long deptId) {
        return "";
        //return receiptPaymentAccountMapper.toDto(deptId==1?receiptPaymentAccountRepository.findAll():receiptPaymentAccountRepository.findByDeptId(deptId));
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
        receiptPaymentAccountRepository.deleteById(id);
    }
}

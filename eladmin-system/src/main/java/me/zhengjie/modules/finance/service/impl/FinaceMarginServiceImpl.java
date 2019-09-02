package me.zhengjie.modules.finance.service.impl;

import me.zhengjie.modules.finance.domain.FinaceMargin;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.finance.repository.FinaceMarginRepository;
import me.zhengjie.modules.finance.service.FinaceMarginService;
import me.zhengjie.modules.finance.service.dto.FinaceMarginDTO;
import me.zhengjie.modules.finance.service.dto.FinaceMarginQueryCriteria;
import me.zhengjie.modules.finance.service.mapper.FinaceMarginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
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
public class FinaceMarginServiceImpl implements FinaceMarginService {

    @Autowired
    private FinaceMarginRepository finaceMarginRepository;

    @Autowired
    private FinaceMarginMapper finaceMarginMapper;

    @Override
    public Object queryAll(FinaceMarginQueryCriteria criteria, Pageable pageable){
        Page<FinaceMargin> page = finaceMarginRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(finaceMarginMapper::toDto));
    }

    @Override
    public Object queryAll(FinaceMarginQueryCriteria criteria){
        return finaceMarginMapper.toDto(finaceMarginRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public FinaceMarginDTO findById(Long id) {
        Optional<FinaceMargin> finaceMargin = finaceMarginRepository.findById(id);
        ValidationUtil.isNull(finaceMargin,"FinaceMargin","id",id);
        return finaceMarginMapper.toDto(finaceMargin.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FinaceMarginDTO create(FinaceMargin resources) {
        return finaceMarginMapper.toDto(finaceMarginRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(FinaceMargin resources) {
        Optional<FinaceMargin> optionalFinaceMargin = finaceMarginRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalFinaceMargin,"FinaceMargin","id",resources.getId());
        FinaceMargin finaceMargin = optionalFinaceMargin.get();
        finaceMargin.copy(resources);
        finaceMarginRepository.save(finaceMargin);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        finaceMarginRepository.deleteById(id);
    }
}

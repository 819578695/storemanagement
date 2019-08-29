package me.zhengjie.modules.finance.service.impl;

import me.zhengjie.modules.finance.domain.FinanceMaintain;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.finance.repository.FinanceMaintainRepository;
import me.zhengjie.modules.finance.service.FinanceMaintainService;
import me.zhengjie.modules.finance.service.dto.FinanceMaintainDTO;
import me.zhengjie.modules.finance.service.dto.FinanceMaintainQueryCriteria;
import me.zhengjie.modules.finance.service.mapper.FinanceMaintainMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
public class FinanceMaintainServiceImpl implements FinanceMaintainService {

    @Autowired
    private FinanceMaintainRepository financeMaintainRepository;

    @Autowired
    private FinanceMaintainMapper financeMaintainMapper;

    @Override
    public Object queryAll(FinanceMaintainQueryCriteria criteria, Pageable pageable){
        Page<FinanceMaintain> page = financeMaintainRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(financeMaintainMapper::toDto));
    }

    @Override
    public Object queryAll(FinanceMaintainQueryCriteria criteria){
        return financeMaintainMapper.toDto(financeMaintainRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public FinanceMaintainDTO findById(Long id) {
        Optional<FinanceMaintain> financeMaintain = financeMaintainRepository.findById(id);
        ValidationUtil.isNull(financeMaintain,"FinanceMaintain","id",id);
        return financeMaintainMapper.toDto(financeMaintain.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FinanceMaintainDTO create(FinanceMaintain resources) {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        resources.setId(snowflake.nextId()); 
        return financeMaintainMapper.toDto(financeMaintainRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(FinanceMaintain resources) {
        Optional<FinanceMaintain> optionalFinanceMaintain = financeMaintainRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalFinanceMaintain,"FinanceMaintain","id",resources.getId());
        FinanceMaintain financeMaintain = optionalFinanceMaintain.get();
        financeMaintain.copy(resources);
        financeMaintainRepository.save(financeMaintain);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        financeMaintainRepository.deleteById(id);
    }
}
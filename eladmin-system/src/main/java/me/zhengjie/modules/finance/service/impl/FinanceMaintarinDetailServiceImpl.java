package me.zhengjie.modules.finance.service.impl;

import me.zhengjie.modules.finance.domain.FinanceMaintarinDetail;
import me.zhengjie.modules.system.repository.DictDetailRepository;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.finance.repository.FinanceMaintarinDetailRepository;
import me.zhengjie.modules.finance.service.FinanceMaintarinDetailService;
import me.zhengjie.modules.finance.service.dto.FinanceMaintarinDetailDTO;
import me.zhengjie.modules.finance.service.dto.FinanceMaintarinDetailQueryCriteria;
import me.zhengjie.modules.finance.service.mapper.FinanceMaintarinDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
public class FinanceMaintarinDetailServiceImpl implements FinanceMaintarinDetailService {

    @Autowired
    private FinanceMaintarinDetailRepository financeMaintarinDetailRepository;

    @Autowired
    private FinanceMaintarinDetailMapper financeMaintarinDetailMapper;

    @Autowired
    private DictDetailRepository dictDetailRepository;

    @Override
    public Object queryAll(FinanceMaintarinDetailQueryCriteria criteria, Pageable pageable){
        Page<FinanceMaintarinDetail> page = financeMaintarinDetailRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        List<FinanceMaintarinDetailDTO> financeMaintarinDetailDTOS = new ArrayList<>();
        for (FinanceMaintarinDetail financeMaintarinDetail : page.getContent()) {
            financeMaintarinDetailDTOS.add(financeMaintarinDetailMapper.toDTO(financeMaintarinDetail , dictDetailRepository.findById(financeMaintarinDetail.getTradType().getId()).get()));
        }
        return PageUtil.toPage(financeMaintarinDetailDTOS,page.getTotalElements());
    }

    @Override
    public Object queryAll(FinanceMaintarinDetailQueryCriteria criteria){
        return financeMaintarinDetailMapper.toDto(financeMaintarinDetailRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public FinanceMaintarinDetailDTO findById(Long id) {
        Optional<FinanceMaintarinDetail> financeMaintarinDetail = financeMaintarinDetailRepository.findById(id);
        ValidationUtil.isNull(financeMaintarinDetail,"FinanceMaintarinDetail","id",id);
        return financeMaintarinDetailMapper.toDto(financeMaintarinDetail.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FinanceMaintarinDetailDTO create(FinanceMaintarinDetail resources) {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        resources.setId(snowflake.nextId());
        return financeMaintarinDetailMapper.toDto(financeMaintarinDetailRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(FinanceMaintarinDetail resources) {
        Optional<FinanceMaintarinDetail> optionalFinanceMaintarinDetail = financeMaintarinDetailRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalFinanceMaintarinDetail,"FinanceMaintarinDetail","id",resources.getId());
        FinanceMaintarinDetail financeMaintarinDetail = optionalFinanceMaintarinDetail.get();
        financeMaintarinDetail.copy(resources);
        financeMaintarinDetailRepository.save(financeMaintarinDetail);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        financeMaintarinDetailRepository.deleteById(id);
    }
}

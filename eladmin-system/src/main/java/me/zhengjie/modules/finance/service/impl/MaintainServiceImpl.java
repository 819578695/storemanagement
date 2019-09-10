package me.zhengjie.modules.finance.service.impl;

import me.zhengjie.modules.finance.domain.Maintain;
import me.zhengjie.modules.finance.repository.MaintainRepository;
import me.zhengjie.modules.finance.repository.MaintarinDetailRepository;
import me.zhengjie.modules.finance.service.MaintainService;
import me.zhengjie.modules.finance.service.dto.MaintainDTO;
import me.zhengjie.modules.finance.service.dto.MaintainQueryCriteria;
import me.zhengjie.modules.finance.service.mapper.MaintainMapper;
import me.zhengjie.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
public class MaintainServiceImpl implements MaintainService {

    @Autowired
    private MaintainRepository maintainRepository;

    @Autowired
    private MaintainMapper maintainMapper;

    @Autowired
    private MaintarinDetailRepository maintarinDetailRepository;

    @Override
    public Object queryAll(MaintainQueryCriteria criteria, Pageable pageable){
            Page<Maintain> page = maintainRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
            List<MaintainDTO> MaintainDTOs = new ArrayList<>();
            for (Maintain maintain : page.getContent()) {
                MaintainDTO dto = maintainMapper.toDTO(maintain, maintain.getDept());
                BigDecimal maintainSum = maintarinDetailRepository.findByMaintainId(dto.getId());
                dto.setRemaining(maintainSum);
                MaintainDTOs.add(dto);
            }
            return PageUtil.toPage(MaintainDTOs, page.getTotalElements());
    }

    @Override
    public Object queryAll(MaintainQueryCriteria criteria){
        return maintainMapper.toDto(maintainRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public MaintainDTO findById(Long id) {
        Optional<Maintain> maintain = maintainRepository.findById(id);
        ValidationUtil.isNull(maintain,"Maintain","id",id);
        return maintainMapper.toDto(maintain.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MaintainDTO create(Maintain resources) {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        resources.setId(snowflake.nextId());
        return maintainMapper.toDto(maintainRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Maintain resources) {
        Optional<Maintain> optionalMaintain = maintainRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalMaintain,"Maintain","id",resources.getId());
        Maintain maintain = optionalMaintain.get();
        maintain.copy(resources);
        maintainRepository.save(maintain);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        maintainRepository.deleteById(id);
    }


}

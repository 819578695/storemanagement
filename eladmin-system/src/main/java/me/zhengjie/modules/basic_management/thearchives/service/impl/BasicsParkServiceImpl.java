package me.zhengjie.modules.basic_management.thearchives.service.impl;

import me.zhengjie.modules.basic_management.thearchives.domain.BasicsPark;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.basic_management.thearchives.repository.BasicsParkRepository;
import me.zhengjie.modules.basic_management.thearchives.service.BasicsParkService;
import me.zhengjie.modules.basic_management.thearchives.service.dto.BasicsParkDTO;
import me.zhengjie.modules.basic_management.thearchives.service.dto.BasicsParkQueryCriteria;
import me.zhengjie.modules.basic_management.thearchives.service.mapper.BasicsParkMapper;
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
* @author zlk
* @date 2019-08-26
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class BasicsParkServiceImpl implements BasicsParkService {

    @Autowired
    private BasicsParkRepository basicsParkRepository;

    @Autowired
    private BasicsParkMapper basicsParkMapper;

    @Override
    public Object queryAll(BasicsParkQueryCriteria criteria, Pageable pageable){
        Page<BasicsPark> page = basicsParkRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(basicsParkMapper::toDto));
    }

    @Override
    public Object queryAll(BasicsParkQueryCriteria criteria){
        return basicsParkMapper.toDto(basicsParkRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public BasicsParkDTO findById(Long id) {
        Optional<BasicsPark> basicsPark = basicsParkRepository.findById(id);
        ValidationUtil.isNull(basicsPark,"BasicsPark","id",id);
        return basicsParkMapper.toDto(basicsPark.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BasicsParkDTO create(BasicsPark resources) {
        return basicsParkMapper.toDto(basicsParkRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(BasicsPark resources) {
        Optional<BasicsPark> optionalBasicsPark = basicsParkRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalBasicsPark,"BasicsPark","id",resources.getId());
        BasicsPark basicsPark = optionalBasicsPark.get();
        basicsPark.copy(resources);
        basicsParkRepository.save(basicsPark);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        basicsParkRepository.deleteById(id);
    }
}
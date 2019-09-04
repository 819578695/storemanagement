package me.zhengjie.modules.basic_management.client.service.impl;

import me.zhengjie.modules.basic_management.client.domain.BasicsClient;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.basic_management.client.repository.BasicsClientRepository;
import me.zhengjie.modules.basic_management.client.service.BasicsClientService;
import me.zhengjie.modules.basic_management.client.service.dto.BasicsClientDTO;
import me.zhengjie.modules.basic_management.client.service.dto.BasicsClientQueryCriteria;
import me.zhengjie.modules.basic_management.client.service.mapper.BasicsClientMapper;
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
* @author nmkzlk
* @date 2019-09-02
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class BasicsClientServiceImpl implements BasicsClientService {

    @Autowired
    private BasicsClientRepository basicsClientRepository;

    @Autowired
    private BasicsClientMapper basicsClientMapper;

    @Override
    public Object queryAll(BasicsClientQueryCriteria criteria, Pageable pageable){
        Page<BasicsClient> page = basicsClientRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(basicsClientMapper::toDto));
    }

    @Override
    public Object queryAll(BasicsClientQueryCriteria criteria){
        return basicsClientMapper.toDto(basicsClientRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public BasicsClientDTO findById(Integer id) {
        Optional<BasicsClient> basicsClient = basicsClientRepository.findById(id);
        ValidationUtil.isNull(basicsClient,"BasicsClient","id",id);
        return basicsClientMapper.toDto(basicsClient.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BasicsClientDTO create(BasicsClient resources) {
        return basicsClientMapper.toDto(basicsClientRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(BasicsClient resources) {
        Optional<BasicsClient> optionalBasicsClient = basicsClientRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalBasicsClient,"BasicsClient","id",resources.getId());
        BasicsClient basicsClient = optionalBasicsClient.get();
        basicsClient.copy(resources);
        basicsClientRepository.save(basicsClient);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        basicsClientRepository.deleteById(id);
    }
}
package me.zhengjie.modules.finance.service.impl;

import me.zhengjie.modules.finance.domain.MaintarinDetail;
import me.zhengjie.modules.finance.repository.MaintarinDetailRepository;
import me.zhengjie.modules.finance.service.MaintarinDetailService;
import me.zhengjie.modules.finance.service.dto.MaintarinDetailDTO;
import me.zhengjie.modules.finance.service.dto.MaintarinDetailQueryCriteria;
import me.zhengjie.modules.finance.service.mapper.MaintarinDetailMapper;
import me.zhengjie.modules.system.repository.DictDetailRepository;
import me.zhengjie.utils.ValidationUtil;
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
public class MaintarinDetailServiceImpl implements MaintarinDetailService {

    @Autowired
    private MaintarinDetailRepository maintarinDetailRepository;

    @Autowired
    private MaintarinDetailMapper maintarinDetailMapper;

    @Autowired
    private DictDetailRepository dictDetailRepository;

    @Override
    public Object queryAll(MaintarinDetailQueryCriteria criteria, Pageable pageable){
        Page<MaintarinDetail> page = maintarinDetailRepository.findAllByDeptId(criteria.getDeptId(),pageable);
        List<MaintarinDetailDTO> maintarinDetailDTOS = new ArrayList<>();
        for (MaintarinDetail maintarinDetail : page.getContent()) {
            MaintarinDetailDTO dto = maintarinDetailMapper.toDTO(maintarinDetail, dictDetailRepository.findById(maintarinDetail.getTradType().getId()).get());
            dto.setDeptId(maintarinDetail.getDept().getId());
            dto.setDeptName(maintarinDetail.getDept().getName());
            maintarinDetailDTOS.add(dto);
        }
        return PageUtil.toPage(maintarinDetailDTOS,page.getTotalElements());
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
    public MaintarinDetailDTO create(MaintarinDetail resources) {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        if (queryExist(resources)){
            resources.setId(snowflake.nextId());
            return maintarinDetailMapper.toDto(maintarinDetailRepository.save(resources));
        }
        return new MaintarinDetailDTO();
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
}



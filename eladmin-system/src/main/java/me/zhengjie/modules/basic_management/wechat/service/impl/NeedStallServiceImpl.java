package me.zhengjie.modules.basic_management.wechat.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import me.zhengjie.modules.basic_management.wechat.domain.NeedStall;
import me.zhengjie.modules.basic_management.wechat.repository.NeedStallRepository;
import me.zhengjie.modules.basic_management.wechat.service.NeedStallService;
import me.zhengjie.modules.basic_management.wechat.service.dto.NeedStallDto;
import me.zhengjie.modules.basic_management.wechat.service.dto.NeedStallQueryCriteria;
import me.zhengjie.modules.basic_management.wechat.service.mapper.NeedStallMapper;
import me.zhengjie.modules.system.service.dto.DeptDTO;
import me.zhengjie.utils.QueryHelp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mingkun_Niu
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class NeedStallServiceImpl implements NeedStallService {

    @Autowired
    private NeedStallRepository needStallRepository;
    @Autowired
    @SuppressWarnings("all")
    private NeedStallMapper needStallMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public NeedStallDto create(NeedStall resources) {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        resources.setId(snowflake.nextId());
        NeedStall save = needStallRepository.save(resources);
        return needStallMapper.toDto(save);
    }

    @Override
    public Map queryByNeedAll(NeedStallQueryCriteria criteria) {
        Map map = new HashMap();
        List<NeedStallDto> list = needStallMapper.toDto(needStallRepository.findAll((root, criteriaquery, criteriabuilder) -> QueryHelp.getPredicate(root,criteria,criteriabuilder)));
        map.put("list",list);
        map.put("count",list.size());
        return map;
    }
}

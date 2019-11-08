package me.zhengjie.modules.basic_management.wechat.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import me.zhengjie.modules.basic_management.wechat.domain.NeedStall;
import me.zhengjie.modules.basic_management.wechat.repository.NeedStallRepository;
import me.zhengjie.modules.basic_management.wechat.service.NeedStallService;
import me.zhengjie.modules.basic_management.wechat.service.dto.NeedStallDto;
import me.zhengjie.modules.basic_management.wechat.service.mapper.NeedStallMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
}

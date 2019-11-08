package me.zhengjie.modules.basic_management.wechat.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import me.zhengjie.modules.basic_management.wechat.domain.PublicWarehouse;
import me.zhengjie.modules.basic_management.wechat.repository.PublicWarehouseRepository;
import me.zhengjie.modules.basic_management.wechat.service.PublicWarehouseService;
import me.zhengjie.modules.basic_management.wechat.service.dto.PublicWarehouseDto;
import me.zhengjie.modules.basic_management.wechat.service.mapper.PublicWarehouseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class PublicWarehouseServiceImpl implements PublicWarehouseService {
    @Autowired
    private PublicWarehouseRepository publicWarehouseRepository;
    @Autowired
    @SuppressWarnings("all")
    private PublicWarehouseMapper publicWarehouseMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PublicWarehouseDto crite(PublicWarehouse resource) {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        resource.setId(snowflake.nextId());
        PublicWarehouse save = publicWarehouseRepository.save(resource);
        return publicWarehouseMapper.toDto(save);
    }
}

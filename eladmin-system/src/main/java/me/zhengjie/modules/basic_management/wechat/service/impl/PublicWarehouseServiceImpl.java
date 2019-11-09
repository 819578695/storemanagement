package me.zhengjie.modules.basic_management.wechat.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import me.zhengjie.modules.basic_management.wechat.domain.PublicWarehouse;
import me.zhengjie.modules.basic_management.wechat.repository.PublicWarehouseRepository;
import me.zhengjie.modules.basic_management.wechat.service.PublicWarehouseService;
import me.zhengjie.modules.basic_management.wechat.service.dto.NeedStallDto;
import me.zhengjie.modules.basic_management.wechat.service.dto.PublicWarehouseDto;
import me.zhengjie.modules.basic_management.wechat.service.dto.PublicWarehouseQueryCriteria;
import me.zhengjie.modules.basic_management.wechat.service.mapper.PublicWarehouseMapper;
import me.zhengjie.utils.QueryHelp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public Map queryByPWarehouseAll(PublicWarehouseQueryCriteria criteria) {
        Map map = new HashMap();
        List<PublicWarehouseDto> list = publicWarehouseMapper.toDto(publicWarehouseRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
        map.put("list",list);
        return map;
    }
}

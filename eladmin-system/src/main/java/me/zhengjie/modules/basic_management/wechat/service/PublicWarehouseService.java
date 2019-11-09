package me.zhengjie.modules.basic_management.wechat.service;

import me.zhengjie.modules.basic_management.wechat.domain.PublicWarehouse;
import me.zhengjie.modules.basic_management.wechat.service.dto.PublicWarehouseDto;
import me.zhengjie.modules.basic_management.wechat.service.dto.PublicWarehouseQueryCriteria;

import java.util.List;
import java.util.Map;

public interface PublicWarehouseService {

    PublicWarehouseDto crite(PublicWarehouse resource);

    Map queryByPWarehouseAll(PublicWarehouseQueryCriteria criteria);
}

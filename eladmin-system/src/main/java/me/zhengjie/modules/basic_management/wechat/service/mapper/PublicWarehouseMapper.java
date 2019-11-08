package me.zhengjie.modules.basic_management.wechat.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.basic_management.wechat.domain.PublicWarehouse;
import me.zhengjie.modules.basic_management.wechat.service.dto.PublicWarehouseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PublicWarehouseMapper extends EntityMapper<PublicWarehouseDto, PublicWarehouse> {
}

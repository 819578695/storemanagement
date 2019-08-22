package me.zhengjie.modules.business.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.business.domain.ParkCost;
import me.zhengjie.modules.business.service.dto.ParkCostDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author kang
* @date 2019-08-22
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ParkCostMapper extends EntityMapper<ParkCostDTO, ParkCost> {

}
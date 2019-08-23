package me.zhengjie.modules.business.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.business.domain.ParkCost;
import me.zhengjie.modules.business.service.dto.ParkCostDTO;
import me.zhengjie.modules.system.domain.Dept;
import me.zhengjie.modules.system.service.mapper.DeptMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

/**
* @author kang
* @date 2019-08-22
*/
@Mapper(componentModel = "spring",uses = {DeptMapper.class},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ParkCostMapper extends EntityMapper<ParkCostDTO, ParkCost> {
    @Mapping(source = "deptName", target = "deptName")
    ParkCostDTO toDto(ParkCost parkCost,String deptName);

}

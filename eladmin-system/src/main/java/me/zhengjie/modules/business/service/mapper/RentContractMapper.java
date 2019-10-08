package me.zhengjie.modules.business.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.basic_management.thearchives.domain.BasicsPark;
import me.zhengjie.modules.business.domain.ParkCost;
import me.zhengjie.modules.business.domain.RentContract;
import me.zhengjie.modules.business.service.dto.ParkCostDTO;
import me.zhengjie.modules.business.service.dto.RentContractDTO;
import me.zhengjie.modules.system.domain.Dept;
import me.zhengjie.modules.system.domain.DictDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

/**
* @author kang
* @date 2019-08-28
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RentContractMapper extends EntityMapper<RentContractDTO, RentContract> {

    @Mappings({
            @Mapping(source = "rentContract.id",target = "id"),
            @Mapping(source = "rentContract.createTime",target = "createTime"),
            @Mapping(source = "dept.name",target = "deptName"),
            @Mapping(source = "dept.id",target = "deptId"),
            @Mapping(source = "payCycle.label",target = "payCycleName"),
            @Mapping(source = "payCycle.id",target = "payCycleId"),
    })
    RentContractDTO toDto(RentContract rentContract, Dept dept,DictDetail payCycle);
}

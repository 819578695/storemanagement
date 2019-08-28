package me.zhengjie.modules.business.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.basic_management.thearchives.domain.BasicsPark;
import me.zhengjie.modules.business.domain.ParkCost;
import me.zhengjie.modules.business.service.dto.ParkCostDTO;
import me.zhengjie.modules.system.domain.Dept;
import me.zhengjie.modules.system.domain.DictDetail;
import me.zhengjie.modules.system.service.mapper.DeptMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

/**
* @author kang
* @date 2019-08-22
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ParkCostMapper extends EntityMapper<ParkCostDTO, ParkCost> {
    @Mappings({
            @Mapping(source = "parkCost.id",target = "id"),
            @Mapping(source = "dept.name",target = "deptName"),
            @Mapping(source = "dept.id",target = "deptId"),
            @Mapping(source = "dictDetail.id",target = "paymentType"),
            @Mapping(source = "dictDetail.label",target = "paymentTypeName"),
            @Mapping(source = "parkCost.createTime",target = "createTime"),
            @Mapping(source = "basicsPark.id",target = "parkId"),
            @Mapping(source = "basicsPark.garden",target = "basicsParkName")

    })
    ParkCostDTO toDto(ParkCost parkCost, Dept dept, DictDetail dictDetail, BasicsPark basicsPark);

}

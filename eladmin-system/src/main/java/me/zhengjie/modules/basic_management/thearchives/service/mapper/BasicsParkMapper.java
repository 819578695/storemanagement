package me.zhengjie.modules.basic_management.thearchives.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.basic_management.thearchives.domain.BasicsPark;
import me.zhengjie.modules.basic_management.thearchives.service.dto.BasicsParkDTO;
import me.zhengjie.modules.system.domain.Dept;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

/**
* @author zlk
* @date 2019-08-26
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BasicsParkMapper extends EntityMapper<BasicsParkDTO, BasicsPark> {
    @Mappings({
            @Mapping(source = "tenantinformation.id",target = "id"),
            @Mapping(source = "dept.id",target = "deptId"),
    })
    BasicsParkDTO toDto(BasicsPark tenantinformation, Dept dept);

}
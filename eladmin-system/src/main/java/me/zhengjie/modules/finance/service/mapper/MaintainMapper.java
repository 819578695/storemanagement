package me.zhengjie.modules.finance.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.finance.domain.Maintain;
import me.zhengjie.modules.finance.service.dto.MaintainDTO;
import me.zhengjie.modules.system.domain.Dept;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

/**
* @author nmk
* @date 2019-08-29
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MaintainMapper extends EntityMapper<MaintainDTO, Maintain> {

    @Mappings({
            @Mapping(source = "maintain.id", target = "id"),
            @Mapping(source = "dept.id", target = "deptId"),
            @Mapping(source = "dept.name", target = "deptName"),
    })
    MaintainDTO toDTO (Maintain maintain , Dept dept);
}

package me.zhengjie.modules.finance.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.finance.domain.FinanceMaintain;
import me.zhengjie.modules.finance.service.dto.FinanceMaintainDTO;
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
public interface FinanceMaintainMapper extends EntityMapper<FinanceMaintainDTO, FinanceMaintain> {

    @Mappings({
            @Mapping(source = "financeMaintain.id", target = "id"),
            @Mapping(source = "dept.id", target = "deptId"),
            @Mapping(source = "dept.name", target = "deptName"),
    })
    FinanceMaintainDTO toDTO (FinanceMaintain financeMaintain , Dept dept);
}

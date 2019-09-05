package me.zhengjie.modules.finance.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.finance.domain.FundFlowing;
import me.zhengjie.modules.finance.service.dto.FundFlowingDTO;
import me.zhengjie.modules.system.domain.Dept;
import me.zhengjie.modules.system.domain.DictDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

/**
* @author nmk
* @date 2019-08-22
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FundFlowingMapper extends EntityMapper<FundFlowingDTO, FundFlowing> {

    @Mappings({
            @Mapping(source = "fundFlowing.id" , target = "id"),
            @Mapping(source = "tradType.id",target = "tradTypeId"),
            @Mapping(source = "tradType.label",target = "tradTypeLabel"),
            @Mapping(source = "tallyType.id",target = "tallyTypeId"),
            @Mapping(source = "tallyType.label",target = "tallyTypeIdLabel"),
            @Mapping(source = "typeDict.id",target = "typeId"),
            @Mapping(source = "typeDict.label",target = "typeLabel"),
            @Mapping(source = "dept.id",target = "deptId"),
            @Mapping(source = "dept.name",target = "deptName"),
    })
    FundFlowingDTO toDTO (FundFlowing fundFlowing , DictDetail tradType , DictDetail tallyType , DictDetail typeDict, Dept dept);
}

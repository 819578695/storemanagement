package me.zhengjie.modules.basic_management.thearchives.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.basic_management.thearchives.domain.BasicsPark;
import me.zhengjie.modules.basic_management.thearchives.service.dto.BasicsParkContractDTO;
import me.zhengjie.modules.business.domain.RentContract;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BasicsParkContractMapper extends EntityMapper<BasicsParkContractDTO, BasicsPark> {
    @Mappings({
            /*@Mapping(source = "basicsPark.id",target = "id"),*/
            @Mapping(source = "rentContract.dept.id",target = "deptId"),
            @Mapping(source = "rentContract.id",target = "theContractInformation"),
            @Mapping(source = "rentContract.fileName",target = "fileName"),
            @Mapping(source = "rentContract.isEnable",target = "isEnable"),
            @Mapping(source = "rentContract.contractNo",target = "contractNo"),
            @Mapping(source = "rentContract.contractName",target = "contractName"),
    })
    BasicsParkContractDTO toDto(RentContract rentContract);
}

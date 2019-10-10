package me.zhengjie.modules.basic_management.Tenantinformation.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.basic_management.Tenantinformation.domain.Tenantinformation;
import me.zhengjie.modules.basic_management.Tenantinformation.service.dto.ParticularsDTO;
import me.zhengjie.modules.business.domain.LeaseContract;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ParticularsMapper extends EntityMapper<ParticularsDTO, Tenantinformation> {
    @Mappings({
            @Mapping(source = "tenantinformation.id",target = "id"),
            @Mapping(source = "LeaseContract.id",target = "contractid"),
            @Mapping(source = "LeaseContract.createTime",target = "createTime"),
            @Mapping(source = "LeaseContract.archivesmouthsmanagement.dictDetail.id",target = "stalltype"),
            @Mapping(source = "LeaseContract.archivesmouthsmanagement.dictDetail.label",target = "stalltypeName"),
            @Mapping(source = "LeaseContract.archivesmouthsmanagement.id",target = "stallid"),
            @Mapping(source = "LeaseContract.archivesmouthsmanagement.acreage",target = "area"),
            @Mapping(source = "LeaseContract.archivesmouthsmanagement.housenumber",target = "roomnumber")
    })
    ParticularsDTO toDto(Tenantinformation tenantinformation, LeaseContract LeaseContract);
}

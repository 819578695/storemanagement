package me.zhengjie.modules.basic_management.Tenantinformation.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.basic_management.Tenantinformation.domain.Tenantinformation;
import me.zhengjie.modules.basic_management.Tenantinformation.service.dto.TenantinformationDTO;
import me.zhengjie.modules.business.domain.LeaseContract;
import me.zhengjie.modules.system.domain.Dept;
import me.zhengjie.modules.system.domain.DictDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

/**
* @author zlk
* @date 2019-08-12
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TenantinformationMapper extends EntityMapper<TenantinformationDTO, Tenantinformation> {
    @Mappings({
            @Mapping(source = "tenantinformation.id",target = "id"),
            @Mapping(source = "dictDetail.id",target = "stalltype"),
            @Mapping(source = "dictDetail.label",target = "stalltypeName"),
            @Mapping(source = "LeaseContract.id",target = "contractid"),
            @Mapping(source = "LeaseContract.fileName",target = "thecontractdetails"),
            @Mapping(source = "dept.id",target = "deptId"),
    })
    TenantinformationDTO toDto(Tenantinformation tenantinformation, Dept dept, DictDetail dictDetail, LeaseContract LeaseContract);

}
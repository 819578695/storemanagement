package me.zhengjie.modules.basic_management.Tenantinformation.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.domain.Archivesmouthsmanagement;
import me.zhengjie.modules.basic_management.Tenantinformation.domain.Tenantinformation;
import me.zhengjie.modules.basic_management.Tenantinformation.service.dto.ParticularsDTO;
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
            /*@Mapping(source = "LeaseContract.archivesmouthsmanagement.dictDetail.id",target = "stalltype"),
            @Mapping(source = "LeaseContract.archivesmouthsmanagement.acreage",target = "area"),*/
            @Mapping(source = "LeaseContract.id",target = "contractid"),
            @Mapping(source = "LeaseContract.fileName",target = "thecontractdetails"),
            /*@Mapping(source = "LeaseContract.archivesmouthsmanagement.id",target = "stallid"),
            @Mapping(source = "LeaseContract.archivesmouthsmanagement.housenumber",target = "roomnumber"),*/
            @Mapping(source = "dept.id",target = "deptId"),
            @Mapping(source = "dept.name",target = "deptName")
    })
    TenantinformationDTO toDto(Tenantinformation tenantinformation, Dept dept, LeaseContract LeaseContract);
}

package me.zhengjie.modules.business.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.domain.Archivesmouthsmanagement;
import me.zhengjie.modules.basic_management.Tenantinformation.domain.Tenantinformation;
import me.zhengjie.modules.basic_management.thearchives.domain.BasicsPark;
import me.zhengjie.modules.business.domain.LeaseContract;
import me.zhengjie.modules.business.domain.ParkPevenue;
import me.zhengjie.modules.business.domain.ReceiptPaymentAccount;
import me.zhengjie.modules.business.service.dto.LeaseContractDTO;
import me.zhengjie.modules.business.service.dto.ParkPevenueDTO;
import me.zhengjie.modules.system.domain.Dept;
import me.zhengjie.modules.system.domain.DictDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

/**
* @author nmk
* @date 2019-08-29
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LeaseContractMapper extends EntityMapper<LeaseContractDTO, LeaseContract> {

    @Mappings({
            @Mapping(source = "leaseContract.id",target = "id"),
            @Mapping(source = "archivesmouthsmanagement.id",target = "stallId"),
            @Mapping(source = "archivesmouthsmanagement.housenumber",target = "houseNumber"),
            @Mapping(source = "tenantinformation.id",target = "tenementId"),
            @Mapping(source = "tenantinformation.linkman",target = "tenementName"),
            @Mapping(source = "dept.name",target = "deptName"),
            @Mapping(source = "dept.id",target = "deptId"),
    })
    LeaseContractDTO toDto(LeaseContract leaseContract, Archivesmouthsmanagement archivesmouthsmanagement, Dept dept, Tenantinformation tenantinformation);


}

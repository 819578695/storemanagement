package me.zhengjie.modules.business.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.domain.Archivesmouthsmanagement;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.service.mapper.ArchivesmouthsmanagementMapper;
import me.zhengjie.modules.business.domain.ParkPevenue;
import me.zhengjie.modules.business.service.dto.ParkPevenueDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

/**
* @author kang
* @date 2019-08-23
*/
@Mapper(componentModel = "spring",uses = {ArchivesmouthsmanagementMapper.class},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ParkPevenueMapper extends EntityMapper<ParkPevenueDTO, ParkPevenue> {


    @Mappings({
            @Mapping(source = "parkPevenue.id",target = "id"),
            @Mapping(source = "archivesmouthsmanagement.id",target = "archivesMouthsId"),
            @Mapping(source = "archivesmouthsmanagement.housenumber",target = "houseNumber")
    })
    ParkPevenueDTO toDto(ParkPevenue parkPevenue, Archivesmouthsmanagement archivesmouthsmanagement);
}

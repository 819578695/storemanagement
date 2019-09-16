package me.zhengjie.modules.basic_management.Archivesmouthsmanagement.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.domain.Archivesmouthsmanagement;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.service.dto.ArchiveDto;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.service.dto.ArchivesmouthsmanagementDTO;
import me.zhengjie.modules.basic_management.city.domain.City;
import me.zhengjie.modules.system.domain.Dept;
import me.zhengjie.modules.system.domain.DictDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ArchivesmouthsmanagementMapper extends EntityMapper<ArchivesmouthsmanagementDTO, Archivesmouthsmanagement> {
    @Mappings({
            @Mapping(source = "archivesmouthsmanagement.id",target = "id"),
            @Mapping(source = "dictDetail.id",target = "stalltype"),
            @Mapping(source = "dictDetail.label",target = "stalltypeName"),
            @Mapping(source = "dept.id",target = "deptId"),
    })
    ArchivesmouthsmanagementDTO toDto(Archivesmouthsmanagement archivesmouthsmanagement, Dept dept, DictDetail dictDetail);
}

package me.zhengjie.modules.basic_management.Archivesmouthsmanagement.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.domain.Archivesmouthsmanagement;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.service.dto.ArchiveDto;
import me.zhengjie.modules.basic_management.city.domain.City;
import me.zhengjie.modules.system.domain.DictDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ArchiveMapper extends EntityMapper<ArchiveDto, Archivesmouthsmanagement> {
    @Mappings({
            @Mapping(source = "archivesmouthsmanagement.id",target = "id"),
            @Mapping(source = "dictDetail.id",target = "stalltype"),
            @Mapping(source = "dictDetail.label",target = "stalltypeName"),
            @Mapping(source = "city.areaId",target = "areaId"),
            @Mapping(source = "city.areaName",target = "areaName"),
    })
    ArchiveDto toDtos(Archivesmouthsmanagement archivesmouthsmanagement, DictDetail dictDetail, City city);
}

package me.zhengjie.modules.business.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.business.domain.ProcurementInformation;
import me.zhengjie.modules.business.service.dto.ProcurementInformationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author kang
* @date 2019-08-20
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProcurementInformationMapper extends EntityMapper<ProcurementInformationDTO, ProcurementInformation> {

}
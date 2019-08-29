package me.zhengjie.modules.business.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.business.domain.LeaseContract;
import me.zhengjie.modules.business.service.dto.LeaseContractDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author nmk
* @date 2019-08-29
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LeaseContractMapper extends EntityMapper<LeaseContractDTO, LeaseContract> {

}
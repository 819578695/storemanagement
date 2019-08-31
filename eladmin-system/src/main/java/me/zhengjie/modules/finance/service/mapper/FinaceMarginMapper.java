package me.zhengjie.modules.finance.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.finance.domain.FinaceMargin;
import me.zhengjie.modules.finance.service.dto.FinaceMarginDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author nmk
* @date 2019-08-29
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FinaceMarginMapper extends EntityMapper<FinaceMarginDTO, FinaceMargin> {

}

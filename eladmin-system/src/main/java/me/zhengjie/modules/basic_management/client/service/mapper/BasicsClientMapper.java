package me.zhengjie.modules.basic_management.client.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.basic_management.client.domain.BasicsClient;
import me.zhengjie.modules.basic_management.client.service.dto.BasicsClientDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author nmkzlk
* @date 2019-09-02
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BasicsClientMapper extends EntityMapper<BasicsClientDTO, BasicsClient> {

}
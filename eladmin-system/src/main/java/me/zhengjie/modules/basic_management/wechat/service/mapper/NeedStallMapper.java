package me.zhengjie.modules.basic_management.wechat.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.basic_management.wechat.domain.NeedStall;
import me.zhengjie.modules.basic_management.wechat.service.dto.NeedStallDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NeedStallMapper extends EntityMapper<NeedStallDto, NeedStall> {

}

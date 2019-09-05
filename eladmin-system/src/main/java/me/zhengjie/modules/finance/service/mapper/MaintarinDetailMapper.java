package me.zhengjie.modules.finance.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.finance.domain.MaintarinDetail;
import me.zhengjie.modules.finance.service.dto.MaintarinDetailDTO;
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
public interface MaintarinDetailMapper extends EntityMapper<MaintarinDetailDTO, MaintarinDetail> {

    @Mappings({
            @Mapping(source = "maintarinDetail.id",target = "id"),
            @Mapping(source = "tradType.id",target = "tradTypeId"),
            @Mapping(source = "tradType.label",target = "tradTypeLabel")
    })
    MaintarinDetailDTO toDTO (MaintarinDetail maintarinDetail , DictDetail tradType);
}

package me.zhengjie.modules.finance.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.finance.domain.FinanceMaintarinDetail;
import me.zhengjie.modules.finance.domain.JournalAccountOfCapital;
import me.zhengjie.modules.finance.service.dto.FinanceMaintarinDetailDTO;
import me.zhengjie.modules.finance.service.dto.JournalAccountOfCapitalDTO;
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
public interface FinanceMaintarinDetailMapper extends EntityMapper<FinanceMaintarinDetailDTO, FinanceMaintarinDetail> {

//    @Mappings({
//            @Mapping(source = "financeMaintarinDetail.id",target = "id"),
//            @Mapping(source = "tradType.id",target = "tradtypeId"),
//            @Mapping(source = "tradType.label",target = "tradTypeLabel")
//    })
//    FinanceMaintarinDetailDTO toDTO (FinanceMaintarinDetail financeMaintarinDetail , DictDetail tradType);
}

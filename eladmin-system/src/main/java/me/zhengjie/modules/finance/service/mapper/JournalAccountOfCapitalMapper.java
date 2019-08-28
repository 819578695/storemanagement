package me.zhengjie.modules.finance.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.finance.domain.JournalAccountOfCapital;
import me.zhengjie.modules.finance.service.dto.JournalAccountOfCapitalDTO;
import me.zhengjie.modules.system.domain.DictDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

/**
* @author nmk
* @date 2019-08-22
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JournalAccountOfCapitalMapper extends EntityMapper<JournalAccountOfCapitalDTO, JournalAccountOfCapital> {

    @Mappings({
            @Mapping(source = "journalAccountOfCapital.id" , target = "id"),
            @Mapping(source = "dictDetail.id",target = "tradTypeId"),
            @Mapping(source = "dictDetail.label",target = "dictDetailLabel"),
            @Mapping(source = "tallyType.id",target = "tallyTypeId"),
            @Mapping(source = "tallyType.label",target = "tallyTypeIdLabel"),
            @Mapping(source = "typeDict.id",target = "typeId"),
            @Mapping(source = "typeDict.label",target = "typeLabel")
    })
    JournalAccountOfCapitalDTO toDTO (JournalAccountOfCapital journalAccountOfCapital , DictDetail dictDetail , DictDetail tallyType , DictDetail typeDict);
}

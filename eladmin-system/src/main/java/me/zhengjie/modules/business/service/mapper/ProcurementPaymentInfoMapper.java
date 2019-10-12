package me.zhengjie.modules.business.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.business.domain.ProcurementPaymentInfo;
import me.zhengjie.modules.business.domain.ReceiptPaymentAccount;
import me.zhengjie.modules.business.service.dto.ProcurementPaymentInfoDTO;
import me.zhengjie.modules.system.domain.DictDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

/**
* @author kang
* @date 2019-10-09
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProcurementPaymentInfoMapper extends EntityMapper<ProcurementPaymentInfoDTO, ProcurementPaymentInfo> {


    @Mappings({
            @Mapping(source = "procurementInformation.id",target = "id"),
            @Mapping(source = "receiptPaymentAccount.name",target = "receiptPaymentAccountName"),
            @Mapping(source = "receiptPaymentAccount.id",target = "receiptPaymentAccountId"),
            @Mapping(source = "dictDetail.id",target = "paymentTypeId"),
            @Mapping(source = "dictDetail.label",target = "paymentTypeName"),
    })
    ProcurementPaymentInfoDTO toDto(ProcurementPaymentInfo procurementInformation, ReceiptPaymentAccount receiptPaymentAccount, DictDetail dictDetail);

}

package me.zhengjie.modules.business.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.business.domain.ProcurementInformation;
import me.zhengjie.modules.business.domain.ReceiptPaymentAccount;
import me.zhengjie.modules.business.service.dto.ProcurementInformationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

/**
* @author kang
* @date 2019-08-20
*/
@Mapper(componentModel = "spring",uses = {ReceiptPaymentAccount.class},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProcurementInformationMapper extends EntityMapper<ProcurementInformationDTO, ProcurementInformation> {



  @Mappings({
          @Mapping(source = "procurementInformation.id",target = "id"),
          @Mapping(source = "receiptPaymentAccount.id",target = "receiptPaymentAccountId"),
          @Mapping(source = "receiptPaymentAccount.name",target = "receiptPaymentAccountName")
  })
    ProcurementInformationDTO toDto(ProcurementInformation procurementInformation, ReceiptPaymentAccount receiptPaymentAccount);
}
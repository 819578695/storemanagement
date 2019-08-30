package me.zhengjie.modules.business.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.business.domain.ProcurementInformation;
import me.zhengjie.modules.business.domain.ReceiptPaymentAccount;
import me.zhengjie.modules.business.service.dto.ProcurementInformationDTO;
import me.zhengjie.modules.system.domain.Dept;
import me.zhengjie.modules.system.domain.DictDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

/**
* @author kang
* @date 2019-08-20
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProcurementInformationMapper extends EntityMapper<ProcurementInformationDTO, ProcurementInformation> {



  @Mappings({
          @Mapping(source = "procurementInformation.id",target = "id"),
          @Mapping(source = "receiptPaymentAccount.id",target = "receiptPaymentAccountId"),
          @Mapping(source = "receiptPaymentAccount.name",target = "receiptPaymentAccountName"),
          @Mapping(source = "dictDetail.id",target = "paymentType"),
          @Mapping(source = "dictDetail.label",target = "paymentTypeName"),
          @Mapping(source = "dept.name",target = "deptName"),
          @Mapping(source = "dept.id",target = "deptId"),
  })
    ProcurementInformationDTO toDto(ProcurementInformation procurementInformation, ReceiptPaymentAccount receiptPaymentAccount, DictDetail dictDetail, Dept dept);
}

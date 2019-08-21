package me.zhengjie.modules.business.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.business.domain.ReceiptPaymentAccount;
import me.zhengjie.modules.business.service.dto.ReceiptPaymentAccountDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author kang
* @date 2019-08-21
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReceiptPaymentAccountMapper extends EntityMapper<ReceiptPaymentAccountDTO, ReceiptPaymentAccount> {

}
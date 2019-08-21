package me.zhengjie.modules.business.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.business.domain.ReceiptPaymentAccount;
import me.zhengjie.modules.business.service.ReceiptPaymentAccountService;
import me.zhengjie.modules.business.service.dto.ReceiptPaymentAccountQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

/**
* @author kang
* @date 2019-08-21
*/
@Api(tags = "ReceiptPaymentAccount管理")
@RestController
@RequestMapping("api")
public class ReceiptPaymentAccountController {

    @Autowired
    private ReceiptPaymentAccountService receiptPaymentAccountService;

    @Log("查询ReceiptPaymentAccount")
    @ApiOperation(value = "查询ReceiptPaymentAccount")
    @GetMapping(value = "/receiptPaymentAccount")
    @PreAuthorize("hasAnyRole('ADMIN','RECEIPTPAYMENTACCOUNT_ALL','RECEIPTPAYMENTACCOUNT_SELECT')")
    public ResponseEntity getReceiptPaymentAccounts(ReceiptPaymentAccountQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(receiptPaymentAccountService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增ReceiptPaymentAccount")
    @ApiOperation(value = "新增ReceiptPaymentAccount")
    @PostMapping(value = "/receiptPaymentAccount")
    @PreAuthorize("hasAnyRole('ADMIN','RECEIPTPAYMENTACCOUNT_ALL','RECEIPTPAYMENTACCOUNT_CREATE')")
    public ResponseEntity create(@Validated @RequestBody ReceiptPaymentAccount resources){
        return new ResponseEntity(receiptPaymentAccountService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改ReceiptPaymentAccount")
    @ApiOperation(value = "修改ReceiptPaymentAccount")
    @PutMapping(value = "/receiptPaymentAccount")
    @PreAuthorize("hasAnyRole('ADMIN','RECEIPTPAYMENTACCOUNT_ALL','RECEIPTPAYMENTACCOUNT_EDIT')")
    public ResponseEntity update(@Validated @RequestBody ReceiptPaymentAccount resources){
        receiptPaymentAccountService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除ReceiptPaymentAccount")
    @ApiOperation(value = "删除ReceiptPaymentAccount")
    @DeleteMapping(value = "/receiptPaymentAccount/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','RECEIPTPAYMENTACCOUNT_ALL','RECEIPTPAYMENTACCOUNT_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        receiptPaymentAccountService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
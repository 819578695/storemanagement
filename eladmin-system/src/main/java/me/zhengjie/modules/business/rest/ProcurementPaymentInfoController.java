package me.zhengjie.modules.business.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.business.domain.ProcurementPaymentInfo;
import me.zhengjie.modules.business.service.ProcurementPaymentInfoService;
import me.zhengjie.modules.business.service.dto.ProcurementPaymentInfoQueryCriteria;
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
* @date 2019-10-09
*/
@Api(tags = "ProcurementPaymentInfo管理")
@RestController
@RequestMapping("api")
public class ProcurementPaymentInfoController {

    @Autowired
    private ProcurementPaymentInfoService procurementPaymentInfoService;

    @Log("查询ProcurementPaymentInfo")
    @ApiOperation(value = "查询ProcurementPaymentInfo")
    @GetMapping(value = "/procurementPaymentInfo")
    @PreAuthorize("hasAnyRole('ADMIN','PROCUREMENTPAYMENTINFO_ALL','PROCUREMENTPAYMENTINFO_SELECT')")
    public ResponseEntity getProcurementPaymentInfos(ProcurementPaymentInfoQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(procurementPaymentInfoService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("根据采购id查询ProcurementPaymentInfo")
    @ApiOperation(value = "根据采购id查询ProcurementPaymentInfo")
    @GetMapping(value = "/procurementPaymentInfoById/{procurementInformationId}")
    @PreAuthorize("hasAnyRole('ADMIN','PROCUREMENTPAYMENTINFO_ALL','PROCUREMENTPAYMENTINFO_SELECT')")
    public ResponseEntity getProcurementPaymentInfoById(@PathVariable Long procurementInformationId){
        return new ResponseEntity(procurementPaymentInfoService.findByProcurement(procurementInformationId),HttpStatus.OK);
    }


    @Log("新增ProcurementPaymentInfo")
    @ApiOperation(value = "新增ProcurementPaymentInfo")
    @PostMapping(value = "/procurementPaymentInfo")
    @PreAuthorize("hasAnyRole('ADMIN','PROCUREMENTPAYMENTINFO_ALL','PROCUREMENTPAYMENTINFO_CREATE')")
    public ResponseEntity create(@Validated @RequestBody ProcurementPaymentInfo resources){
        return new ResponseEntity(procurementPaymentInfoService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改ProcurementPaymentInfo")
    @ApiOperation(value = "修改ProcurementPaymentInfo")
    @PutMapping(value = "/procurementPaymentInfo")
    @PreAuthorize("hasAnyRole('ADMIN','PROCUREMENTPAYMENTINFO_ALL','PROCUREMENTPAYMENTINFO_EDIT')")
    public ResponseEntity update(@Validated @RequestBody ProcurementPaymentInfo resources){
        procurementPaymentInfoService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除ProcurementPaymentInfo")
    @ApiOperation(value = "删除ProcurementPaymentInfo")
    @DeleteMapping(value = "/procurementPaymentInfo/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PROCUREMENTPAYMENTINFO_ALL','PROCUREMENTPAYMENTINFO_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        procurementPaymentInfoService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}

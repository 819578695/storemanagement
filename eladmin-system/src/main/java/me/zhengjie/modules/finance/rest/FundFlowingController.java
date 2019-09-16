package me.zhengjie.modules.finance.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.business.service.dto.ProcurementInformationQueryCriteria;
import me.zhengjie.modules.finance.domain.FundFlowing;
import me.zhengjie.modules.finance.service.FundFlowingService;
import me.zhengjie.modules.finance.service.dto.FundFlowingQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

/**
* @author nmk
* @date 2019-08-22
*/
@Api(tags = "FundFlowing管理")
@RestController
@RequestMapping("api")
public class FundFlowingController {

    @Autowired
    private FundFlowingService fundFlowingService;

    @Log("查询所有FundFlowing")
    @ApiOperation(value = "查询所有FundFlowing")
    @GetMapping(value = "/fundFlowingAll")
    @PreAuthorize("hasAnyRole('ADMIN','JOURNALACCOUNTOFCAPITAL_ALL','JOURNALACCOUNTOFCAPITAL_SELECT')")
    public ResponseEntity fundFlowingAll(FundFlowingQueryCriteria criteria){
        return new ResponseEntity(fundFlowingService.queryExportAll(criteria),HttpStatus.OK);
    }

    @Log("查询FundFlowing")
    @ApiOperation(value = "查询FundFlowing")
    @GetMapping(value = "/fundFlowing")
    @PreAuthorize("hasAnyRole('ADMIN','JOURNALACCOUNTOFCAPITAL_ALL','JOURNALACCOUNTOFCAPITAL_SELECT')")
    public ResponseEntity getJournalAccountOfCapitals(FundFlowingQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(fundFlowingService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增FundFlowing")
    @ApiOperation(value = "新增FundFlowing")
    @PostMapping(value = "/fundFlowing")
    @PreAuthorize("hasAnyRole('ADMIN','JOURNALACCOUNTOFCAPITAL_ALL','JOURNALACCOUNTOFCAPITAL_CREATE')")
    public ResponseEntity create(@Validated @RequestBody FundFlowing resources){
        return new ResponseEntity(fundFlowingService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改FundFlowing")
    @ApiOperation(value = "修改FundFlowing")
    @PutMapping(value = "/fundFlowing")
    @PreAuthorize("hasAnyRole('ADMIN','JOURNALACCOUNTOFCAPITAL_ALL','JOURNALACCOUNTOFCAPITAL_EDIT')")
    public ResponseEntity update(@Validated @RequestBody FundFlowing resources){
        fundFlowingService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除FundFlowing")
    @ApiOperation(value = "删除FundFlowing")
    @DeleteMapping(value = "/fundFlowing/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','JOURNALACCOUNTOFCAPITAL_ALL','JOURNALACCOUNTOFCAPITAL_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        fundFlowingService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}

package me.zhengjie.modules.business.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.business.domain.LeaseContract;
import me.zhengjie.modules.business.service.LeaseContractService;
import me.zhengjie.modules.business.service.dto.LeaseContractQueryCriteria;
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
* @date 2019-08-29
*/
@Api(tags = "LeaseContract管理")
@RestController
@RequestMapping("api")
public class LeaseContractController {

    @Autowired
    private LeaseContractService leaseContractService;

    @Log("查询LeaseContract")
    @ApiOperation(value = "查询LeaseContract")
    @GetMapping(value = "/leaseContract")
    @PreAuthorize("hasAnyRole('ADMIN','LEASECONTRACT_ALL','LEASECONTRACT_SELECT')")
    public ResponseEntity getLeaseContracts(LeaseContractQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(leaseContractService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增LeaseContract")
    @ApiOperation(value = "新增LeaseContract")
    @PostMapping(value = "/leaseContract")
    @PreAuthorize("hasAnyRole('ADMIN','LEASECONTRACT_ALL','LEASECONTRACT_CREATE')")
    public ResponseEntity create(@Validated @RequestBody LeaseContract resources){
        return new ResponseEntity(leaseContractService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改LeaseContract")
    @ApiOperation(value = "修改LeaseContract")
    @PutMapping(value = "/leaseContract")
    @PreAuthorize("hasAnyRole('ADMIN','LEASECONTRACT_ALL','LEASECONTRACT_EDIT')")
    public ResponseEntity update(@Validated @RequestBody LeaseContract resources){
        leaseContractService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除LeaseContract")
    @ApiOperation(value = "删除LeaseContract")
    @DeleteMapping(value = "/leaseContract/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','LEASECONTRACT_ALL','LEASECONTRACT_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        leaseContractService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
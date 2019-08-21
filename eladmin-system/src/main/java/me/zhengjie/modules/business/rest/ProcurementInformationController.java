package me.zhengjie.modules.business.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.business.domain.ProcurementInformation;
import me.zhengjie.modules.business.service.ProcurementInformationService;
import me.zhengjie.modules.business.service.dto.ProcurementInformationQueryCriteria;
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
* @date 2019-08-20
*/
@Api(tags = "ProcurementInformation管理")
@RestController
@RequestMapping("api")
public class ProcurementInformationController {

    @Autowired
    private ProcurementInformationService procurementInformationService;

    @Log("查询ProcurementInformation")
    @ApiOperation(value = "查询ProcurementInformation")
    @GetMapping(value = "/procurementInformation")
    @PreAuthorize("hasAnyRole('ADMIN','PROCUREMENTINFORMATION_ALL','PROCUREMENTINFORMATION_SELECT')")
    public ResponseEntity getProcurementInformations(ProcurementInformationQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(procurementInformationService.queryAll(criteria,pageable),HttpStatus.OK);
    }
    @Log("查询所有ProcurementInformation")
    @ApiOperation(value = "查询所有ProcurementInformation")
    @GetMapping(value = "/procurementInformationAll")
    @PreAuthorize("hasAnyRole('ADMIN','PROCUREMENTINFORMATION_ALL','PROCUREMENTINFORMATION_SELECT')")
    public ResponseEntity procurementInformationaAll(ProcurementInformationQueryCriteria criteria){
        return new ResponseEntity(procurementInformationService.queryAll(criteria),HttpStatus.OK);
    }

    @Log("新增ProcurementInformation")
    @ApiOperation(value = "新增ProcurementInformation")
    @PostMapping(value = "/procurementInformation")
    @PreAuthorize("hasAnyRole('ADMIN','PROCUREMENTINFORMATION_ALL','PROCUREMENTINFORMATION_CREATE')")
    public ResponseEntity create(@Validated @RequestBody ProcurementInformation resources){
        return new ResponseEntity(procurementInformationService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改ProcurementInformation")
    @ApiOperation(value = "修改ProcurementInformation")
    @PutMapping(value = "/procurementInformation")
    @PreAuthorize("hasAnyRole('ADMIN','PROCUREMENTINFORMATION_ALL','PROCUREMENTINFORMATION_EDIT')")
    public ResponseEntity update(@Validated @RequestBody ProcurementInformation resources){
        procurementInformationService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除ProcurementInformation")
    @ApiOperation(value = "删除ProcurementInformation")
    @DeleteMapping(value = "/procurementInformation/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PROCUREMENTINFORMATION_ALL','PROCUREMENTINFORMATION_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        procurementInformationService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
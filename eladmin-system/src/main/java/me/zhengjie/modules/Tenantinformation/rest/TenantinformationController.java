package me.zhengjie.modules.Tenantinformation.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.Tenantinformation.domain.Tenantinformation;
import me.zhengjie.modules.Tenantinformation.service.TenantinformationService;
import me.zhengjie.modules.Tenantinformation.service.dto.TenantinformationQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

/**
* @author zlk
* @date 2019-08-12
*/
@Api(tags = "Tenantinformation管理")
@RestController
@RequestMapping("api")
public class TenantinformationController {

    @Autowired
    private TenantinformationService tenantinformationService;

    @Log("查询Tenantinformation")
    @ApiOperation(value = "查询Tenantinformation")
    @GetMapping(value = "/tenantinformation")
    @PreAuthorize("hasAnyRole('ADMIN','TENANTINFORMATION_ALL','TENANTINFORMATION_SELECT')")
    public ResponseEntity getTenantinformations(TenantinformationQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(tenantinformationService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增Tenantinformation")
    @ApiOperation(value = "新增Tenantinformation")
    @PostMapping(value = "/tenantinformation")
    @PreAuthorize("hasAnyRole('ADMIN','TENANTINFORMATION_ALL','TENANTINFORMATION_CREATE')")
    public ResponseEntity create(@Validated @RequestBody Tenantinformation resources){
        return new ResponseEntity(tenantinformationService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改Tenantinformation")
    @ApiOperation(value = "修改Tenantinformation")
    @PutMapping(value = "/tenantinformation")
    @PreAuthorize("hasAnyRole('ADMIN','TENANTINFORMATION_ALL','TENANTINFORMATION_EDIT')")
    public ResponseEntity update(@Validated @RequestBody Tenantinformation resources){
        tenantinformationService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除Tenantinformation")
    @ApiOperation(value = "删除Tenantinformation")
    @DeleteMapping(value = "/tenantinformation/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','TENANTINFORMATION_ALL','TENANTINFORMATION_DELETE')")
    public ResponseEntity delete(@PathVariable Integer id){
        tenantinformationService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
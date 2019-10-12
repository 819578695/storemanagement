package me.zhengjie.modules.basic_management.Tenantinformation.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.basic_management.Tenantinformation.domain.Tenantinformation;
import me.zhengjie.modules.basic_management.Tenantinformation.repository.TenantinformationRepository;
import me.zhengjie.modules.basic_management.Tenantinformation.service.TenantinformationService;
import me.zhengjie.modules.basic_management.Tenantinformation.service.dto.ParticularsDTO;
import me.zhengjie.modules.basic_management.Tenantinformation.service.dto.TenantinformationQueryCriteria;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

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

    @Log("查询档口")
    @ApiOperation(value = "查询当前租户档口")
    @GetMapping(value = "/particulars")
    /*@PreAuthorize("hasAnyRole('ADMIN','TENANTINFORMATION_ALL','TENANTINFORMATION_SELECT')")*/
    public List<ParticularsDTO> getParticulars(Long id){
        List<ParticularsDTO> particularsDTOS = tenantinformationService.queryParticulars(id);
        return particularsDTOS;
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
    @Log("查询TenantinformationByDeptId")
    @ApiOperation(value = "查询TenantinformationByDeptId")
    @GetMapping(value = "/tenantinformationByDeptId/{deptId}")
    @PreAuthorize("hasAnyRole('ADMIN','TENANTINFORMATION_ALL','TENANTINFORMATION_DELETE')")
    public ResponseEntity tenantinformationByDeptId(@PathVariable Long deptId){
        return new ResponseEntity(tenantinformationService.findByDeptId(deptId),HttpStatus.OK);
    }

    @Log("查询TenantinformationByArchivesmouthsmanagementId")
    @ApiOperation(value = "查询TenantinformationArchivesmouthsmanagementId")
    @GetMapping(value = "/tenantinformationByArchivesmouthsmanagementId/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','TENANTINFORMATION_ALL','TENANTINFORMATION_SELECT')")
    public ResponseEntity tenantinformationByArchivesmouthsmanagementId(@PathVariable Long id){
        return new ResponseEntity(tenantinformationService.findByArchivesmouthsmanagementId(id),HttpStatus.OK);
    }

    @Log("删除Tenantinformation")
    @ApiOperation(value = "删除Tenantinformation")
    @DeleteMapping(value = "/tenantinformation/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','TENANTINFORMATION_ALL','TENANTINFORMATION_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        tenantinformationService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}

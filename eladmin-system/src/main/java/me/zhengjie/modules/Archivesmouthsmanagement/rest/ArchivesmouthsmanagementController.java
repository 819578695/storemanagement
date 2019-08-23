package me.zhengjie.modules.Archivesmouthsmanagement.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.Archivesmouthsmanagement.domain.Archivesmouthsmanagement;
import me.zhengjie.modules.Archivesmouthsmanagement.service.ArchivesmouthsmanagementService;
import me.zhengjie.modules.Archivesmouthsmanagement.service.dto.ArchivesmouthsmanagementQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

@Api(tags = "Tenantinformation管理")
@RestController
@RequestMapping("api")
public class ArchivesmouthsmanagementController {

    @Autowired
    private ArchivesmouthsmanagementService archivesmouthsmanagementService;

    @Log("查询Archivesmouthsmanagement")
    @ApiOperation(value = "查询Archivesmouthsmanagement")
    @GetMapping("/archivesmouthsmanagement")
    @PreAuthorize("hasAnyRole('ADMIN','ARCHIVESMOUTHSMANAGEMENT_ALL','ARCHIVESMOUTHSMANAGEMENT_SELECT')")
    public ResponseEntity getArchivesmouthsmanagement(ArchivesmouthsmanagementQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(archivesmouthsmanagementService.queryAll(criteria,pageable), HttpStatus.OK);
    }

    @Log("新增Archivesmouthsmanagement")
    @ApiOperation(value = "新增Archivesmouthsmanagement")
    @PostMapping(value = "/archivesmouthsmanagement")
    @PreAuthorize("hasAnyRole('ADMIN','ARCHIVESMOUTHSMANAGEMENT_ALL','ARCHIVESMOUTHSMANAGEMENT_CREATE')")
    public ResponseEntity create(@Validated @RequestBody Archivesmouthsmanagement resources){
        return new ResponseEntity(archivesmouthsmanagementService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改Archivesmouthsmanagement")
    @ApiOperation(value = "修改Archivesmouthsmanagement")
    @PutMapping(value = "/archivesmouthsmanagement")
    @PreAuthorize("hasAnyRole('ADMIN','ARCHIVESMOUTHSMANAGEMENT_ALL','ARCHIVESMOUTHSMANAGEMENT_EDIT')")
    public ResponseEntity update(@Validated @RequestBody Archivesmouthsmanagement resources){
        archivesmouthsmanagementService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除Archivesmouthsmanagement")
    @ApiOperation(value = "删除Archivesmouthsmanagement")
    @DeleteMapping(value = "/archivesmouthsmanagement/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ARCHIVESMOUTHSMANAGEMENT_ALL','ARCHIVESMOUTHSMANAGEMENT_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        archivesmouthsmanagementService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}

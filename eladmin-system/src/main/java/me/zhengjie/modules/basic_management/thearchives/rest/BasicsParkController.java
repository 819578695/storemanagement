package me.zhengjie.modules.basic_management.thearchives.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.basic_management.thearchives.domain.BasicsPark;
import me.zhengjie.modules.basic_management.thearchives.service.BasicsParkService;
import me.zhengjie.modules.basic_management.thearchives.service.dto.BasicsParkQueryCriteria;
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
* @date 2019-08-26
*/
@Api(tags = "BasicsPark管理")
@RestController
@RequestMapping("api")
public class BasicsParkController {

    @Autowired
    private BasicsParkService basicsParkService;

    @Log("查询BasicsPark")
    @ApiOperation(value = "查询BasicsPark")
    @GetMapping(value = "/basicsPark")
    @PreAuthorize("hasAnyRole('ADMIN','BASICSPARK_ALL','BASICSPARK_SELECT')")
    public ResponseEntity getBasicsParks(BasicsParkQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(basicsParkService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增BasicsPark")
    @ApiOperation(value = "新增BasicsPark")
    @PostMapping(value = "/basicsPark")
    @PreAuthorize("hasAnyRole('ADMIN','BASICSPARK_ALL','BASICSPARK_CREATE')")
    public ResponseEntity create(@Validated @RequestBody BasicsPark resources){
        return new ResponseEntity(basicsParkService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改BasicsPark")
    @ApiOperation(value = "修改BasicsPark")
    @PutMapping(value = "/basicsPark")
    @PreAuthorize("hasAnyRole('ADMIN','BASICSPARK_ALL','BASICSPARK_EDIT')")
    public ResponseEntity update(@Validated @RequestBody BasicsPark resources){
        basicsParkService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除BasicsPark")
    @ApiOperation(value = "删除BasicsPark")
    @DeleteMapping(value = "/basicsPark/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','BASICSPARK_ALL','BASICSPARK_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        basicsParkService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
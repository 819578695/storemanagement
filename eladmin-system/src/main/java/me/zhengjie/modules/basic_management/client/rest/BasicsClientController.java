package me.zhengjie.modules.basic_management.client.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.basic_management.client.domain.BasicsClient;
import me.zhengjie.modules.basic_management.client.service.BasicsClientService;
import me.zhengjie.modules.basic_management.client.service.dto.BasicsClientQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

/**
* @author nmkzlk
* @date 2019-09-02
*/
@Api(tags = "BasicsClient管理")
@RestController
@RequestMapping("api")
public class BasicsClientController {

    @Autowired
    private BasicsClientService basicsClientService;

    @Log("查询BasicsClient")
    @ApiOperation(value = "查询BasicsClient")
    @GetMapping(value = "/basicsClient")
    @PreAuthorize("hasAnyRole('ADMIN','BASICSCLIENT_ALL','BASICSCLIENT_SELECT')")
    public ResponseEntity getBasicsClients(BasicsClientQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(basicsClientService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增BasicsClient")
    @ApiOperation(value = "新增BasicsClient")
    @PostMapping(value = "/basicsClient")
    @PreAuthorize("hasAnyRole('ADMIN','BASICSCLIENT_ALL','BASICSCLIENT_CREATE')")
    public ResponseEntity create(@Validated @RequestBody BasicsClient resources){
        return new ResponseEntity(basicsClientService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改BasicsClient")
    @ApiOperation(value = "修改BasicsClient")
    @PutMapping(value = "/basicsClient")
    @PreAuthorize("hasAnyRole('ADMIN','BASICSCLIENT_ALL','BASICSCLIENT_EDIT')")
    public ResponseEntity update(@Validated @RequestBody BasicsClient resources){
        basicsClientService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除BasicsClient")
    @ApiOperation(value = "删除BasicsClient")
    @DeleteMapping(value = "/basicsClient/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','BASICSCLIENT_ALL','BASICSCLIENT_DELETE')")
    public ResponseEntity delete(@PathVariable Integer id){
        basicsClientService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
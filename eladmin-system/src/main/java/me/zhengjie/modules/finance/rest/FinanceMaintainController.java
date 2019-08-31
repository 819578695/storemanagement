package me.zhengjie.modules.finance.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.finance.domain.FinanceMaintain;
import me.zhengjie.modules.finance.service.FinanceMaintainService;
import me.zhengjie.modules.finance.service.dto.FinanceMaintainQueryCriteria;
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
@Api(tags = "FinanceMaintain管理")
@RestController
@RequestMapping("api")
public class FinanceMaintainController {

    @Autowired
    private FinanceMaintainService financeMaintainService;

    @Log("查询FinanceMaintain")
    @ApiOperation(value = "查询FinanceMaintain")
    @GetMapping(value = "/financeMaintain")
    @PreAuthorize("hasAnyRole('ADMIN','FINANCEMAINTAIN_ALL','FINANCEMAINTAIN_SELECT')")
    public ResponseEntity getFinanceMaintains(FinanceMaintainQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(financeMaintainService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增FinanceMaintain")
    @ApiOperation(value = "新增FinanceMaintain")
    @PostMapping(value = "/financeMaintain")
    @PreAuthorize("hasAnyRole('ADMIN','FINANCEMAINTAIN_ALL','FINANCEMAINTAIN_CREATE')")
    public ResponseEntity create(@Validated @RequestBody FinanceMaintain resources){
        return new ResponseEntity(financeMaintainService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改FinanceMaintain")
    @ApiOperation(value = "修改FinanceMaintain")
    @PutMapping(value = "/financeMaintain")
    @PreAuthorize("hasAnyRole('ADMIN','FINANCEMAINTAIN_ALL','FINANCEMAINTAIN_EDIT')")
    public ResponseEntity update(@Validated @RequestBody FinanceMaintain resources){
        financeMaintainService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除FinanceMaintain")
    @ApiOperation(value = "删除FinanceMaintain")
    @DeleteMapping(value = "/financeMaintain/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','FINANCEMAINTAIN_ALL','FINANCEMAINTAIN_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        financeMaintainService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}

package me.zhengjie.modules.finance.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.finance.domain.FinaceMargin;
import me.zhengjie.modules.finance.service.FinaceMarginService;
import me.zhengjie.modules.finance.service.dto.FinaceMarginQueryCriteria;
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
@Api(tags = "FinaceMargin管理")
@RestController
@RequestMapping("api")
public class FinaceMarginController {

    @Autowired
    private FinaceMarginService finaceMarginService;

    @Log("查询FinaceMargin")
    @ApiOperation(value = "查询FinaceMargin")
    @GetMapping(value = "/finaceMargin")
    @PreAuthorize("hasAnyRole('ADMIN','FINACEMARGIN_ALL','FINACEMARGIN_SELECT')")
    public ResponseEntity getFinaceMargins(FinaceMarginQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(finaceMarginService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增FinaceMargin")
    @ApiOperation(value = "新增FinaceMargin")
    @PostMapping(value = "/finaceMargin")
    @PreAuthorize("hasAnyRole('ADMIN','FINACEMARGIN_ALL','FINACEMARGIN_CREATE')")
    public ResponseEntity create(@Validated @RequestBody FinaceMargin resources){
        return new ResponseEntity(finaceMarginService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改FinaceMargin")
    @ApiOperation(value = "修改FinaceMargin")
    @PutMapping(value = "/finaceMargin")
    @PreAuthorize("hasAnyRole('ADMIN','FINACEMARGIN_ALL','FINACEMARGIN_EDIT')")
    public ResponseEntity update(@Validated @RequestBody FinaceMargin resources){
        finaceMarginService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除FinaceMargin")
    @ApiOperation(value = "删除FinaceMargin")
    @DeleteMapping(value = "/finaceMargin/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','FINACEMARGIN_ALL','FINACEMARGIN_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        finaceMarginService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}

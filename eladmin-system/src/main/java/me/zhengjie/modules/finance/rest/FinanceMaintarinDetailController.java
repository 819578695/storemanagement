package me.zhengjie.modules.finance.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.finance.domain.FinanceMaintarinDetail;
import me.zhengjie.modules.finance.service.FinanceMaintarinDetailService;
import me.zhengjie.modules.finance.service.dto.FinanceMaintarinDetailQueryCriteria;
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
@Api(tags = "FinanceMaintarinDetail管理")
@RestController
@RequestMapping("api")
public class FinanceMaintarinDetailController {

    @Autowired
    private FinanceMaintarinDetailService financeMaintarinDetailService;

    @Log("查询FinanceMaintarinDetail")
    @ApiOperation(value = "查询FinanceMaintarinDetail")
    @GetMapping(value = "/financeMaintarinDetail")
    @PreAuthorize("hasAnyRole('ADMIN','FINANCEMAINTARINDETAIL_ALL','FINANCEMAINTARINDETAIL_SELECT')")
    public ResponseEntity getFinanceMaintarinDetails(FinanceMaintarinDetailQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(financeMaintarinDetailService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增FinanceMaintarinDetail")
    @ApiOperation(value = "新增FinanceMaintarinDetail")
    @PostMapping(value = "/financeMaintarinDetail")
    @PreAuthorize("hasAnyRole('ADMIN','FINANCEMAINTARINDETAIL_ALL','FINANCEMAINTARINDETAIL_CREATE')")
    public ResponseEntity create(@Validated @RequestBody FinanceMaintarinDetail resources){
        return new ResponseEntity(financeMaintarinDetailService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改FinanceMaintarinDetail")
    @ApiOperation(value = "修改FinanceMaintarinDetail")
    @PutMapping(value = "/financeMaintarinDetail")
    @PreAuthorize("hasAnyRole('ADMIN','FINANCEMAINTARINDETAIL_ALL','FINANCEMAINTARINDETAIL_EDIT')")
    public ResponseEntity update(@Validated @RequestBody FinanceMaintarinDetail resources){
        financeMaintarinDetailService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除FinanceMaintarinDetail")
    @ApiOperation(value = "删除FinanceMaintarinDetail")
    @DeleteMapping(value = "/financeMaintarinDetail/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','FINANCEMAINTARINDETAIL_ALL','FINANCEMAINTARINDETAIL_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        financeMaintarinDetailService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
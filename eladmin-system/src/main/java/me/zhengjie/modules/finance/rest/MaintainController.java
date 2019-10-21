package me.zhengjie.modules.finance.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.finance.domain.Maintain;
import me.zhengjie.modules.finance.service.MaintainService;
import me.zhengjie.modules.finance.service.dto.MaintainQueryCriteria;
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
public class MaintainController {

    @Autowired
    private MaintainService maintainService;

    @Log("查询FinanceMaintain")
    @ApiOperation(value = "查询FinanceMaintain")
    @GetMapping(value = "/maintain")
    @PreAuthorize("hasAnyRole('ADMIN','ACCOUNT_ALL','ACCOUNT_SELECT')")
    public ResponseEntity getFinanceMaintains(MaintainQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(maintainService.queryAll(criteria,pageable),HttpStatus.OK);
    }

/*   @Log("新增FinanceMaintain")
    @ApiOperation(value = "新增FinanceMaintain")
    @PostMapping(value = "/maintain")
    public ResponseEntity create(@Validated @RequestBody Maintain resources){
        return new ResponseEntity(maintainService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改FinanceMaintain")
    @ApiOperation(value = "修改FinanceMaintain")
    @PutMapping(value = "/maintain")
    @PreAuthorize("hasAnyRole('ADMIN','MAINTAIN_ALL','MAINTAIN_EDIT')")
    public ResponseEntity update(@Validated @RequestBody Maintain resources){
        maintainService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除FinanceMaintain")
    @ApiOperation(value = "删除FinanceMaintain")
    @DeleteMapping(value = "/maintain/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MAINTAIN_ALL','MAINTAIN_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        maintainService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }*/
}

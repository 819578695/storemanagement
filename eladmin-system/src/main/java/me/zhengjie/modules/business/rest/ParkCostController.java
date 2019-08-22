package me.zhengjie.modules.business.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.business.domain.ParkCost;
import me.zhengjie.modules.business.service.ParkCostService;
import me.zhengjie.modules.business.service.dto.ParkCostQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

/**
* @author kang
* @date 2019-08-22
*/
@Api(tags = "ParkCost管理")
@RestController
@RequestMapping("api")
public class ParkCostController {

    @Autowired
    private ParkCostService parkCostService;

    @Log("查询ParkCost")
    @ApiOperation(value = "查询ParkCost")
    @GetMapping(value = "/parkCost/all")
    @PreAuthorize("hasAnyRole('ADMIN','PARKCOST_ALL','PARKCOST_SELECT')")
    public ResponseEntity getParkCostALL(@PageableDefault(value = 2000, direction = Sort.Direction.ASC) Pageable pageable){
        return new ResponseEntity(parkCostService.queryAll(pageable),HttpStatus.OK);
    }
    @Log("查询ParkCost")
    @ApiOperation(value = "查询ParkCost")
    @GetMapping(value = "/parkCost")
    @PreAuthorize("hasAnyRole('ADMIN','PARKCOST_ALL','PARKCOST_SELECT')")
    public ResponseEntity getParkCosts(ParkCostQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(parkCostService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增ParkCost")
    @ApiOperation(value = "新增ParkCost")
    @PostMapping(value = "/parkCost")
    @PreAuthorize("hasAnyRole('ADMIN','PARKCOST_ALL','PARKCOST_CREATE')")
    public ResponseEntity create(@Validated @RequestBody ParkCost resources){
        return new ResponseEntity(parkCostService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改ParkCost")
    @ApiOperation(value = "修改ParkCost")
    @PutMapping(value = "/parkCost")
    @PreAuthorize("hasAnyRole('ADMIN','PARKCOST_ALL','PARKCOST_EDIT')")
    public ResponseEntity update(@Validated @RequestBody ParkCost resources){
        parkCostService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除ParkCost")
    @ApiOperation(value = "删除ParkCost")
    @DeleteMapping(value = "/parkCost/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PARKCOST_ALL','PARKCOST_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        parkCostService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}

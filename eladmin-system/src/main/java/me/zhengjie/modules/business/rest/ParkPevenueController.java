package me.zhengjie.modules.business.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.business.domain.ParkPevenue;
import me.zhengjie.modules.business.service.ParkPevenueService;
import me.zhengjie.modules.business.service.dto.ParkPevenueQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

/**
* @author kang
* @date 2019-08-23
*/
@Api(tags = "ParkPevenue管理")
@RestController
@RequestMapping("api")
public class ParkPevenueController {

    @Autowired
    private ParkPevenueService parkPevenueService;

    @Log("查询ParkPevenue")
    @ApiOperation(value = "查询ParkPevenue")
    @GetMapping(value = "/parkPevenue")
    @PreAuthorize("hasAnyRole('ADMIN','PARKPEVENUE_ALL','PARKPEVENUE_SELECT')")
    public ResponseEntity getParkPevenues(ParkPevenueQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(parkPevenueService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增ParkPevenue")
    @ApiOperation(value = "新增ParkPevenue")
    @PostMapping(value = "/parkPevenue")
    @PreAuthorize("hasAnyRole('ADMIN','PARKPEVENUE_ALL','PARKPEVENUE_CREATE')")
    public ResponseEntity create(@Validated @RequestBody ParkPevenue resources){
        return new ResponseEntity(parkPevenueService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改ParkPevenue")
    @ApiOperation(value = "修改ParkPevenue")
    @PutMapping(value = "/parkPevenue")
    @PreAuthorize("hasAnyRole('ADMIN','PARKPEVENUE_ALL','PARKPEVENUE_EDIT')")
    public ResponseEntity update(@Validated @RequestBody ParkPevenue resources){
        parkPevenueService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除ParkPevenue")
    @ApiOperation(value = "删除ParkPevenue")
    @DeleteMapping(value = "/parkPevenue/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PARKPEVENUE_ALL','PARKPEVENUE_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        parkPevenueService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
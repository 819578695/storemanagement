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
    @Log("查询所有ParkPevenue")
    @ApiOperation(value = "查询所有ParkPevenue")
    @GetMapping(value = "/parkPevenueAll")
    @PreAuthorize("hasAnyRole('ADMIN','PARKPEVENUE_ALL','PARKPEVENUE_EXPORT_ALL')")
    public ResponseEntity parkPevenueAll(ParkPevenueQueryCriteria criteria){
        return new ResponseEntity(parkPevenueService.queryAll(criteria),HttpStatus.OK);
    }

    @Log("新增ParkPevenue")
    @ApiOperation(value = "新增ParkPevenue")
    @PostMapping(value = "/parkPevenue")
    @PreAuthorize("hasAnyRole('ADMIN','PARKPEVENUE_ALL','PARKPEVENUE_CREATE')")
    public ResponseEntity create(@Validated @RequestBody ParkPevenue resources){
        return new ResponseEntity(parkPevenueService.create(resources),HttpStatus.CREATED);
    }

    @Log("审核ParkPevenue")
    @ApiOperation(value = "审核ParkPevenue")
    @PostMapping(value = "/pevenueVertify")
    @PreAuthorize("hasAnyRole('ADMIN','PARKPEVENUE_ALL','PARKPEVENUE_VERTIFY')")
    public ResponseEntity vertify( @RequestBody Long[] vertifys, @RequestParam Integer  status){
        parkPevenueService.vertify(vertifys,status);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("修改ParkPevenue")
    @ApiOperation(value = "修改ParkPevenue")
    @PutMapping(value = "/parkPevenue")
    @PreAuthorize("hasAnyRole('ADMIN','PARKPEVENUE_ALL','PARKPEVENUE_EDIT')")
    public ResponseEntity update(@Validated @RequestBody ParkPevenue resources){
        parkPevenueService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("补缴ParkPevenue")
    @ApiOperation(value = "补缴ParkPevenue")
    @PutMapping(value = "/parkPevenuePayBack")
    @PreAuthorize("hasAnyRole('ADMIN','PARKPEVENUE_ALL','PARKPEVENUE_PAYBACK')")
    public ResponseEntity payBack(@Validated @RequestBody ParkPevenue resources){
        return new ResponseEntity(parkPevenueService.payBack(resources),HttpStatus.OK);
    }


    @Log("删除ParkPevenue")
    @ApiOperation(value = "删除ParkPevenue")
    @DeleteMapping(value = "/parkPevenue/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PARKPEVENUE_ALL','PARKPEVENUE_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        parkPevenueService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("查询ParkPevenue统计数据")
    @ApiOperation(value = "查询ParkPevenue统计数据")
    @GetMapping (value = "/findPevenueMoney/{deptId}")
    public ResponseEntity findPevenueMoney(@PathVariable Long deptId){
        return new ResponseEntity(parkPevenueService.findPevenueMoney(deptId),HttpStatus.OK);
    }
}

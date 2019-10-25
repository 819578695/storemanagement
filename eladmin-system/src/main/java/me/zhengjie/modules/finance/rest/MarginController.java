package me.zhengjie.modules.finance.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.finance.service.MarginService;
import me.zhengjie.modules.finance.service.dto.MarginQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

/**
* @author nmk
* @date 2019-08-29
*/
@Api(tags = "Margin管理")
@RestController
@RequestMapping("api")
public class MarginController {

    @Autowired
    private MarginService marginService;

//    @Log("查询收入Margin")
    @ApiOperation(value = "查询Margin")
    @GetMapping(value = "/margin")
    @PreAuthorize("hasAnyRole('ADMIN','MARGIN_ALL','MARGIN_SELECT')")
    public ResponseEntity getFinaceMargins(MarginQueryCriteria criteria){
        return new ResponseEntity(marginService.queryAll(criteria),HttpStatus.OK);
    }
//    @Log("查询成本Margin")
    @ApiOperation(value = "查询Margin")
    @GetMapping(value = "/marginCost")
    @PreAuthorize("hasAnyRole('ADMIN','MARGIN_ALL','MARGIN_SELECT')")
    public ResponseEntity getFinaceMarginsCost(MarginQueryCriteria criteria){
        return new ResponseEntity(marginService.queryCostAll(criteria),HttpStatus.OK);
    }
//    @Log("查询deptTree")
    @GetMapping(value = "/marginTree")
    @PreAuthorize("hasAnyRole('ADMIN','MARGIN_ALL','MARGIN_SELECT')")
    public  ResponseEntity getMarginTree(MarginQueryCriteria criteria){
        return new ResponseEntity(marginService.buildTree(criteria), HttpStatus.OK);
    }
}

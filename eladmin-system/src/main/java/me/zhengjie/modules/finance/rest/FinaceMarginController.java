package me.zhengjie.modules.finance.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.finance.service.FinaceMarginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity getFinaceMargins( Object criteria, Pageable pageable){
        return new ResponseEntity(finaceMarginService.queryAll(criteria,pageable),HttpStatus.OK);
    }

}

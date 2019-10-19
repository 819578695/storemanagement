package me.zhengjie.modules.finance.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.finance.domain.MaintarinDetail;
import me.zhengjie.modules.finance.service.MaintarinDetailService;
import me.zhengjie.modules.finance.service.dto.AccountAllotDTO;
import me.zhengjie.modules.finance.service.dto.MaintarinDetailQueryCriteria;
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
public class MaintarinDetailController {

    @Autowired
    private MaintarinDetailService maintarinDetailService;

    @Log("查询FinanceMaintarinDetail")
    @ApiOperation(value = "查询FinanceMaintarinDetail")
    @GetMapping(value = "/maintarinDetail")
    @PreAuthorize("hasAnyRole('ADMIN','MaintarinDetail_ALL','MaintarinDetail_SELECT')")
    public ResponseEntity getMaintarinDetails(MaintarinDetailQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(maintarinDetailService.queryAll(criteria,pageable),HttpStatus.OK);
    }

   /* @Log("新增MaintarinDetail")
    @ApiOperation(value = "新增MaintarinDetail")
    @PostMapping(value = "/maintarinDetail")
    @PreAuthorize("hasAnyRole('ADMIN','MaintarinDetail_ALL','MaintarinDetail_CREATE')")
    public ResponseEntity create(@Validated @RequestBody MaintarinDetail resources){
        maintarinDetailService.create(resources);
        return new ResponseEntity(HttpStatus.OK);
    }*/

    @Log("资金调拨")
    @ApiOperation(value = "资金调拨MaintarinDetail")
    @PostMapping(value = "/maintarinDetailAdd")
    @PreAuthorize("hasAnyRole('ADMIN','MaintarinDetail_ALL','MaintarinDetail_ALLOT')")
    public ResponseEntity createDetail(@Validated @RequestBody AccountAllotDTO resources){
        maintarinDetailService.createDetail(resources);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("修改MaintarinDetail")
    @ApiOperation(value = "修改MaintarinDetail")
    @PutMapping(value = "/maintarinDetail")
    @PreAuthorize("hasAnyRole('ADMIN','MaintarinDetail_ALL','MaintarinDetail_EDIT')")
    public ResponseEntity update(@Validated @RequestBody MaintarinDetail resources){
        maintarinDetailService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除MaintarinDetail")
    @ApiOperation(value = "删除MaintarinDetail")
    @DeleteMapping(value = "/maintarinDetail/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MaintarinDetail_ALL','MaintarinDetail_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        maintarinDetailService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("查询金额")
    @GetMapping(value = "/getMoney")
    @PreAuthorize("hasAnyRole('ADMIN','MaintarinDetail_ALL','MaintarinDetail_SELECT')")
    public  ResponseEntity getMoney(MaintarinDetailQueryCriteria criteria){
        return new ResponseEntity(maintarinDetailService.getMoney(criteria),HttpStatus.OK);
    }
}

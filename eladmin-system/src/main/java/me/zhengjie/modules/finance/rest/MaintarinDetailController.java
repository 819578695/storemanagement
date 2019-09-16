package me.zhengjie.modules.finance.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.finance.domain.MaintarinDetail;
import me.zhengjie.modules.finance.service.MaintarinDetailService;
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
    @PreAuthorize("hasAnyRole('ADMIN','FINANCEMAINTARINDETAIL_ALL','FINANCEMAINTARINDETAIL_SELECT')")
    public ResponseEntity getFinanceMaintarinDetails(MaintarinDetailQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(maintarinDetailService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增FinanceMaintarinDetail")
    @ApiOperation(value = "新增FinanceMaintarinDetail")
    @PostMapping(value = "/maintarinDetail")
    @PreAuthorize("hasAnyRole('ADMIN','FINANCEMAINTARINDETAIL_ALL','FINANCEMAINTARINDETAIL_CREATE')")
    public ResponseEntity create(@Validated @RequestBody MaintarinDetail resources){
        maintarinDetailService.create(resources);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("修改FinanceMaintarinDetail")
    @ApiOperation(value = "修改FinanceMaintarinDetail")
    @PutMapping(value = "/maintarinDetail")
    @PreAuthorize("hasAnyRole('ADMIN','FINANCEMAINTARINDETAIL_ALL','FINANCEMAINTARINDETAIL_EDIT')")
    public ResponseEntity update(@Validated @RequestBody MaintarinDetail resources){
        maintarinDetailService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除FinanceMaintarinDetail")
    @ApiOperation(value = "删除FinanceMaintarinDetail")
    @DeleteMapping(value = "/maintarinDetail/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','FINANCEMAINTARINDETAIL_ALL','FINANCEMAINTARINDETAIL_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        maintarinDetailService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("查询金额")
    @GetMapping(value = "/getMoney")
    @PreAuthorize("hasAnyRole('ADMIN','FINANCEMAINTARINDETAIL_ALL','FINANCEMAINTARINDETAIL_SELECT')")
    public  ResponseEntity getMoney(MaintarinDetailQueryCriteria criteria){
        return new ResponseEntity(maintarinDetailService.getMoney(criteria),HttpStatus.OK);
    }
}

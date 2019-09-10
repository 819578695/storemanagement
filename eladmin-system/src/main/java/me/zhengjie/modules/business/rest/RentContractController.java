package me.zhengjie.modules.business.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.business.domain.RentContract;
import me.zhengjie.modules.business.service.RentContractService;
import me.zhengjie.modules.business.service.dto.RentContractQueryCriteria;
import me.zhengjie.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
* @author kang
* @date 2019-08-28
*/
@Api(tags = "RentContract管理")
@RestController
@RequestMapping("api")
public class RentContractController {

    @Autowired
    private RentContractService rentContractService;

    @Log("查询RentContract")
    @ApiOperation(value = "查询RentContract")
    @GetMapping(value = "/rentContract")
    @PreAuthorize("hasAnyRole('ADMIN','RENTCONTRACT_ALL','RENTCONTRACT_SELECT')")
    public ResponseEntity getRentContracts(RentContractQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(rentContractService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("根据部门查询RentContract")
    @ApiOperation(value = "根据部门RentContract")
    @GetMapping(value = "/rentContractByDeptId/{deptId}")
    @PreAuthorize("hasAnyRole('ADMIN','RENTCONTRACT_ALL','RENTCONTRACT_SELECT')")
    public ResponseEntity rentContractByDeptId(@PathVariable Long deptId){
        return new ResponseEntity(rentContractService.findByDeptId(deptId),HttpStatus.OK);
    }


    @Log("新增RentContract")
    @ApiOperation(value = "新增RentContract")
    @PostMapping(value = "/rentContract")
    @PreAuthorize("hasAnyRole('ADMIN','RENTCONTRACT_ALL','RENTCONTRACT_CREATE')")
    public ResponseEntity create(@Validated @RequestBody RentContract resources){
        return new ResponseEntity(rentContractService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改RentContract")
    @ApiOperation(value = "修改RentContract")
    @PutMapping(value = "/rentContract")
    @PreAuthorize("hasAnyRole('ADMIN','RENTCONTRACT_ALL','RENTCONTRACT_EDIT')")
    public ResponseEntity update(@Validated @RequestBody RentContract resources){
        rentContractService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("上传RentContract")
    @ApiOperation(value = "上传RentContract")
    @PostMapping(value="/upload/{contractNo}")
    @PreAuthorize("hasAnyRole('ADMIN','RENTCONTRACT_ALL','RENTCONTRACT_EDIT')")
    public ResponseEntity uploadHeadPortrait(MultipartHttpServletRequest multipartRequest,@PathVariable String contractNo) throws Exception{
        String path = rentContractService.uploadFile(multipartRequest,contractNo);
        if (!StringUtils.isEmpty(path)) {
            return new ResponseEntity(path,HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除RentContract")
    @ApiOperation(value = "删除RentContract")
    @DeleteMapping(value = "/rentContract/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','RENTCONTRACT_ALL','RENTCONTRACT_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        rentContractService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}

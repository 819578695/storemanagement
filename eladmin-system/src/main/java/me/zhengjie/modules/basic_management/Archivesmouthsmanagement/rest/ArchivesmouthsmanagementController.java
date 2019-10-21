package me.zhengjie.modules.basic_management.Archivesmouthsmanagement.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.domain.Archivesmouthsmanagement;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.service.ArchivesmouthsmanagementService;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.service.dto.ArchivesmouthsmanagementQueryCriteria;
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


@Api(tags = "Tenantinformation管理")
@RestController
@RequestMapping("api")
public class ArchivesmouthsmanagementController {

    @Autowired
    private ArchivesmouthsmanagementService archivesmouthsmanagementService;

    @Log("查询Archivesmouthsmanagement")
    @ApiOperation(value = "查询Archivesmouthsmanagement")
    @GetMapping("/archivesmouthsmanagement")
    @PreAuthorize("hasAnyRole('ADMIN','ARCHIVESMOUTHSMANAGEMENT_ALL','ARCHIVESMOUTHSMANAGEMENT_SELECT')")
    public ResponseEntity getArchivesmouthsmanagement(ArchivesmouthsmanagementQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(archivesmouthsmanagementService.queryAll(criteria,pageable), HttpStatus.OK);
    }
    @Log("查询Archivesmouthsmanagement")
    @ApiOperation(value = "查询Archivesmouthsmanagement")
    @GetMapping("/getarchivesmouthsmanagementAll")
    @PreAuthorize("hasAnyRole('ADMIN','ARCHIVESMOUTHSMANAGEMENT_ALL','ARCHIVESMOUTHSMANAGEMENT_SELECT')")
    public ResponseEntity getarchivesmouthsmanagementAll(ArchivesmouthsmanagementQueryCriteria criteria){
        return new ResponseEntity(archivesmouthsmanagementService.queryAll(criteria), HttpStatus.OK);
    }

    @Log("查询Archivesmouthsmanagement")
    @ApiOperation(value = "查询Archivesmouthsmanagement")
    @GetMapping("/archivesmouthsmanagement/all")
    @PreAuthorize("hasAnyRole('ADMIN','ARCHIVESMOUTHSMANAGEMENT_ALL','ARCHIVESMOUTHSMANAGEMENT_SELECT')")
    public ResponseEntity getArchivesmouthsmanagementAll(ArchivesmouthsmanagementQueryCriteria criteria){
        return new ResponseEntity(archivesmouthsmanagementService.queryAll(criteria), HttpStatus.OK);
    }

    @Log("根据部门查询Archivesmouthsmanagement")
    @ApiOperation(value = "根据部门Archivesmouthsmanagement")
    @GetMapping(value = "/archivesmouthsmanagementByDeptId/{deptId}")
    public ResponseEntity archivesmouthsmanagementByDeptId(@PathVariable Long deptId){
        return new ResponseEntity(archivesmouthsmanagementService.findByDeptId(deptId),HttpStatus.OK);
    }

    @Log("根据部门查询已出租Archivesmouthsmanagement")
    @ApiOperation(value = "根据部门Archivesmouthsmanagement")
    @GetMapping(value = "/findByDeptIdAndTenementNameIsNotNull/{deptId}")
    public ResponseEntity findByDeptIdAndTenementNameIsNotNull(@PathVariable Long deptId){
        return new ResponseEntity(archivesmouthsmanagementService.findByDeptIdAndTenementNameIsNotNull(deptId),HttpStatus.OK);
    }

    @Log("根据部门查询未出租Archivesmouthsmanagement")
    @ApiOperation(value = "根据部门查询未出租Archivesmouthsmanagement")
    @GetMapping(value = "/findByDeptIdAndTenementNameIsNull/{deptId}")
    public ResponseEntity findByDeptIdAndTenementNameIsNull(@PathVariable Long deptId){
        return new ResponseEntity(archivesmouthsmanagementService.findByDeptIdAndTenementNameIsNull(deptId),HttpStatus.OK);
    }

    @Log("新增Archivesmouthsmanagement")
    @ApiOperation(value = "新增Archivesmouthsmanagement")
    @PostMapping(value = "/archivesmouthsmanagement")
    @PreAuthorize("hasAnyRole('ADMIN','ARCHIVESMOUTHSMANAGEMENT_ALL','ARCHIVESMOUTHSMANAGEMENT_CREATE')")
    public ResponseEntity create(@Validated @RequestBody Archivesmouthsmanagement resources){
        return new ResponseEntity(archivesmouthsmanagementService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改Archivesmouthsmanagement")
    @ApiOperation(value = "修改Archivesmouthsmanagement")
    @PutMapping(value = "/archivesmouthsmanagement")
    @PreAuthorize("hasAnyRole('ADMIN','ARCHIVESMOUTHSMANAGEMENT_ALL','ARCHIVESMOUTHSMANAGEMENT_EDIT')")
    public ResponseEntity update(@Validated @RequestBody Archivesmouthsmanagement resources){
        archivesmouthsmanagementService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除Archivesmouthsmanagement")
    @ApiOperation(value = "删除Archivesmouthsmanagement")
    @DeleteMapping(value = "/archivesmouthsmanagement/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ARCHIVESMOUTHSMANAGEMENT_ALL','ARCHIVESMOUTHSMANAGEMENT_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        archivesmouthsmanagementService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("上传图片")
    @ApiOperation(value = "上传RentContract")
    @PostMapping(value="/uploadPictureExamine/{contractNo}")
    public ResponseEntity uploadPictureExamine(MultipartHttpServletRequest multipartRequest, @PathVariable String contractNo) throws Exception{
        String path = archivesmouthsmanagementService.uploadPictureExamine(multipartRequest,contractNo);
        if (!StringUtils.isEmpty(path)) {
            return new ResponseEntity(path,HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /*@GetMapping(value = "/index")
    public ResponseModel index(String iv, String encryptedData, String code) {
        if (StringUtils.isEmpty(encryptedData) || StringUtils.isEmpty(iv)|| StringUtils.isEmpty(code)) {
            return ResponseModel.error("参数错误");
        }
        // 获取sessionKey
        Map<Object, Object> map = wechat.getInfo(code);
        if (map == null) {
            return ResponseModel.error("获取sessionKey失败");
        }
        String sessionKey = map.get("sessionKey").toString();
        Map<String, Object> childMap = wechat.getUserInfo(encryptedData, iv, sessionKey);
        Archivesmouthsmanagement record = new Archivesmouthsmanagement();
        if(StringUtils.isNotEmpty(childMap.get("contacts").toString())) {
            record.setContacts(childMap.get("contacts").toString());
        }
        if(StringUtils.isNotEmpty(childMap.get("leasetype").toString())) {
            record.setLeasetype(childMap.get("leasetype").toString());
        }
        if(StringUtils.isNotEmpty(childMap.get("picturetoview").toString())) {
            record.setPicturetoview(childMap.get("picturetoview").toString());
        }
        archivesmouthsmanagementService.publicQuery(record);
        return ResponseModel.success(childMap);
    }*/

}

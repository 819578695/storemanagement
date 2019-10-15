package me.zhengjie.modules.basic_management.thearchives.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.domain.Picture;
import me.zhengjie.modules.basic_management.thearchives.domain.BasicsPark;
import me.zhengjie.modules.basic_management.thearchives.service.BasicsParkService;
import me.zhengjie.modules.basic_management.thearchives.service.dto.BasicsParkQueryCriteria;
import me.zhengjie.service.PictureService;
import me.zhengjie.utils.SecurityUtils;
import me.zhengjie.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.HashMap;
import java.util.Map;

/**
* @author zlk
* @date 2019-08-26
*/
@Api(tags = "BasicsPark管理")
@RestController
@RequestMapping("api")
public class BasicsParkController {

    @Autowired
    private BasicsParkService basicsParkService;

    @Log("查询BasicsPark")
    @ApiOperation(value = "查询BasicsPark")
    @GetMapping(value = "/basicsPark")
    @PreAuthorize("hasAnyRole('ADMIN','BASICSPARK_ALL','BASICSPARK_SELECT')")
    public ResponseEntity getBasicsParks(BasicsParkQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(basicsParkService.queryAll(criteria,pageable),HttpStatus.OK);
    }
    @Log("查询BasicsPark")
    @ApiOperation(value = "查询BasicsPark")
    @GetMapping(value = "/gettenantinformationAll")
    @PreAuthorize("hasAnyRole('ADMIN','BASICSPARK_ALL','BASICSPARK_SELECT')")
    public ResponseEntity gettenantinformationAll(BasicsParkQueryCriteria criteria){
        return new ResponseEntity(basicsParkService.queryAll(criteria),HttpStatus.OK);
    }
    @Log("根据部门查询BasicsPark")
    @ApiOperation(value = "根据部门BasicsPark")
    @GetMapping(value = "/basicsParkByDeptId/{deptId}")
    @PreAuthorize("hasAnyRole('ADMIN','BASICSPARK_ALL','BASICSPARK_CREATE')")
    public ResponseEntity basicsParkByDeptId(@PathVariable Long deptId){
        return new ResponseEntity(basicsParkService.findByDeptId(deptId),HttpStatus.OK);
    }

    @Log("新增BasicsPark")
    @ApiOperation(value = "新增BasicsPark")
    @PostMapping(value = "/basicsPark")
    @PreAuthorize("hasAnyRole('ADMIN','BASICSPARK_ALL','BASICSPARK_CREATE')")
    public ResponseEntity create(@Validated @RequestBody BasicsPark resources){
        return new ResponseEntity(basicsParkService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改BasicsPark")
    @ApiOperation(value = "修改BasicsPark")
    @PutMapping(value = "/basicsPark")
    @PreAuthorize("hasAnyRole('ADMIN','BASICSPARK_ALL','BASICSPARK_EDIT')")
    public ResponseEntity update(@Validated @RequestBody BasicsPark resources){
        basicsParkService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    /*@Log("修改BasicsPark")
    @ApiOperation(value = "修改BasicsPark")
    @PostMapping(value = "/basicsParksc")
    @PreAuthorize("hasAnyRole('ADMIN','BASICSPARK_ALL','BASICSPARK_EDIT')")
    public ResponseEntity updatesc(@RequestParam MultipartFile file){
        String userName = SecurityUtils.getUsername();
        BasicsPark picture = basicsParkService.updatesc(file);
        Map map = new HashMap(3);
        map.put("errno",0);
        map.put("data",new String[]{picture.getUrl()});
        return new ResponseEntity(map,HttpStatus.OK);
    }*/

    @Log("删除BasicsPark")
    @ApiOperation(value = "删除BasicsPark")
    @DeleteMapping(value = "/basicsPark/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','BASICSPARK_ALL','BASICSPARK_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        basicsParkService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("上传图片")
    @ApiOperation(value = "上传RentContract")
    @PostMapping(value="/uploadPicture/{contractNo}")
    public ResponseEntity uploadPicture(MultipartHttpServletRequest multipartRequest, @PathVariable String contractNo) throws Exception{
        String path = basicsParkService.uploadPicture(multipartRequest,contractNo);
        if (!StringUtils.isEmpty(path)) {
            return new ResponseEntity(path,HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}

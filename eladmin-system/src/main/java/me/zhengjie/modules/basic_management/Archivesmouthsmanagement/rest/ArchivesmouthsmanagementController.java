package me.zhengjie.modules.basic_management.Archivesmouthsmanagement.rest;

import cn.hutool.core.codec.Base64;
import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.domain.Archivesmouthsmanagement;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.service.ArchivesmouthsmanagementService;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.service.dto.ArchivesmouthsmanagementQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.List;
import java.util.Map;

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
    @PreAuthorize("hasAnyRole('ADMIN','ARCHIVESMOUTHSMANAGEMENT_ALL','ARCHIVESMOUTHSMANAGEMENT_SELECT')")
    public ResponseEntity archivesmouthsmanagementByDeptId(@PathVariable Long deptId){
        return new ResponseEntity(archivesmouthsmanagementService.findByDeptId(deptId),HttpStatus.OK);
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

    @Log("微信小程序")
    @ApiOperation(value = "微信小程序查询")
    @GetMapping("/wechatquery")
    public List<Archivesmouthsmanagement> wechatQuery(HttpServletRequest request){
       List<Archivesmouthsmanagement> list = archivesmouthsmanagementService.publiccity(request);
        return list ;
    }

    @RequestMapping("/deciphering")
    @ResponseBody
    public String deciphering(String encrypdata, String ivdata, String sessionkey, HttpServletRequest request) throws IOException {
        byte[] encrypData = Base64.decode(encrypdata);
        byte[] ivData = Base64.decode(ivdata);
        byte[] sessionKey = Base64.decode(sessionkey);
        String str="";
        try {
            str = decrypt(sessionKey,ivData,encrypData);
//            JSONObject x=JSONObject.parseObject(str);
//            Dto dto = WebUtils.getParamAsDto(request);
//            dto.put("phoneNumber",x.get("phoneNumber"));
//
//            Dto order = (Dto) g4Reader.queryForObject("openId.SelectUser", dto);
//            order.put("phoneNumber",x.get("phoneNumber"));
//            System.out.println(order);
            //   return order;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return str;
    }

    public static String decrypt(byte[] key, byte[] iv, byte[] encData) throws Exception {
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        //解析解密后的字符串  
        return new String(cipher.doFinal(encData),"UTF-8");
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

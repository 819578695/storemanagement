package me.zhengjie.modules.basic_management.wechat.rest;

import cn.hutool.core.codec.Base64;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.service.ArchivesmouthsmanagementService;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.service.dto.ArchivesmouthsmanagementDTO;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.service.dto.ArchivesmouthsmanagementQueryCriteria;
import me.zhengjie.modules.basic_management.wechat.UserUtil;
import me.zhengjie.modules.basic_management.wechat.Wxpoid;
import me.zhengjie.modules.basic_management.client.domain.BasicsClient;
import me.zhengjie.modules.basic_management.client.service.BasicsClientService;
import me.zhengjie.modules.basic_management.wechat.domain.NeedStall;
import me.zhengjie.modules.basic_management.wechat.domain.PublicWarehouse;
import me.zhengjie.modules.basic_management.wechat.service.NeedStallService;
import me.zhengjie.modules.basic_management.wechat.service.PublicWarehouseService;
import me.zhengjie.modules.basic_management.wechat.service.dto.NeedStallDto;
import me.zhengjie.modules.basic_management.wechat.service.dto.NeedStallQueryCriteria;
import me.zhengjie.modules.basic_management.wechat.service.dto.PublicWarehouseDto;
import me.zhengjie.modules.basic_management.wechat.service.dto.PublicWarehouseQueryCriteria;
import me.zhengjie.modules.system.service.DeptService;
import me.zhengjie.modules.system.service.dto.DeptQueryCriteria;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Api(tags = "Tenantinformation管理")
@RestController
@RequestMapping("wechat")
public class wechatController {
    @Autowired
    private ArchivesmouthsmanagementService archivesmouthsmanagementService;
    @Autowired
    private BasicsClientService basicsClientService;
    @Autowired
    private DeptService deptService;
    @Autowired
    private NeedStallService needStallService;
    @Autowired
    private PublicWarehouseService publicWarehouseService;

    @Log("微信小程序")
    @ApiOperation(value = "微信小程序查询")
    @GetMapping("/wechatquery")
    public Map wechatQuery(Long id){
        Map list = archivesmouthsmanagementService.publiccity(id);
        return list ;
    }

    @Log("微信查询")
    @ApiOperation(value = "微信查询")
    @GetMapping("/publicWechatId")
    public List<ArchivesmouthsmanagementDTO> publicWechatId(ArchivesmouthsmanagementQueryCriteria criteria){
        List<ArchivesmouthsmanagementDTO> list = archivesmouthsmanagementService.publicWechatId(criteria);
        return list;
    }

    @Log("查询所有")
    @GetMapping(value = "/WechatDeptAll")
    public Map WechatDeptAll(DeptQueryCriteria criteria){
        /*new ResponseEntity(deptService.queryAll(criteria),HttpStatus.OK)*/
        Map map = deptService.WechatDeptAll(criteria);
        return map;
    }

    @Log("微信返回openid")
    @ApiOperation(value = "微信返回openid")
    @GetMapping("/loginByopid")
    public Map loginByopid(String code){
        Map<String,String> map = new HashMap<>();
        String openid = null ;
        String token = UUID.randomUUID().toString();
        if (StringUtils.isNotEmpty(code)) {
            openid = UserUtil.getopenid(code);
            Wxpoid json = JSON.parseObject(openid, Wxpoid.class);
            map.put("openid", json.getOpenid());
            map.put("sessionKey", json.getSession_key());
            map.put("token",token);
        }
        return map;
    }
    @Log("微信新增")
    @ApiOperation(value = "微信新增")
    @PostMapping(value = "/wechatadd")
    public ResponseEntity create(@Validated @RequestParam BasicsClient resources){
        return new ResponseEntity(basicsClientService.create(resources), HttpStatus.CREATED);
    }

    @Log("微信删除")
    @ApiOperation(value = "微信删除")
    @DeleteMapping(value = "/wechatdelete/{id}")
    public ResponseEntity createdelete(@PathVariable Integer id){
        basicsClientService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("微信发布档口需求")
    @ApiOperation(value = "微信发布档口需求")
    @PostMapping(value = "/createNeedStall")
    public ResponseEntity createNeedStall( @RequestBody NeedStall resources){
        return new ResponseEntity(needStallService.create(resources),HttpStatus.OK);
    }

    @ApiOperation(value = "查询所有档口需求")
    @GetMapping(value = "/queryByNeedAll")
    public Map queryByNeedAll(NeedStallQueryCriteria criteria){
        Map map = needStallService.queryByNeedAll(criteria);
        return map;
    }

    @Log("微信发布仓库")
    @ApiOperation(value = "微信发布仓库")
    @PostMapping(value = "/createPWarehouse")
    public ResponseEntity createPWarehouse(@RequestBody PublicWarehouse resources){
        return new ResponseEntity(publicWarehouseService.crite(resources),HttpStatus.OK);
    }

    @ApiOperation(value = "查询所有发布仓库")
    @GetMapping(value = "/queryByPWarehouseAll")
    public Map queryByPWarehouse(PublicWarehouseQueryCriteria criteria){
        Map map = publicWarehouseService.queryByPWarehouseAll(criteria);
        return map;
    }

    @ApiOperation(value = "发布仓库图片上传")
    @PostMapping(value = "/upImage")
    public ResponseEntity upImage(MultipartFile file) throws Exception{
        String path = publicWarehouseService.upImage(file);
        return new ResponseEntity(path,HttpStatus.OK);
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
//            Dto dto = WebUtils.getParamAsDto(request);UserUtil
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
}

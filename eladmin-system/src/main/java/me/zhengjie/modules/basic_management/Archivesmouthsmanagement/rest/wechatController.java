package me.zhengjie.modules.basic_management.Archivesmouthsmanagement.rest;

import cn.hutool.core.codec.Base64;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.service.ArchivesmouthsmanagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Map;

@Api(tags = "Tenantinformation管理")
@RestController
@RequestMapping("wechat")
public class wechatController {
    @Autowired
    private ArchivesmouthsmanagementService archivesmouthsmanagementService;

    @Log("微信小程序")
    @ApiOperation(value = "微信小程序查询")
    @GetMapping("/wechatquery")
    public Map wechatQuery(Long id){
        Map list = archivesmouthsmanagementService.publiccity(id);
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
}

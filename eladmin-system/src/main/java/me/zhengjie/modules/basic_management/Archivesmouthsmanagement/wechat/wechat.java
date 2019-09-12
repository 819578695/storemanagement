/*
 * Copyright (C), 2019-2019
 * FileName: WebChartSmallUtil
 * Author:   zhixiang.meng
 * Date:     2019/5/17 2:44 PM
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 *//*

package me.zhengjie.modules.basic_management.Archivesmouthsmanagement.wechat;

import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.codec.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;
import java.util.HashMap;
import java.util.Map;

*/
/**
 * 微信小程序相关登录接口
 *
 * @author zhixiang.meng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 *//*

public class wechat {

    private static Logger LOGGER = LoggerFactory.getLogger(wechat.class);

    public static final String SMALL_APPID = "success";
    */
/**
     * 获取用户小程序的openid
     *
     * @param code
     * @return
     *//*

    public static Map<Object, Object> getInfo(String code){
        String url ="https://api.weixin.qq.com/sns/jscode2session?appid="+Constant.SMALL_APPID+"&secret="+Constant.SMALL_SECRET+"&js_code="+code+"&grant_type="+Constant.GRANT_TYPE;

        //发送请求
        String data = getResponse(url);

        if (data.indexOf("errcode") != -1) {
            return null;
        }

        //解析相应内容（转换成json对象）
        JSONObject json = JSONObject.fromObject(data);

        String sessionKey = json.get("session_key") == null ? null : String.valueOf(json.get("session_key"));
        String openId = json.get("openid") == null ? null : String.valueOf(json.get("openid"));

        if (sessionKey == null) {
            return null;
        }

        Map map = new HashMap<Object, Object>();
        map.put("sessionKey", sessionKey);
        map.put("openId", openId);

        return map;
    }

    */
/**
     * 从微信小程序那里获取用户基本信息
     * @param encryptedData
     * @param iv
     * @param sessionKey
     * @return
     *//*

    public static Map<String, Object> getUserInfo(String encryptedData, String iv, String sessionKey){
        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);

        try {
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding","BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String data = new String(resultByte, "UTF-8");

                LOGGER.info("小程序解析用户信息encryptedData: "+data);

                JSONObject json = JSONObject.fromObject(data);

                String unionId = String.valueOf(json.get("unionId"));
                String openId = String.valueOf(json.get("openId"));
                String nickName = String.valueOf(json.get("nickName"));
                String avatarUrl = String.valueOf(json.get("avatarUrl"));
                String appid = String.valueOf(json.get("appId"));
                Map<String, Object> result = new HashMap<>();
                result.put("unionId", unionId);
                result.put("openId", openId);
                result.put("nickName", nickName);
                result.put("avatarUrl", avatarUrl);
                result.put("appId", appid);
                return result;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidParameterSpecException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }

    */
/**
     * 获取小程序的accessToken
     * @return
     *//*

    public static String getAccessToken(){
        String access_token_all = "";
        StringBuffer buffer = new StringBuffer(); // 用来拼接参数
        StringBuffer result = new StringBuffer(); // 用来接受返回值
        URL httpUrl = null; // HTTP URL类 用这个类来创建连接
        URLConnection connection = null; // 创建的http连接
        BufferedReader bufferedReader = null; // 接受连接受的参数
        buffer.append(Constant.GET_GLOBAL_ACCESS_TOKEN + "grant_type=" + Constant.GLOBAL_GRANT_TYPE + "&appid=" + Constant.SMALL_APPID + "&secret=" + Constant.SMALL_SECRET);
        try{
            // 创建URL
            httpUrl = new URL(buffer.toString());
            // 建立连接
            connection = httpUrl.openConnection();
            connection.setRequestProperty("accept", Constant.ACCEPT);
            connection.setRequestProperty("connection", Constant.CONNECTION);
            connection.setRequestProperty("user-agent", Constant.USER_AGENT);
            connection.connect();
            // 接收连接返回参数
            bufferedReader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
            bufferedReader.close();
            JSONObject obj = JSONObject.fromObject(result.toString());
            access_token_all = obj.get("access_token") + "";

        }catch (Exception e) {}
        return access_token_all;
    }

    public static String getQr(String url, Long recruitmentId, Long sourceId, Long jobId,
                               Long userId, Integer level, String pathUrl) {
        StringBuffer buffer = new StringBuffer(url + "/recruitmentPosters/getPostUrl" + "?"); // 用来拼接参数
        buffer.append("recruitmentId=" + recruitmentId + "&sourceId=" + sourceId) ;
        if(null != jobId) {
            buffer.append("&jobId=" + jobId);
        }
        if(null != userId) {
            buffer.append("&userId=" + userId );
        }
        if(null != level) {
            buffer.append("&level=" + level );
        }
        if(StringUtils.isNotEmpty(pathUrl)) {
            buffer.append("&pathUrl=" + pathUrl);
        }
        URL httpUrl = null;
        URLConnection connection = null;
        BufferedReader bufferedReader = null;
        StringBuffer result = new StringBuffer();
        try {
            // 创建URL
            httpUrl = new URL(buffer.toString());
            // 建立连接
            connection = httpUrl.openConnection();
            connection.setRequestProperty("accept", Constant.ACCEPT);
            connection.setRequestProperty("connection", Constant.CONNECTION);
            connection.setRequestProperty("user-agent", Constant.USER_AGENT);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();
            // 接收连接返回参数
            bufferedReader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            // 接收连接返回参数
            bufferedReader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
            bufferedReader.close();
        } catch (Exception e){e.printStackTrace();}
        return result.toString();
    }

    private static String getResponse(String serverUrl){
        LOGGER.info("小程序获取sessionKey serverUrl :{}" , serverUrl);

        StringBuffer result = new StringBuffer();
        try {
            URL url = new URL(serverUrl);
            URLConnection conn = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line;
            while((line = in.readLine()) != null){
                result.append(line);
            }
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info("小程序获取sessionKey result :{}" , result.toString());
        return result.toString();
    }
}




*/

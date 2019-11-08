/*
package me.zhengjie.modules.basic_management.Archivesmouthsmanagement.wechat;

import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.service.ArchivesmouthsmanagementService;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.service.dto.ArchivesmouthsmanagementQueryCriteria;
import me.zhengjie.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

public class wct {
    @Autowired
    private ArchivesmouthsmanagementService archivesmouthsmanagementService;

    @GetMapping(value = "/index")
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
        ArchivesmouthsmanagementQueryCriteria record = new ArchivesmouthsmanagementQueryCriteria();
        if(StringUtils.isNotEmpty(childMap.get("housenumber").toString())) {
            record.setOpenid(childMap.get("housenumber").toString());
        }
        if(StringUtils.isNotEmpty(childMap.get("leasetype").toString())) {
            record.setNickname(childMap.get("leasetype").toString());
        }
        if(StringUtils.isNotEmpty(childMap.get("picturetoview").toString())) {
            record.setAvatarurl(childMap.get("picturetoview").toString());
        }
        archivesmouthsmanagementService.saveBaseInfo(record);
        return ResponseModel.success(childMap);
    }
}
*/

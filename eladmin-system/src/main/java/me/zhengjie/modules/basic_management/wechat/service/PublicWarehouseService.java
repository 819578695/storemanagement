package me.zhengjie.modules.basic_management.wechat.service;

import me.zhengjie.modules.basic_management.wechat.domain.PublicWarehouse;
import me.zhengjie.modules.basic_management.wechat.service.dto.PublicWarehouseDto;
import me.zhengjie.modules.basic_management.wechat.service.dto.PublicWarehouseQueryCriteria;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Map;

public interface PublicWarehouseService {

    PublicWarehouseDto crite(PublicWarehouse resource);

    Map queryByPWarehouseAll(PublicWarehouseQueryCriteria criteria);

    String upImage(MultipartFile file) throws Exception;
}

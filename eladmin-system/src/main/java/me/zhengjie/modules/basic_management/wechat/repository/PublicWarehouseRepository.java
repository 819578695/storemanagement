package me.zhengjie.modules.basic_management.wechat.repository;

import me.zhengjie.modules.basic_management.wechat.domain.PublicWarehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PublicWarehouseRepository extends JpaRepository<PublicWarehouse, Long>, JpaSpecificationExecutor {

    List findByOpenId(String openId);


}

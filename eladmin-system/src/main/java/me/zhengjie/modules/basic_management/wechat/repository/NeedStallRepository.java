package me.zhengjie.modules.basic_management.wechat.repository;

import me.zhengjie.modules.basic_management.wechat.domain.NeedStall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface NeedStallRepository extends JpaRepository<NeedStall, Long>, JpaSpecificationExecutor {

    List findByOpenId(String openId);


}

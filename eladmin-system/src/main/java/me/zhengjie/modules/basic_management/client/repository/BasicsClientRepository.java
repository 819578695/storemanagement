package me.zhengjie.modules.basic_management.client.repository;

import me.zhengjie.modules.basic_management.client.domain.BasicsClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author nmkzlk
* @date 2019-09-02
*/
public interface BasicsClientRepository extends JpaRepository<BasicsClient, Integer>, JpaSpecificationExecutor {
}
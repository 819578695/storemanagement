package me.zhengjie.modules.quartz.task;

import lombok.extern.slf4j.Slf4j;
import me.zhengjie.modules.business.service.LeaseContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 测试用
 * @author Zheng Jie
 * @date 2019-01-08
 */
@Slf4j
@Component
public class LeaseContractTask {
    @Autowired
    private LeaseContractService leaseContractService;

    public void run(){
        leaseContractService.findTask();
        log.info("执行成功");
    }

}

package me.zhengjie.modules.basic_management.wechat.service;

import me.zhengjie.modules.basic_management.wechat.domain.NeedStall;
import me.zhengjie.modules.basic_management.wechat.service.dto.NeedStallDto;
import me.zhengjie.modules.basic_management.wechat.service.dto.NeedStallQueryCriteria;

import java.util.List;
import java.util.Map;

/**
 * @author Mingkun_Niu
 */
public interface NeedStallService {

    NeedStallDto create(NeedStall resources);

    Map queryByNeedAll(NeedStallQueryCriteria criteria);
}

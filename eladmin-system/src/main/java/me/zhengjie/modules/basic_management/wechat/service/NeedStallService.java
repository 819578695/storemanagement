package me.zhengjie.modules.basic_management.wechat.service;

import me.zhengjie.modules.basic_management.wechat.domain.NeedStall;
import me.zhengjie.modules.basic_management.wechat.service.dto.NeedStallDto;

/**
 * @author Mingkun_Niu
 */
public interface NeedStallService {

    NeedStallDto create(NeedStall resources);

}

package me.zhengjie.modules.basic_management.thearchives.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import me.zhengjie.domain.Picture;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.basic_management.thearchives.domain.BasicsPark;
import me.zhengjie.modules.system.repository.DeptRepository;
import me.zhengjie.utils.*;
import me.zhengjie.modules.basic_management.thearchives.repository.BasicsParkRepository;
import me.zhengjie.modules.basic_management.thearchives.service.BasicsParkService;
import me.zhengjie.modules.basic_management.thearchives.service.dto.BasicsParkDTO;
import me.zhengjie.modules.basic_management.thearchives.service.dto.BasicsParkQueryCriteria;
import me.zhengjie.modules.basic_management.thearchives.service.mapper.BasicsParkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

/**
* @author zlk
* @date 2019-08-26
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class BasicsParkServiceImpl implements BasicsParkService {

    @Autowired
    private BasicsParkRepository basicsParkRepository;

    @Autowired
    private BasicsParkMapper basicsParkMapper;

    @Autowired
    private DeptRepository deptRepository;

    public static final String SUCCESS = "success";

    public static final String CODE = "code";

    public static final String MSG = "msg";

    @Override
    public Object queryAll(BasicsParkQueryCriteria criteria, Pageable pageable){
        Page<BasicsPark> page = basicsParkRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        List<BasicsParkDTO> basicsParkDTOS = new ArrayList<>();
        for (BasicsPark basicsPark : page.getContent()){
            basicsParkDTOS.add(basicsParkMapper.toDto(basicsPark,deptRepository.findById(basicsPark.getDept().getId()).get()));
        }
        return PageUtil.toPage(basicsParkDTOS,page.getTotalElements());
    }

    @Override
    public Object findByDeptId(Long deptId) {
        return basicsParkMapper.toDto(deptId==1?basicsParkRepository.findAll():basicsParkRepository.findByDeptId(deptId));
    }

    @Override
    public Object queryAll(BasicsParkQueryCriteria criteria){
        return basicsParkMapper.toDto(basicsParkRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public BasicsParkDTO findById(Long id) {
        Optional<BasicsPark> basicsPark = basicsParkRepository.findById(id);
        ValidationUtil.isNull(basicsPark,"BasicsPark","id",id);
        return basicsParkMapper.toDto(basicsPark.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BasicsParkDTO create(BasicsPark resources) {
        return basicsParkMapper.toDto(basicsParkRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(BasicsPark resources) {
        Optional<BasicsPark> optionalBasicsPark = basicsParkRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalBasicsPark,"BasicsPark","id",resources.getId());
        BasicsPark basicsPark = optionalBasicsPark.get();
        basicsPark.copy(resources);
        basicsParkRepository.save(basicsPark);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Picture updatesc(MultipartFile multipartFile,String username) {
        File file = FileUtil.toFile(multipartFile);

        HashMap<String, Object> paramMap = new HashMap<>(1);

        paramMap.put("smfile", file);
        String result= HttpUtil.post(ElAdminConstant.Url.SM_MS_URL, paramMap);

        JSONObject jsonObject = JSONUtil.parseObj(result);
        Picture picture = null;
        if(!jsonObject.get(CODE).toString().equals(SUCCESS)){
            throw new BadRequestException(jsonObject.get(MSG).toString());
        }
        //转成实体类
        picture = JSON.parseObject(jsonObject.get("data").toString(), Picture.class);
        picture.setSize(FileUtil.getSize(Integer.valueOf(picture.getSize())));
        picture.setUsername(username);
        picture.setFilename(FileUtil.getFileNameNoEx(multipartFile.getOriginalFilename())+"."+FileUtil.getExtensionName(multipartFile.getOriginalFilename()));
        //删除临时文件
        FileUtil.deleteFile(file);
        return picture;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        basicsParkRepository.deleteById(id);
    }
}

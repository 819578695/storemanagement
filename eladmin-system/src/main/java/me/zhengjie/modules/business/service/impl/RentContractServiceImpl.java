package me.zhengjie.modules.business.service.impl;

import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.basic_management.thearchives.domain.BasicsPark;
import me.zhengjie.modules.basic_management.thearchives.repository.BasicsParkRepository;
import me.zhengjie.modules.business.domain.LeaseContract;
import me.zhengjie.modules.business.domain.ParkCost;
import me.zhengjie.modules.business.domain.RentContract;
import me.zhengjie.modules.business.repository.ParkCostRepository;
import me.zhengjie.modules.monitor.service.RedisService;
import me.zhengjie.modules.security.security.JwtUser;
import me.zhengjie.modules.system.repository.DeptRepository;
import me.zhengjie.modules.system.repository.DictDetailRepository;
import me.zhengjie.modules.system.service.mapper.DeptMapper;
import me.zhengjie.utils.*;
import me.zhengjie.modules.business.repository.RentContractRepository;
import me.zhengjie.modules.business.service.RentContractService;
import me.zhengjie.modules.business.service.dto.RentContractDTO;
import me.zhengjie.modules.business.service.dto.RentContractQueryCriteria;
import me.zhengjie.modules.business.service.mapper.RentContractMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
* @author kang
* @date 2019-08-28
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RentContractServiceImpl implements RentContractService {

    @Autowired
    private RentContractRepository rentContractRepository;

    @Autowired
    private RentContractMapper rentContractMapper;
    @Autowired
    private DeptRepository deptRepository;
    @Autowired
    private ParkCostRepository parkCostRepository;
    @Autowired
    private DictDetailRepository dictDetailRepository;
    @Autowired
    private RedisService redisService;
    @Autowired
    private BasicsParkRepository basicsParkRepository;

    @Value("${httpUrl}")
    private String httpUrl; //服务器文件地址
    @Value("${filePath}")
    private String filePath; //文件路径

    @Override
    public Object queryAll(RentContractQueryCriteria criteria, Pageable pageable){
        Page<RentContract> page = rentContractRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        List<RentContractDTO> rentContractDTOS = new ArrayList<>();
        for (RentContract rentContract : page.getContent()) {
            List<ParkCost> parkCosts = parkCostRepository.findByRentContractIdAndDeptId(rentContract.getId(),rentContract.getDept().getId());
            BigDecimal totalMoney = new BigDecimal(0);
            for (ParkCost parkCost :parkCosts) {
                if (parkCost.getSiteRent()!=null){
                    //bigdecimal 求和(未缴费用)
                    totalMoney = totalMoney.add(parkCost.getSiteRent());
                    rentContract.setPaymentedExpenses(totalMoney);
                }
            }
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            //合同启用才会生效
            if (rentContract.getIsEnable().equals("1")){
                //合同截止时间早于当前时间 则将自动吧合同转为
                if (rentContract.getEndDate().before(timestamp)){
                    rentContract.setIsEnable("2");
                    rentContractRepository.save(rentContract);
                }
            }
            rentContractDTOS.add(rentContractMapper.toDto(rentContract,rentContract.getDept()==null?null:deptRepository.findAllById(rentContract.getDept().getId()),rentContract.getPayCycle()==null?null:dictDetailRepository.findById(rentContract.getPayCycle().getId()).get()));
        }
        return PageUtil.toPage(rentContractDTOS,page.getTotalElements());
    }

    @Override
    public Object queryAll(RentContractQueryCriteria criteria){
        return rentContractMapper.toDto(rentContractRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public Object findByDeptId(Long deptId) {
        return rentContractMapper.toDto(deptId==1?rentContractRepository.findAllByAudit():rentContractRepository.findByDeptId(deptId));
    }

    @Override
    public RentContractDTO findById(Long id) {
        Optional<RentContract> rentContract = rentContractRepository.findById(id);
        ValidationUtil.isNull(rentContract,"RentContract","id",id);
        return rentContractMapper.toDto(rentContract.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RentContractDTO create(RentContract resources) {

        /*RentContract rentContract = rentContractRepository.findById(resources.getDept().getId()).get();
        BasicsPark basicsPark = basicsParkRepository.findById(resources.getDept().getId()).get();
        if (basicsPark.getDept().getId() == rentContract.getDept().getId()){
            rentContract.setParkId(basicsPark.getId());
        }*/
        JwtUser jwtUser =(JwtUser) SecurityUtils.getUserDetails();
        Long no = 0001l;
        if (rentContractRepository.findByNewcontractNo(resources.getDept().getId())!=null){
            no=Long.valueOf(rentContractRepository.findByNewcontractNo(resources.getDept().getId()))+0001l;
        }
        resources.setContractNo(jwtUser.getDeptNo()+StringUtils.getCurentDate()+new DecimalFormat("0000").format(no));
        return rentContractMapper.toDto(rentContractRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(RentContract resources) {
        Optional<RentContract> optionalRentContract = rentContractRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalRentContract,"RentContract","id",resources.getId());
        RentContract rentContract = optionalRentContract.get();
        rentContract.copy(resources);
        rentContract.setIsAudit(0);
        rentContractRepository.save(rentContract);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        rentContractRepository.deleteById(id);
    }

    @Override
    public String uploadFile(MultipartHttpServletRequest multipartRequest,String contractNo) throws Exception {
            String imgUrl = FileUtil.uploadUtil(multipartRequest, httpUrl, filePath, "upfile", "/contract/image",contractNo );
            try {
                return imgUrl;
            } catch (Exception e) {
                e.printStackTrace();
            }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void vertify(Long[] vertifys, Integer status) {
        if (vertifys.length>0){
            for (Long id : vertifys) {
                RentContract rentContract = rentContractRepository.findById(id).get();
                /* 状态为审核中才能审核*/
                if (rentContract.getIsAudit()==0){
                    //审核未通过
                    if (status==1){
                        rentContract.setIsAudit(1);
                        rentContractRepository.updateByVertify1(rentContract.getId());
                    }
                    //审核通过并且类型
                    if (status==2){
                        rentContract.setIsAudit(2);
                        rentContractRepository.updateByVertify2(rentContract.getId());
                    }
                }
                else{
                    throw new BadRequestException("请勿重复审核");
                }
            }
        }
    }

}

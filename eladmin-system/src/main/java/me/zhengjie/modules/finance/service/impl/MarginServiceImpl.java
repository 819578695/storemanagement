package me.zhengjie.modules.finance.service.impl;

import me.zhengjie.modules.business.domain.ParkCost;
import me.zhengjie.modules.business.domain.ParkPevenue;
import me.zhengjie.modules.business.repository.ParkCostRepository;
import me.zhengjie.modules.business.repository.ParkPevenueRepository;
import me.zhengjie.modules.finance.service.MarginService;
import me.zhengjie.modules.finance.service.dto.MarginCostDTO;
import me.zhengjie.modules.finance.service.dto.MarginPevenueDTO;
import me.zhengjie.utils.QueryHelp;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
* @author nmk
* @date 2019-08-29
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MarginServiceImpl implements MarginService {

    @Autowired
    private ParkPevenueRepository parkPevenueRepository;
    @Autowired
    private ParkCostRepository parkCostRepository;
    @Override
    public Object queryAll(Object criteria, Pageable pageable){
        Page<ParkPevenue> pevenues = parkPevenueRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
//        Page<ParkCost> costs = parkCostRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),pageable);
        Page<ParkCost> costs = parkCostRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        List costDTOS = new ArrayList<>();
        //园区收入
        for (ParkCost cost : costs.getContent()) {
            MarginCostDTO marginCostDTO = new MarginCostDTO();
            BeanUtils.copyProperties(cost, marginCostDTO);
            costDTOS.add(marginCostDTO);
        }
        List parkPevenueDTOS = new ArrayList<>();
        //园区成本
        for (ParkPevenue pevenue : pevenues.getContent()){
            MarginPevenueDTO marginPevenueDTO = new MarginPevenueDTO();
            BeanUtils.copyProperties(pevenue, marginPevenueDTO);
            parkPevenueDTOS.add(marginPevenueDTO);
        }
        HashMap map = new HashMap();
        map.put("costs",costDTOS);
        map.put("costsTotal",costs.getTotalElements());
        map.put("pevenues",parkPevenueDTOS);
        map.put("pevenuesTotal",pevenues.getTotalElements());
        return map;
    }

}

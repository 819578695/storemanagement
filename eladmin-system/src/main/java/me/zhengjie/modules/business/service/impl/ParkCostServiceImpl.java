package me.zhengjie.modules.business.service.impl;

import me.zhengjie.modules.business.domain.ParkCost;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.business.repository.ParkCostRepository;
import me.zhengjie.modules.business.service.ParkCostService;
import me.zhengjie.modules.business.service.dto.ParkCostDTO;
import me.zhengjie.modules.business.service.dto.ParkCostQueryCriteria;
import me.zhengjie.modules.business.service.mapper.ParkCostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;

/**
* @author kang
* @date 2019-08-22
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ParkCostServiceImpl implements ParkCostService {

    @Autowired
    private ParkCostRepository parkCostRepository;

    @Autowired
    private ParkCostMapper parkCostMapper;

    @Override
    public Object queryAll(ParkCostQueryCriteria criteria, Pageable pageable){
        Page<ParkCost> page = parkCostRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(parkCostMapper::toDto));
    }
    @Override
    public Object queryAll( Pageable pageable){

        return parkCostMapper.toDto(parkCostRepository.findAll(pageable).getContent());
    }
    @Override
    public Object queryAll(ParkCostQueryCriteria criteria){
        return parkCostMapper.toDto(parkCostRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public ParkCostDTO findById(Long id) {
        Optional<ParkCost> parkCost = parkCostRepository.findById(id);
        ValidationUtil.isNull(parkCost,"ParkCost","id",id);
        return parkCostMapper.toDto(parkCost.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ParkCostDTO create(ParkCost resources) {
        return parkCostMapper.toDto(parkCostRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ParkCost resources) {
        Optional<ParkCost> optionalParkCost = parkCostRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalParkCost,"ParkCost","id",resources.getId());
        ParkCost parkCost = optionalParkCost.get();
        parkCost.copy(resources);
        parkCostRepository.save(parkCost);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        parkCostRepository.deleteById(id);
    }
}

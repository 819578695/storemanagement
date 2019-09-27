package me.zhengjie.modules.finance.service.impl;

import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.repository.ArchivesmouthsmanagementRepository;
import me.zhengjie.modules.basic_management.Archivesmouthsmanagement.service.dto.ArchiveTreeDto;
import me.zhengjie.modules.finance.repository.MarginRepository;
import me.zhengjie.modules.finance.service.MarginService;
import me.zhengjie.modules.finance.service.dto.FundMarginDTO;
import me.zhengjie.modules.finance.service.dto.MarginQueryCriteria;
import me.zhengjie.modules.finance.service.dto.TreeDTO;
import me.zhengjie.modules.system.domain.DictDetail;
import me.zhengjie.modules.system.repository.DictDetailRepository;
import me.zhengjie.modules.system.repository.DictRepository;
import me.zhengjie.modules.system.service.dto.DeptDTO;
import me.zhengjie.modules.system.service.dto.DeptQueryCriteria;
import me.zhengjie.modules.system.service.impl.DeptServiceImpl;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.*;

/**
* @author nmk
* @date 2019-08-29
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MarginServiceImpl implements MarginService {

    @Autowired
    private DeptServiceImpl deptService;
    @Autowired
    private ArchivesmouthsmanagementRepository archivesmouthsmanagementRepository;
    @Autowired
    private DictDetailRepository dictDetailRepository;
    @Autowired
    private DictRepository dictRepository;

    @PersistenceContext
    private EntityManager entityManager;

    //动态查询
    public Object query(MarginQueryCriteria criteria){
        //查询主语句
        String sql = "select sum(f.money) AS money , d.id , d.label from fund_margin f left join dict_detail d on d.id = f.tally_type_id ";
        Map<String,Object> map = new HashMap<>();
        //拼接收入项
        sql += " where f.type_id =:incomeId ";
        map.put("incomeId",criteria.getTypeId());
        //拼接查询
        if (criteria.getDeptId() != null){
            sql += " and f.dept_id = :deptId ";
            map.put("deptId",criteria.getDeptId());
        }
        //房屋ID
        if (criteria.getHouseId() != null){
            sql += " and f.archives_mouths_id = :houseId ";
            map.put("houseId",criteria.getHouseId());
        }
        //房屋ID
        if (criteria.getCreateTimeStart() != null & criteria.getCreateTimeEnd() != null){
            sql += " and trad_date between :createTimeStart and :createTimeEnd ";
            map.put("createTimeStart",criteria.getCreateTimeStart());
            map.put("createTimeEnd",criteria.getCreateTimeEnd());
        }
        //分组语句
        sql += " GROUP BY f.tally_type_id ";
        Query nativeQuery = entityManager.createNativeQuery(sql);
// 把参数传进去
        for (String key:map.keySet()) {
                nativeQuery.setParameter(key, map.get(key));
            }
        nativeQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> resultList = nativeQuery.getResultList();

        List<FundMarginDTO> fundMarginDTOS = new ArrayList();
        for (Map<String, Object> map1 :resultList
        ) {
            BigDecimal money = (BigDecimal) map1.get("money");
            Long id = Long.valueOf(String.valueOf(map1.get("id")));
            String label = (String) map1.get("label");
            FundMarginDTO dto = new FundMarginDTO(money,id,label);
            fundMarginDTOS.add(dto);
        }
        return fundMarginDTOS;
    }

    @Override
    public Object queryAll(MarginQueryCriteria criteria){
        List<DictDetail> list = dictDetailRepository.findAllByDictId(dictRepository.findByName("trade_type").getId());
        Long incomeId = null ;
        //收入ID
        for (DictDetail dictDetail : list) {
            if ("收入".equals(dictDetail.getLabel())){
                incomeId = dictDetail.getId();
            }
        }
        criteria.setTypeId(incomeId);
        return this.query(criteria);
    }

    @Override
    public Object queryCostAll(MarginQueryCriteria criteria){
        List<DictDetail> list = dictDetailRepository.findAllByDictId(dictRepository.findByName("trade_type").getId());
        Long incomeId = null ;
        //收入ID
        for (DictDetail dictDetail : list) {
            if ("支出".equals(dictDetail.getLabel())){
                incomeId = dictDetail.getId();
            }
        }
        criteria.setTypeId(incomeId);
        return this.query(criteria);
    }
    @Override
    public List<TreeDTO> buildTree(MarginQueryCriteria criteria) {
        List<TreeDTO> trees = new ArrayList<>();
        List<DeptDTO> deptDTOS;
        if (criteria.getDeptId() != 1) {
            DeptQueryCriteria criteria1 = new DeptQueryCriteria();
            criteria1.setId(criteria.getDeptId());
            deptDTOS = deptService.queryAll(criteria1);
        }else {
            deptDTOS = deptService.queryAll(new DeptQueryCriteria());
        }
        for (DeptDTO deptDTO : deptDTOS) {
            List<ArchiveTreeDto> list = archivesmouthsmanagementRepository.queryByDeptAndId(deptDTO.getId());
            trees.add(new TreeDTO(deptDTO.getId(),deptDTO.getName(),list));
        }
        return trees;
    }

}

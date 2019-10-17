package me.zhengjie.modules.business.repository;

import me.zhengjie.modules.business.domain.ReceiptPaymentAccount;
import me.zhengjie.modules.business.service.dto.ReceiptPaymentAccountSmallDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
/**
* @author kang
* @date 2019-08-21
*/
public interface ReceiptPaymentAccountRepository extends JpaRepository<ReceiptPaymentAccount, Long>, JpaSpecificationExecutor {
//    @Query(value = "select * from receipt_payment_account where id = ?1",nativeQuery = true)
//    ReceiptPaymentAccount findAllById(Long id);

//    List<ReceiptPaymentAccount> findByDeptId(Long deptId);

    //根据账户详情id查询
    List<ReceiptPaymentAccount> findByDetailId(Long maintarinDetailId);

    //根据部门查询总额 1级金额查询
    @Query(value = "select sum(remaining) from receipt_payment_account r LEFT JOIN fund_maintarin_detail md ON md.id = r.detail_id where dept_id = ? ",nativeQuery = true)
    BigDecimal findByMaintain(Long deptId);

    //根据支付方式id和部门id查询账户
    @Query(value = "select new me.zhengjie.modules.business.service.dto.ReceiptPaymentAccountSmallDTO(r.id,r.name) from ReceiptPaymentAccount r left join MaintarinDetail f on r.detailId=f.id left join DictDetail d on f.tradType.id=d.id where f.tradType.id =?1 and f.dept.id=?2")
    List<ReceiptPaymentAccountSmallDTO> findByTradTypeIdAndDeptId(Long TradTypeId, Long deptId);


}

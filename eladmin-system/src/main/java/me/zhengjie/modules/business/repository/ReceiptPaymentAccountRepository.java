package me.zhengjie.modules.business.repository;

import me.zhengjie.modules.business.domain.ReceiptPaymentAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Set;
/**
* @author kang
* @date 2019-08-21
*/
public interface ReceiptPaymentAccountRepository extends JpaRepository<ReceiptPaymentAccount, Long>, JpaSpecificationExecutor {
    @Query(value = "select * from receipt_payment_account where id = ?1",nativeQuery = true)
    ReceiptPaymentAccount findAllById(Long id);

    List<ReceiptPaymentAccount> findByDeptId(Long deptId);

}

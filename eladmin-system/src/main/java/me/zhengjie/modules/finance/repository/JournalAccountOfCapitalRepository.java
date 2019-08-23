package me.zhengjie.modules.finance.repository;

import me.zhengjie.modules.finance.domain.JournalAccountOfCapital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author nmk
* @date 2019-08-22
*/
public interface JournalAccountOfCapitalRepository extends JpaRepository<JournalAccountOfCapital, Integer>, JpaSpecificationExecutor {
}
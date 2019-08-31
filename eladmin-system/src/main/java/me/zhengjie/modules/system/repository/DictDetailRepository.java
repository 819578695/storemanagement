package me.zhengjie.modules.system.repository;

import jdk.internal.org.objectweb.asm.tree.analysis.Value;
import me.zhengjie.modules.system.domain.DictDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import sun.awt.SunHints;

/**
* @author Zheng Jie
* @date 2019-04-10
*/
public interface DictDetailRepository extends JpaRepository<DictDetail, Long>, JpaSpecificationExecutor {

    //根据value
    DictDetail findByDictIdAndValue(Long id,String value);

}

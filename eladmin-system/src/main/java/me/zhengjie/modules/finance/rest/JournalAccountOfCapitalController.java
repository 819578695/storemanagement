package me.zhengjie.modules.finance.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.finance.domain.JournalAccountOfCapital;
import me.zhengjie.modules.finance.service.JournalAccountOfCapitalService;
import me.zhengjie.modules.finance.service.dto.JournalAccountOfCapitalQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

/**
* @author nmk
* @date 2019-08-22
*/
@Api(tags = "JournalAccountOfCapital管理")
@RestController
@RequestMapping("api")
public class JournalAccountOfCapitalController {

    @Autowired
    private JournalAccountOfCapitalService journalAccountOfCapitalService;

    @Log("查询JournalAccountOfCapital")
    @ApiOperation(value = "查询JournalAccountOfCapital")
    @GetMapping(value = "/journalAccountOfCapital")
    @PreAuthorize("hasAnyRole('ADMIN','JOURNALACCOUNTOFCAPITAL_ALL','JOURNALACCOUNTOFCAPITAL_SELECT')")
    public ResponseEntity getJournalAccountOfCapitals(JournalAccountOfCapitalQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(journalAccountOfCapitalService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增JournalAccountOfCapital")
    @ApiOperation(value = "新增JournalAccountOfCapital")
    @PostMapping(value = "/journalAccountOfCapital")
    @PreAuthorize("hasAnyRole('ADMIN','JOURNALACCOUNTOFCAPITAL_ALL','JOURNALACCOUNTOFCAPITAL_CREATE')")
    public ResponseEntity create(@Validated @RequestBody JournalAccountOfCapital resources){
        return new ResponseEntity(journalAccountOfCapitalService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改JournalAccountOfCapital")
    @ApiOperation(value = "修改JournalAccountOfCapital")
    @PutMapping(value = "/journalAccountOfCapital")
    @PreAuthorize("hasAnyRole('ADMIN','JOURNALACCOUNTOFCAPITAL_ALL','JOURNALACCOUNTOFCAPITAL_EDIT')")
    public ResponseEntity update(@Validated @RequestBody JournalAccountOfCapital resources){
        journalAccountOfCapitalService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除JournalAccountOfCapital")
    @ApiOperation(value = "删除JournalAccountOfCapital")
    @DeleteMapping(value = "/journalAccountOfCapital/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','JOURNALACCOUNTOFCAPITAL_ALL','JOURNALACCOUNTOFCAPITAL_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        journalAccountOfCapitalService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}

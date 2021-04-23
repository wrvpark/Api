package com.wrvpark.apiserver.controllers;



import com.wrvpark.apiserver.dto.AuditLogDTO;
import com.wrvpark.apiserver.dto.SearchDTO;
import com.wrvpark.apiserver.service.AuditLogService;
import com.wrvpark.apiserver.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


/**
 * @author Isabel Ke
 * Original date:2020-04-02
 *
 * Description:audit log controller
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/log")
public class AuditLogController {

    @Autowired
    private AuditLogService auditLogService;

    /**
     * get all logs
     * @return a list of logs, or empty data if no logs.
     */
    @GetMapping(value = "")
    public ResultEntity<List<AuditLogDTO>> getAllLogs(){
       return auditLogService.getAllLog();
    }

    /**
     * search logs
     * @param name keyword
     * @param subId sub-category id
     * @param startTime logs created after this time
     * @param endTime logs created before this time
     * @param uId the creator id of this log
     * @return a list of matched logs, or empty data if no logs
     */
    @GetMapping("/search")
    public ResultEntity<List<AuditLogDTO>> searchLogs(@RequestParam String name,
                                                      @RequestParam String subId,
                                                      @RequestParam String startTime,
                                                      @RequestParam  String endTime,
                                                      @RequestParam String uId){
        SearchDTO parameters=new SearchDTO(name,subId,startTime,endTime,uId);
        return auditLogService.searchLog(parameters);
    }
}

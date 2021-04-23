package com.wrvpark.apiserver.service;


import com.wrvpark.apiserver.dto.AuditLogDTO;
import com.wrvpark.apiserver.dto.SearchDTO;
import com.wrvpark.apiserver.util.ResultEntity;

import java.util.List;

/**
 * @author Isabel Ke
 * @author Vahid Haghighat
 * Original date:2020-04-02
 *
 * Description:audit log service class that handles all the audit log logic
 */
public interface AuditLogService {
    //get all logs
    ResultEntity<List<AuditLogDTO>> getAllLog();
    //search log by certain conditions
    ResultEntity<List<AuditLogDTO>> searchLog(SearchDTO dto);
}

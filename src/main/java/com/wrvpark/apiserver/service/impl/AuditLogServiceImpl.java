package com.wrvpark.apiserver.service.impl;

import com.wrvpark.apiserver.domain.NonParkDocumentLog;
import com.wrvpark.apiserver.dto.AuditLogDTO;
import com.wrvpark.apiserver.dto.SearchDTO;
import com.wrvpark.apiserver.repository.AuditLogRepository;
import com.wrvpark.apiserver.repository.CategoryRepository;
import com.wrvpark.apiserver.repository.NonParkDocumentLogRepository;
import com.wrvpark.apiserver.repository.UserRepository;
import com.wrvpark.apiserver.service.AuditLogService;
import com.wrvpark.apiserver.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Isabel Ke
 */
@Service
public class AuditLogServiceImpl implements AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private NonParkDocumentLogRepository nonParkDocumentLogRepository;

    @Autowired
    private UserRepository userRepository;
    @Override
    public ResultEntity<List<AuditLogDTO>> getAllLog() {
        //get all the logs for park document
        List<NonParkDocumentLog> logs=nonParkDocumentLogRepository.findAll(Sort.by(Sort.Direction.DESC, "createTime"));
       //check if there is any logs
        if(logs.size()==0)
        {
            return ResultEntity.successWithOutData("No Logs!");
        }
        //convert it to dto
        List<AuditLogDTO> dtos=new ArrayList<>();
        for (NonParkDocumentLog log:logs) {
            AuditLogDTO dto=new AuditLogDTO(log);
            dtos.add(dto);
        }
        return ResultEntity.successWithData(dtos,"Get all logs");
    }

    @Override
    public ResultEntity<List<AuditLogDTO>> searchLog(SearchDTO searchParam) {
        String sql=createSql(searchParam);
        List<AuditLogDTO> auditLogDTOS=create(sql);
        if(auditLogDTOS.size()==0)
        {
            return ResultEntity.successWithOutData("No matched logs");
        }
        return ResultEntity.successWithData(auditLogDTOS,"Found matched logs!");
    }

    //query
    private  List<AuditLogDTO> create(String sql)
    {
        return null;
        //store the return data
//        List<AuditLogDTO> resultList = new ArrayList<>();
//        DataSource dataSource= DataSourceConfig.getDataSource();
//        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//        jdbcTemplate.query(sql, (ResultSet resultSet)->
//        {
//
//            NonParkDocumentLog log=new NonParkDocumentLog(
//                    resultSet.getString("id"),
//                    resultSet.getString("item_id"),
//                    resultSet.getString("action"),
//                    resultSet.getDate("create_time"),
//                    resultSet.getString("description"),
//                    resultSet.getString("reason")
//            );
//
//            Category category=categoryRepository.findById(resultSet.getString("category_ID")).get();
//            log.setCategory(category);
//            //set the creator
//            User modifier=userRepository.findById(resultSet.getString("modifier_id")).get();
//           log.setModifier(modifier);
//           AuditLogDTO auditLogDTO=new AuditLogDTO(log);
//            resultList.add(auditLogDTO);
//        });
//        return resultList;
    }
    //create the query
    private String createSql(SearchDTO searchParam) {
        String param = searchParam.getName();
        String startTime = searchParam.getStartTime();
        String endTime = searchParam.getEndTime();
        String uId = searchParam.getUId();
        String subId= searchParam.getSubId();
        boolean hasWhere=false;

        //List<NonParkDocumentLog> logs=nonParkDocumentLogRepository
        String sqlBase="select * from rvpark_nonparkdocumentlog";
        StringBuilder sql = new StringBuilder(sqlBase);

        //search by reason or description
        if (!param.isEmpty()) {
            hasWhere = appendWhereIfNeed(sql, hasWhere);
            sql.append("(reason LIKE '%" + param + "%'");
            sql.append(" OR description LIKE '%" + param + "%')");
        }
        //search by time
        if(!startTime.isEmpty())
        {
            hasWhere = appendWhereIfNeed(sql, hasWhere);
            if(endTime.isEmpty()) {
                sql.append("create_time>" +"'"+startTime+"'");
            }
            else
            {
                sql.append("create_time BETWEEN " + "'"+startTime+"'" + " AND "+"'"+endTime+"'");
            }
        }
        //search by uer
        if(!uId.isEmpty())
        {
            hasWhere = appendWhereIfNeed(sql, hasWhere);
            sql.append("modifier_id=" + "'"+uId+"'");
        }

        //search by category
        if(!subId.isEmpty())
        {
            hasWhere = appendWhereIfNeed(sql, hasWhere);
            sql.append("category_ID=" + "'"+subId+"'");
        }
        System.out.println(sql+"------------------sql languages");
        return sql.toString();
    }

    /**
     * decide where to add WHERE or AND to the sql
     * @param sql
     * @param hasWhere
     * @return
     */
    private static boolean appendWhereIfNeed(StringBuilder sql,boolean hasWhere)
    {
        if(!hasWhere)
        {
            sql.append(" WHERE ");
            hasWhere=true;
        }
        else {
            sql.append(" AND ");
        }
        return hasWhere;
    }
}

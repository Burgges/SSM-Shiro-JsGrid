package com.hand.service.impl;

import com.hand.dao.LogMapper;
import com.hand.dto.LogDto;
import com.hand.model.Log;
import com.hand.service.LogService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by huiyu.chen on 2017/7/17.
 *
 */
@Service
public class LogServiceImpl implements LogService {

    private static final Logger LOGGER = Logger.getLogger(LogServiceImpl.class);

    @Resource
    private LogMapper logMapper;

    /**
     * query log list by condition
     * @param params condition
     * @return result list
     */
    @Override
    public List<LogDto> queryLogs(Map<String, Object> params) {
        String timeStart = params.get("operationTimeStart") == null ? "" :
                params.get("operationTimeStart").toString()+":00";
        String timeEnd = params.get("operationTimeEnd") == null ? "" :
                params.get("operationTimeEnd").toString()+":00";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = null;
        Date endDate = null;
        if(!"".equals(timeStart)){
            try{
                startDate = simpleDateFormat.parse(timeStart);
            }catch(Exception e){
                LOGGER.info(e);
            }
        }
        if(!"".equals(timeEnd)){
            try{
                endDate = simpleDateFormat.parse(timeEnd);
            }catch(Exception e){
                LOGGER.info(e);
            }
        }
        params.put("operationTimeStart",startDate);
        params.put("operationTimeEnd",endDate);
        return logMapper.queryLogs(params);
    }

    /**
     * delete log by id
     * @param logId condition
     */
    @Override
    public void delete(Integer logId) {
        logMapper.delete(logId);
    }

    /**
     * save log
     * @param log operation log
     * @return result
     */
    @Override
    public Log save(Log log) {
        if (log == null) {
            return null;
        }
        logMapper.save(log);
        return log;
    }
}

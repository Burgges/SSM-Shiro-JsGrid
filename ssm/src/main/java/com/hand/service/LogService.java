package com.hand.service;

import com.hand.dto.LogDto;
import com.hand.model.Log;

import java.util.List;
import java.util.Map;

/**
 * Created by huiyu.chen on 2017/7/17.
 *
 */
public interface LogService {

    /**
     * query log by params
     * @param params condition
     * @return result
     */
    List<LogDto> queryLogs(Map<String, Object> params);

    /**
     * delete log by logId
     * @param logId condition
     */
    void delete(Integer logId);

    /**
     * save log to database
     * @param log operation log
     * @return result
     */
    Log save(Log log);

}

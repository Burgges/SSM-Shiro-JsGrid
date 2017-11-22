package com.hand.controller;

import com.hand.annotation.OperationLog;
import com.hand.dto.JsGridResult;
import com.hand.dto.LogDto;
import com.hand.service.LogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by huiyu.chen on 2017/7/17.
 *
 */
@Controller
public class LogController {

    @Resource
    private LogService logService;

    /**
     * Into logs page
     * @return result page
     */
    @RequestMapping(value = "/api/logs/page")
    public String toLog () {
        return "logs";
    }

    /**
     * query log list
     * @param params condition
     * @return result list
     */
    @OperationLog("Query log list")
    @RequestMapping(value = "/api/logs", method = RequestMethod.GET)
    @ResponseBody
    public List<LogDto> queryLogs (@RequestParam Map<String, Object> params) {
        JsGridResult<LogDto> jsGridResult = new JsGridResult<>();
        List<LogDto> logDtoList = logService.queryLogs(params);
        jsGridResult.setData(logDtoList);
        jsGridResult.setItemsCount(Integer.toString(logDtoList.size()));
        return logDtoList;
    }

    /**
     * delete log by id
     * @param logId condition
     * @return result
     */
    @OperationLog("Delete log")
    @RequestMapping(value = "/api/logs/{logId}", method = RequestMethod.DELETE)
    @ResponseBody
    public int deleteLog (@PathVariable Integer logId) {
        logService.delete(logId);
        return 1;
    }

}

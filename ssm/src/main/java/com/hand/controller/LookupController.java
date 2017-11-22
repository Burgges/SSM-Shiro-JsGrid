package com.hand.controller;

import com.hand.annotation.OperationLog;
import com.hand.dto.JsGridResult;
import com.hand.dto.MessageDto;
import com.hand.model.Lookup;
import com.hand.service.LookupService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by huiyu.chen on 2017/7/20.
 *
 */
@Controller
public class LookupController {

    @Resource
    private LookupService lookupService;

    @RequestMapping(value = "/api/toLookup")
    public String toLookup () {
        return "lookup";
    }

    /**
     *  Select lookup list
     * @param params condition
     * @return lookup list
     */
    @OperationLog("Query lookup list")
    @RequestMapping(value = "/api/lookups", method = RequestMethod.GET)
    @ResponseBody
    public List<Lookup> selectLookup (@RequestParam Map<String, Object> params) {

        JsGridResult<Lookup> jsGridResult = new JsGridResult<>();
        List<Lookup> lookupList = lookupService.selectLookup(params);
        if (lookupList != null) {
            jsGridResult.setItemsCount(lookupList.size()+"");
        }
        jsGridResult.setData(lookupList);
        return lookupList;
    }

    /**
     * Add lookup
     * @param lookup new lookup
     * @param request http request
     * @return return info
     */
    @OperationLog("Add lookup")
    @RequestMapping(value = "/api/lookups", method = RequestMethod.POST)
    @ResponseBody
    public MessageDto<Lookup> save (Lookup lookup, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Integer uId = (Integer)(session.getAttribute("UID") == null ? 0: session.getAttribute("UID"));
        lookup.setCreatedBy(uId);
        lookup.setLastUpdatedBy(uId);
        return lookupService.save(lookup);
    }

    /**
     * Update lookup by id
     * @param params new lookup
     * @param id lookup primary key
     * @param request http request
     * @return return info
     */
    @OperationLog("Update lookup")
    @RequestMapping(value = "/api/lookups/{id}", method = RequestMethod.POST)
    @ResponseBody
    public MessageDto<Lookup> update (@RequestParam Map<String, Object> params,
                                    @PathVariable Integer id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String lookupType = params.get("lookupType") == null ? "" : params.get("lookupType").toString();
        String lookupDescription = params.get("lookupDescription") == null ? "" :
                params.get("lookupDescription").toString();
        String lookupCode = params.get("lookupCode") == null ? "" : params.get("lookupCode").toString();
        String meaning = params.get("meaning") == null ? "" : params.get("meaning").toString();
        Boolean enabledFlag = false;
        if(!"false".equals(params.get("enabledFlag"))){
            enabledFlag = true;
        }
        Lookup lookup = new Lookup();
        Integer uId = (Integer)(session.getAttribute("UID") == null ? 0: session.getAttribute("UID"));
        lookup.setId(id);
        lookup.setLookupCode(lookupCode);
        lookup.setLookupType(lookupType);
        lookup.setLastUpdatedBy(uId);
        lookup.setLookupDescription(lookupDescription);
        lookup.setMeaning(meaning);
        lookup.setEnabledFlag(enabledFlag);
        return lookupService.update(lookup);
    }

    /**
     * Delete lookup by id
     * @param id primary key of id
     * @return return info
     */
    @OperationLog("Delete lookup")
    @RequestMapping(value = "/api/lookups/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public int delete (@PathVariable Integer id) {
        lookupService.delete(id);
        return 1;
    }
}

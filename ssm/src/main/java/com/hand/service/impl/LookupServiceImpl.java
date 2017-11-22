package com.hand.service.impl;

import com.hand.dao.LookupMapper;
import com.hand.dto.MessageDto;
import com.hand.model.Lookup;
import com.hand.service.LookupService;
import com.hand.util.MessageUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by huiyu.chen on 2017/7/19.
 *
 */
@Service
public class LookupServiceImpl implements LookupService {

    private static final Logger LOGGER = Logger.getLogger(LookupServiceImpl.class);

    @Resource
    private LookupMapper lookupMapper;

    private MessageUtil<Lookup> messageUtil = new MessageUtil<>();

    /**
     * Save lookup
     * @param lookup new lookup
     * @return rusult info
     */
    @Override
    public MessageDto<Lookup> save(Lookup lookup) {
        Date date = new Date();
        lookup.setCreationDate(date);
        lookup.setLastUpdateDate(date);
        String message = "Insert success";
        if(lookup.getLookupType() == null || "".equals(lookup.getLookupType())){
            message = "Lookup type is not null";
            LOGGER.error(message+": {}",new Exception(message));
            return messageUtil.setMessageDto(500,message, lookup);
        }
        if(lookup.getLookupCode() == null || "".equals(lookup.getLookupCode())){
            message = "Lookup code is not null";
            LOGGER.error(message+": {}",new Exception(message));
            return messageUtil.setMessageDto(500,message, lookup);
        }
        if (lookupMapper.getLookupByCode(lookup.getLookupType(),lookup.getLookupCode()) != null) {
            message = "Lookup was existed";
            LOGGER.error(message+": {}",new Exception(message));
            return messageUtil.setMessageDto(500,message, lookup);
        }
        lookupMapper.save(lookup);
        return messageUtil.setMessageDto(200,message, lookupMapper.getLookupByCode(lookup.getLookupType(),lookup.getLookupCode()));
    }

    /**
     * Delete lookup by id
     * @param id condition of id
     * @return rusult info
     */
    @Override
    public MessageDto<Lookup> delete(Integer id) {
        String message = "Delete success";
        if (lookupMapper.getLookupById(id) == null) {
            message = "Lookup not found";
            LOGGER.error(message+": {}",new Exception(message));
            return messageUtil.setMessageDto(500,message, null);
        }
        lookupMapper.delete(id);
        return messageUtil.setMessageDto(200,message, null);
    }

    /**
     * Update lookup by id
     * @param lookup new lookup
     * @return rusult info
     */
    @Override
    public MessageDto<Lookup> update(Lookup lookup) {
        Date date = new Date();
        String message = "Update success";
        if(lookup.getLookupType() == null || "".equals(lookup.getLookupType())){
            message = "Lookup type is not null";
            LOGGER.error(message+": {}",new Exception(message));
            return messageUtil.setMessageDto(500,message, lookup);
        }
        if(lookup.getLookupCode() == null || "".equals(lookup.getLookupCode())){
            message = "Lookup code is not null";
            LOGGER.error(message+": {}",new Exception(message));
            return messageUtil.setMessageDto(500,message, lookup);
        }
        Lookup lookup1 = lookupMapper.getLookupById(lookup.getId());
        if (lookup1 == null) {
            message = "Lookup not found";
            LOGGER.error(message+": {}",new Exception(message));
            return messageUtil.setMessageDto(500,message, lookup);
        }
        Lookup lookup2 = lookupMapper.getLookupByCode(lookup.getLookupType(),lookup.getLookupCode());
        if (lookup2 != null && !lookup1.getId().equals(lookup2.getId())) {
            message = "Lookup was existed";
            LOGGER.error(message+": {}",new Exception(message));
            return messageUtil.setMessageDto(500,message, lookup);
        }
        lookup.setLastUpdateDate(date);
        lookupMapper.update(lookup);
        return messageUtil.setMessageDto(200,message, lookup);
    }

    /**
     * Get lookup by primary key
     * @param id condition of id
     * @return lookup
     */
    @Override
    public Lookup getLookupById(Integer id) {
        return lookupMapper.getLookupById(id);
    }

    /**
     *  Select lookup by type
     * @param lookupType condition of lookupType
     * @return return info
     */
    @Override
    public List<Lookup> selectLookupByType(String lookupType) {
        return lookupMapper.selectLookupByType(lookupType);
    }

    /**
     *  Select lookup list
     * @param params condition of params
     * @return lookup list
     */
    @Override
    public List<Lookup> selectLookup(Map<String, Object> params) {
        return lookupMapper.selectLookup(params);
    }

    /**
     * Get menu by user id
     * @param userId current user
     * @param lookupType lookup type
     * @return lookup
     */
    @Override
    public Lookup getLookupByUserId(Integer userId, String lookupType) {
        return lookupMapper.getLookupByUserId(userId, lookupType);
    }
}

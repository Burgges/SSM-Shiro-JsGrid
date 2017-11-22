package com.hand.service;

import com.hand.dto.MessageDto;
import com.hand.model.Lookup;

import java.util.List;
import java.util.Map;

/**
 * Created by huiyu.chen on 2017/7/18.
 *
 */
public interface LookupService {

    /**
     * Insert lookup
     * @param lookup new lookup
     * @return return info
     */
    MessageDto<Lookup> save(Lookup lookup);

    /**
     * Delete lookup by id
     * @param id condition of id
     * @return return info
     */
    MessageDto<Lookup> delete(Integer id);

    /**
     * Update lookup
     * @param lookup new lookup
     * @return return info
     */
    MessageDto<Lookup> update(Lookup lookup);

    /**
     * Get one lookup by lookup id
     * @param id condition of id
     * @return result role
     */
    Lookup getLookupById(Integer id);

    /**
     * select lookup by id
     * @param lookupType condition of lookupType
     * @return result Lookup
     */
    List<Lookup> selectLookupByType(String lookupType);

    /**
     * Select lookup list
     * @param params condition of params
     * @return result Lookup list
     */
    List<Lookup> selectLookup(Map<String, Object> params);

    /**
     * Get user menu by userId
     * @param userId current user
     * @param lookupType lookup type
     * @return menu info
     */
    Lookup getLookupByUserId(Integer userId, String lookupType);
}

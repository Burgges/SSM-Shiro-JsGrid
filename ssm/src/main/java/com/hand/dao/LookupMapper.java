package com.hand.dao;

import com.hand.model.Lookup;

import java.util.List;
import java.util.Map;

/**
 * Created by huiyu.chen on 2017/7/18.
 *
 */
public interface LookupMapper {

    /**
     * Insert lookup
     * @param lookup new lookup
     */
    void save(Lookup lookup);

    /**
     * Delete lookup by id
     * @param id condition of id
     */
    void delete(Integer id);

    /**
     * Update lookup
     * @param lookup new lookup
     */
    void update(Lookup lookup);

    /**
     * Get one lookup by lookup id
     * @param id condition of id
     * @return result lookup
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
     * Select lookup by code and type
     * @param lookupType condition of type
     * @param lookupCode condition of code
     * @return return lookup
     */
    Lookup getLookupByCode(String lookupType, String lookupCode);

    /**
     * Get user menu by userId
     * @param userId current user
     * @param lookupType lookup type
     * @return menu info
     */
    Lookup getLookupByUserId(Integer userId, String lookupType);


}

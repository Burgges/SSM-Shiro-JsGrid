package com.hand.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * 常量维护类
 * Created by huiyu.chen on 2017/7/19.
 *
 */
public class Lookup extends BaseModel {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull(message = "Lookup type is not null")
    private String lookupType;

    @NotNull(message = "Lookup code is not null")
//    @Column(unique = true)
    private String lookupCode;

    private String lookupDescription;

    private String meaning;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLookupType() {
        return lookupType;
    }

    public void setLookupType(String lookupType) {
        this.lookupType = lookupType;
    }

    public String getLookupCode() {
        return lookupCode;
    }

    public void setLookupCode(String lookupCode) {
        this.lookupCode = lookupCode;
    }

    public String getLookupDescription() {
        return lookupDescription;
    }

    public void setLookupDescription(String lookupDescription) {
        this.lookupDescription = lookupDescription;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }
}

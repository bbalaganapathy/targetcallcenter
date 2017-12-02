package com.bala.model;

import com.bala.validator.ValidateRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import cz.jirutka.validator.collection.constraints.EachPattern;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 11/30/2017.
 */
@ValidateRequest
public class CallCenterRequest {

    @Valid
    @NotNull
    @JsonProperty("number_of_calls")
    private Integer noOfCalls;

    @NotNull(message = "{je.notnull.message}")
    @Valid
    @EachPattern(regexp="^[0-9]+(,[0-9]+)*$",message = "{je.pattern.message}")
    private List<String> je  = new ArrayList<>();

    @NotNull(message = "{se.notnull.message}")
    @Valid
    @EachPattern(regexp="^[0-9]+(,[0-9]+)*$",message ="{se.pattern.message}")
    private List<String> se  = new ArrayList<>();

    @NotNull(message = "{mgr.notnull.message}")
    @Valid
    @Pattern(regexp="^[0-9]+(,[0-9]+)*$",message="{mgr.pattern.message}")
    private String  mgr;

    public int getNoOfCalls() {
        return noOfCalls;
    }

    public void setNoOfCalls(Integer noOfCalls) {
        this.noOfCalls = noOfCalls;
    }

    public List<String> getJe() {
        return je;
    }

    public void setJe(List<String> je) {
        this.je = je;
    }

    public List<String> getSe() {
        return se;
    }

    public void setSe(List<String> se) {
        this.se = se;
    }

    public String getMgr() {
        return mgr;
    }

    public void setMgr(String mgr) {
        this.mgr = mgr;
    }
}


package com.bala.model;

import com.bala.validator.IsJEGreaterThanSEAndMgr;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 11/30/2017.
 */
@IsJEGreaterThanSEAndMgr
public class CallCenterRequest {

    @JsonProperty("number_of_calls")
    @NotNull(message ="Number of calls cannot be null")
    private Integer noOfCalls;

    @NotNull
    private String[] je  = new String[]{};

    @NotNull
    private String[] se  = new String[]{};

    @NotNull
    private String  mgr;

    public int getNoOfCalls() {
        return noOfCalls;
    }

    public void setNoOfCalls(Integer noOfCalls) {
        this.noOfCalls = noOfCalls;
    }

    public String[] getJe() {
        return je;
    }

    public void setJe(String[] je) {
        this.je = je;
    }

    public String[] getSe() {
        return se;
    }

    public void setSe(String[] se) {
        this.se = se;
    }

    public String getMgr() {
        return mgr;
    }

    public void setMgr(String mgr) {
        this.mgr = mgr;
    }
}


package com.bala.model;

import com.bala.validator.ValidateRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import cz.jirutka.validator.collection.constraints.EachMin;
import cz.jirutka.validator.collection.constraints.EachPattern;
import cz.jirutka.validator.collection.constraints.EachRange;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 12/3/2017
 */
@ValidateRequest
public class CallCenterRequest {

    @Valid
    @NotNull
    @JsonProperty("number_of_calls")
    @Min(0)
    private Integer noOfCalls;

    @NotNull(message = "{je.notnull.message}")
    @Valid
    private List<List<Integer>> je  = new ArrayList<>();

    @NotNull(message = "{se.notnull.message}")
    @Valid
    private List<List<Integer>> se  = new ArrayList<>();

    @Valid
    @NotNull(message = "{mgr.notnull.message}")
    @EachMin(0)
    private List<Integer>  mgr;

    public int getNoOfCalls() {
        return noOfCalls;
    }

    public void setNoOfCalls(Integer noOfCalls) {
        this.noOfCalls = noOfCalls;
    }

    public List<List<Integer>> getJe() {
        return je;
    }

    public void setJe(List<List<Integer>> je) {
        this.je = je;
    }

    public List<List<Integer>> getSe() {
        return se;
    }

    public void setSe(List<List<Integer>> se) {
        this.se = se;
    }

    public List<Integer> getMgr() {
        return mgr;
    }

    public void setMgr(List<Integer> mgr) {
        this.mgr = mgr;
    }
}


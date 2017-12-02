package com.bala.model;

/**
 * Created by hp on 12/2/2017.
 */
public class CallCenterResponse {
    Integer noOfCalls;
    Integer resolved;
    Integer unResolved;

    Long totalTimeTakeninMnts;

    public Integer getNoOfCalls() {
        return noOfCalls;
    }

    public void setNoOfCalls(Integer noOfCalls) {
        this.noOfCalls = noOfCalls;
    }

    public Integer getResolved() {
        return resolved;
    }

    public void setResolved(Integer resolved) {
        this.resolved = resolved;
    }

    public Integer getUnResolved() {
        return unResolved;
    }

    public void setUnResolved(Integer unResolved) {
        this.unResolved = unResolved;
    }

    public Long getTotalTimeTakeninMnts() {
        return totalTimeTakeninMnts;
    }

    public void setTotalTimeTakeninMnts(Long totalTimeTakeninMnts) {
        this.totalTimeTakeninMnts = totalTimeTakeninMnts;
    }

    public Performance getPerformance() {
        return performance;
    }

    public void setPerformance(Performance performance) {
        this.performance = performance;
    }

    Performance performance;


}

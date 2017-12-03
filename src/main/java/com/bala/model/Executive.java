package com.bala.model;

/**
 * Created by hp on 12/3/2017.
 */
public class Executive {

    String id;
    Long timeTakenInMinutes;
    Integer callsAttended;
    Integer resolved;
    Integer unresolved;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTimeTakenInMinutes() {
        return timeTakenInMinutes;
    }

    public void setTimeTakenInMinutes(Long timeTakenInMinutes) {
        this.timeTakenInMinutes = timeTakenInMinutes;
    }

    public Integer getCallsAttended() {
        return callsAttended;
    }

    public void setCallsAttended(Integer callsAttended) {
        this.callsAttended = callsAttended;
    }

    public Integer getResolved() {
        return resolved;
    }

    public void setResolved(Integer resolved) {
        this.resolved = resolved;
    }

    public Integer getUnresolved() {
        return unresolved;
    }

    public void setUnresolved(Integer unresolved) {
        this.unresolved = unresolved;
    }
}

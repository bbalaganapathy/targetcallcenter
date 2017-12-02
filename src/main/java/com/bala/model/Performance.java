package com.bala.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 12/2/2017.
 */
public class Performance {
    List<Executive> manager= new ArrayList<Executive>();

    List<Executive> juniorExecutives= new ArrayList<Executive>();

    List<Executive> seniorExecutives= new ArrayList<Executive>();

    public List<Executive> getManager() {
        return manager;
    }

    public void setManager(List<Executive> manager) {
        this.manager = manager;
    }

    public List<Executive> getJuniorExecutives() {
        return juniorExecutives;
    }

    public void setJuniorExecutives(List<Executive> juniorExecutives) {
        this.juniorExecutives = juniorExecutives;
    }

    public List<Executive> getSeniorExecutives() {
        return seniorExecutives;
    }

    public void setSeniorExecutives(List<Executive> seniorExecutives) {
        this.seniorExecutives = seniorExecutives;
    }
}

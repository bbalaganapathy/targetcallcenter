package com.bala.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 12/3/2017.
 */
public class Performance {
    Executive manager;

    List<Executive> juniorExecutives= new ArrayList<Executive>();

    public Executive getManager() {
        return manager;
    }

    public void setManager(Executive manager) {
        this.manager = manager;
    }

    List<Executive> seniorExecutives= new ArrayList<Executive>();


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

    public void addJuniorExecutive(Executive executive){
        this.juniorExecutives.add(executive);
    }
    public void addSeniorExecutive(Executive executive){
        this.seniorExecutives.add(executive);
    }
}

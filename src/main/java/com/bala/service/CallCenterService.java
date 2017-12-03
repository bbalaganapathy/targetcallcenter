package com.bala.service;

import com.bala.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by hp on 12/3/2017.
 */
@Service
public class CallCenterService {

    @Autowired
    private Environment env;

    public CallCenterResponse calculatePerformance(CallCenterRequest req){
       CallCenterResponse res= new CallCenterResponse();
       res.setNoOfCalls(req.getNoOfCalls());
       int unResolved = (int)Arrays.stream(req.getMgr().split(",")).filter(n -> Integer.parseInt(n)>Integer.parseInt(env.getProperty("manager.escalation.time"))).count();
       int resolved = req.getNoOfCalls()-unResolved;
        res.setResolved(resolved);
        res.setUnResolved(unResolved);
        res.setTotalTimeTakeninMnts(totalTimeTaken(req));
        List<Performance> performances = new ArrayList<>();
        res.setPerformance(findPerformance(req));
        return res;
    }

    private long totalTimeTaken(CallCenterRequest req){
        List<String> allExecutive = Stream.concat(req.getJe().stream(), req.getSe().stream()).collect(Collectors.toList());
        allExecutive.add(req.getMgr());
        String result2 = allExecutive
                .stream()
                .map(a->a.toString())
                .collect(Collectors.joining(","));
        int[] result1= Arrays.stream(result2.split(","))
                .mapToInt(Integer::valueOf)
                .toArray();
        long total = Arrays.stream(result1).sum();
        return total;
    }

    private Performance findPerformance(CallCenterRequest req){
        Performance performance= new Performance();
                Executive mgr= buildExecutive(req.getMgr(),Integer.parseInt(env.getProperty("manager.escalation.time")));
                mgr.setId(env.getProperty("manager.prefix"));
                performance.setManager(mgr);
                int i=0;
                for(String se:req.getSe()){
                    Executive senior= buildExecutive(se,Integer.parseInt(env.getProperty("senior.escalation.time")));
                    senior.setId(env.getProperty("senior.prefix")+i);
                    i++;
                    performance.addSeniorExecutive(senior);
                }
                int j=0;
                for(String je:req.getJe()) {
                    Executive junior = buildExecutive(je, Integer.parseInt(env.getProperty("junior.escalation.time")));
                    junior.setId(env.getProperty("junior.prefix") + j);
                    j++;
                    performance.addJuniorExecutive(junior);
                }
        return performance;
    }

    private Executive buildExecutive(String str,int escalation){
        Executive executive= new Executive();
        int[] result1= Arrays.stream(str.split(","))
                .mapToInt(Integer::valueOf)
                .toArray();
        long total = Arrays.stream(result1).sum();
        executive.setTimeTakenInMinutes(total);
        executive.setCallsAttended(str.split(",").length);
        executive.setResolved((int)Arrays.stream(str.split(",")).filter(n -> Integer.parseInt(n)<=escalation).count());
        executive.setUnresolved((int)Arrays.stream(str.split(",")).filter(n -> Integer.parseInt(n)>escalation).count());
        return executive;
    }

}

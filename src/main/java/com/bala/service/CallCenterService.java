package com.bala.service;

import com.bala.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
       int unResolved = 0;
     //   if(!StringUtils.isEmpty(req.getMgr())) unResolved= (int)Arrays.stream(req.getMgr().split(",")).filter(n -> Integer.parseInt(n)>Integer.parseInt(env.getProperty("manager.escalation.time"))).count();
       int resolved = req.getNoOfCalls()-unResolved;
        res.setResolved(resolved);
        res.setUnResolved(unResolved);
        res.setTotalTimeTakeninMnts(totalTimeTaken(req));
        List<Performance> performances = new ArrayList<>();
        res.setPerformance(findPerformance(req));
        return res;
    }

    private long totalTimeTaken(CallCenterRequest req){
      long jeTotal= req.getJe().stream().flatMap(l->l.stream()).collect(Collectors.toList()).stream().mapToInt(i->i).sum();
      long seTotal= req.getSe().stream().flatMap(l->l.stream()).collect(Collectors.toList()).stream().mapToInt(i->i).sum();
      long mgrTotal=  req.getMgr().stream().mapToInt(Integer::intValue).sum();
      return jeTotal+seTotal+mgrTotal;
    }

    private Performance findPerformance(CallCenterRequest req){
        Performance performance= new Performance();
        if(req.getMgr()!=null && req.getMgr().size()>0) {
            Executive mgr = buildExecutive(req.getMgr(), Integer.parseInt(env.getProperty("manager.escalation.time")));
            mgr.setId(env.getProperty("manager.prefix"));
            performance.setManager(mgr);
        }
            int i=0;
            for (List<Integer> se : req.getSe()) {
                if (se.isEmpty()) continue;
                Executive senior = buildExecutive(se, Integer.parseInt(env.getProperty("senior.escalation.time")));
                senior.setId(env.getProperty("senior.prefix") + i);
                i++;
                performance.addSeniorExecutive(senior);
            }
                int j=0;
                for(List<Integer> je:req.getJe()) {
                    Executive junior = buildExecutive(je, Integer.parseInt(env.getProperty("junior.escalation.time")));
                    junior.setId(env.getProperty("junior.prefix") + j);
                    j++;
                    performance.addJuniorExecutive(junior);
                }
        return performance;
    }

    private Executive buildExecutive(List<Integer> duration,int escalation){
        Executive executive= new Executive();
        long total= duration.stream().mapToInt(Integer::intValue).sum();
        executive.setTimeTakenInMinutes(total);
            executive.setCallsAttended(duration.size());
            executive.setResolved((int) duration.stream().filter(n -> n <= escalation).count());
            executive.setUnresolved((int) duration.stream().filter(n -> n >escalation).count());

        return executive;
    }

}

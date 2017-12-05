package resource;

import com.bala.CallCenterApplication;
import com.bala.model.CallCenterRequest;
import com.bala.model.CallCenterResponse;
import com.bala.model.Executive;
import com.bala.model.Performance;
import com.bala.resource.CallCenterResource;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CallCenterApplication.class)
@ActiveProfiles("test")
public class CallCenterResourceTest {
     @InjectMocks
    CallCenterResource controller;

    @Autowired
    WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void initTests() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }



    @Test
    public void shouldPostPerformance() throws Exception {
        CallCenterRequest r1 = mockCallCenterRequest("shouldPostPerformance");
        CallCenterResponse expectedResponse = new CallCenterResponse();
        expectedResponse.setTotalTimeTakeninMnts(4l);
        expectedResponse.setNoOfCalls(2);
        expectedResponse.setUnResolved(0);
        expectedResponse.setResolved(2);
        Performance exp=new Performance();
        Executive je = new Executive();
        je.setId("je0");
        je.setResolved(2);
        je.setCallsAttended(2);
        je.setUnresolved(0);
        je.setTimeTakenInMinutes(4l);
        exp.addJuniorExecutive(je);
        expectedResponse.setPerformance(exp);
        byte[] r1Json = toJson(r1);
        byte[] resJson = toJson(expectedResponse);
        MvcResult result = mvc.perform(post("/api/performance/")
                .content(r1Json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(new String(resJson))).andReturn();
        }

    @Test
    public void shouldThrowValidationError() throws Exception {
        CallCenterRequest r1 = mockCallCenterRequest("shouldThrowValidationError");
        byte[] r1Json = toJson(r1);
        MvcResult result = mvc.perform(post("/api/performance/")
                .content(r1Json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void shouldThrowValidationErrorForNegativeCalls() throws Exception {
        CallCenterRequest r1 = new CallCenterRequest();
        r1.setNoOfCalls(-10);
        byte[] r1Json = toJson(r1);
        MvcResult result = mvc.perform(post("/api/performance/")
                .content(r1Json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void shouldThrowValidationErrorForNegativeDuration() throws Exception {
        CallCenterRequest r1 = mockCallCenterRequest("shouldThrowValidationErrorForNegativeDuration");
        r1.setNoOfCalls(10);
        byte[] r1Json = toJson(r1);
        MvcResult result = mvc.perform(post("/api/performance/")
                .content(r1Json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void shouldPostPerformanceSE() throws Exception {
        CallCenterRequest r1 = mockCallCenterRequest1("shouldPostPerformanceSE");
        CallCenterResponse expectedResponse = new CallCenterResponse();
        expectedResponse.setTotalTimeTakeninMnts(17l);
        expectedResponse.setNoOfCalls(4);
        expectedResponse.setUnResolved(0);
        expectedResponse.setResolved(4);
        Performance exp=new Performance();
        Executive je = new Executive();
        je.setId("je0");
        je.setResolved(2);
        je.setCallsAttended(2);
        je.setUnresolved(0);
        je.setTimeTakenInMinutes(4l);
        exp.addJuniorExecutive(je);

        Executive je1 = new Executive();
        je1.setId("je1");
        je1.setResolved(1);
        je1.setCallsAttended(2);
        je1.setUnresolved(1);
        je1.setTimeTakenInMinutes(10l);
        exp.addJuniorExecutive(je1);

        Executive se = new Executive();
        se.setId("se0");
        se.setResolved(1);
        se.setCallsAttended(1);
        se.setUnresolved(0);
        se.setTimeTakenInMinutes(3l);
        exp.addSeniorExecutive(se);
        expectedResponse.setPerformance(exp);
        byte[] r1Json = toJson(r1);
        byte[] resJson = toJson(expectedResponse);
        MvcResult result = mvc.perform(post("/api/performance/")
                .content(r1Json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(new String(resJson))).andReturn();
    }

    @Test
    public void shouldPostPerformanceMgr() throws Exception {
        CallCenterRequest r1 = mockCallCenterRequest1("shouldPostPerformanceMgr");
        CallCenterResponse expectedResponse = new CallCenterResponse();
        expectedResponse.setTotalTimeTakeninMnts(35l);
        expectedResponse.setNoOfCalls(4);
        expectedResponse.setUnResolved(0);
        expectedResponse.setResolved(4);
        Performance exp=new Performance();
        Executive je = new Executive();
        je.setId("je0");
        je.setResolved(2);
        je.setCallsAttended(2);
        je.setUnresolved(0);
        je.setTimeTakenInMinutes(4l);
        exp.addJuniorExecutive(je);

        Executive je1 = new Executive();
        je1.setId("je1");
        je1.setResolved(1);
        je1.setCallsAttended(2);
        je1.setUnresolved(1);
        je1.setTimeTakenInMinutes(10l);
        exp.addJuniorExecutive(je1);

        Executive se = new Executive();
        se.setId("se0");
        se.setResolved(0);
        se.setCallsAttended(1);
        se.setUnresolved(1);
        se.setTimeTakenInMinutes(16l);
        exp.addSeniorExecutive(se);

        Executive mgr = new Executive();
        mgr.setId("mgr");
        mgr.setResolved(1);
        mgr.setCallsAttended(1);
        mgr.setUnresolved(0);
        mgr.setTimeTakenInMinutes(5l);
        exp.setManager(mgr);
        expectedResponse.setPerformance(exp);
        byte[] r1Json = toJson(r1);
        byte[] resJson = toJson(expectedResponse);
        MvcResult result = mvc.perform(post("/api/performance/")
                .content(r1Json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(new String(resJson))).andReturn();
    }


    private CallCenterRequest mockCallCenterRequest(String prefix) {
        CallCenterRequest r = new CallCenterRequest();
        if(prefix.equalsIgnoreCase("shouldThrowValidationError"))
            r.setNoOfCalls(4);
        else
            r.setNoOfCalls(2);
        List<List<Integer>> je= new ArrayList<>();
        List<Integer> je1=new ArrayList<>();
        if(prefix.equalsIgnoreCase("shouldThrowValidationErrorForNegativeDuration"))
            je1.add(-3);
        je1.add(3);je1.add(1);
        je.add(je1);
        r.setJe(je);
        List<List<Integer>> se= new ArrayList<>();

        r.setSe(se);
        r.setMgr(new ArrayList<Integer>());
        return r;
    }

    private CallCenterRequest mockCallCenterRequest1(String prefix) {
        CallCenterRequest r = new CallCenterRequest();
            r.setNoOfCalls(4);

        List<List<Integer>> je= new ArrayList<>();
        List<Integer> je1=new ArrayList<>();
        je1.add(3);je1.add(1);
        je.add(je1);
        List<Integer> je2=new ArrayList<>();
        je2.add(8);
        je2.add(2);
        je.add(je2);
        r.setJe(je);

        List<List<Integer>> se= new ArrayList<>();
        List<Integer> se1=new ArrayList<>();
        if(prefix.equalsIgnoreCase("shouldPostPerformanceMgr"))
           se1.add(16);
        else
            se1.add(3);
        se.add(se1);
        r.setSe(se);
        List<Integer> mgr=new ArrayList<>();
        if(prefix.equalsIgnoreCase("shouldPostPerformanceMgr"))
            mgr.add(5);
        r.setMgr(mgr);
        return r;
    }

    private byte[] toJson(Object r) throws Exception {
        ObjectMapper map = new ObjectMapper();
        return map.writeValueAsString(r).getBytes();
    }



}
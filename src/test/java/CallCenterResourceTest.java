import com.bala.model.CallCenterResponse;
import com.bala.resource.CallCenterResource;
import com.bala.service.CallCenterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Created by hp on 12/3/2017.
 */
    @RunWith(SpringRunner.class)
    @WebMvcTest(CallCenterResource.class)
    public class CallCenterResourceTest {
        @Autowired
        MockMvc mockMvc;
        @MockBean
        CallCenterService service;
        @Autowired
        ObjectMapper objectMapper;

        @Test
        public void testCreateClientSuccessfully() throws Exception {
       /*     given(service.calculatePerformance()).willReturn(new CallCenterResponse());
            mockMvc.perform(post("/clients")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(new CreateClientRequest("Foo"))))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.name", is("Foo")))
                    .andExpect(jsonPath("$.number", notNullValue()));&*/
    }
}

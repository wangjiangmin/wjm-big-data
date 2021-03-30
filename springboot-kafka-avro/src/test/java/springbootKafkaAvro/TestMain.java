package springbootKafkaAvro;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;


@SpringBootTest(classes = SKAApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TestMain {

    private static final Logger log = LoggerFactory.getLogger(TestMain.class);

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;



    @BeforeEach
    public void begin () {
        System.out.println("开始测试	>>>>");

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();//建议使用这种

    }

    public void finish() {
        System.out.println("结束测试	>>>>");
    }


    public void buildGet(String url, Map<String, String> data) throws Exception {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON);
        if(null == data) {
            data = new HashMap<String, String>();
        }
        for (String key : data.keySet()) {
            request.param(key, data.get(key));
        }

        MvcResult mvcResult= mockMvc.perform(request).andDo(MockMvcResultHandlers.print()).andReturn();


        int status=mvcResult.getResponse().getStatus();                 //得到返回代码
        String content = mvcResult.getResponse().getContentAsString();    //得到返回结果

        log.info("返回结果: " + content);

        Assert.assertEquals(200,status);                        //断言，判断返回代码是否正确
    }

    public void buildPost(String url,Map<String, String> data) throws Exception {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(url).accept(MediaType.APPLICATION_JSON);
        if(null == data) {
            data = new HashMap<String, String>();
        }
        for (String key : data.keySet()) {
            request.param(key, data.get(key));
        }

        MvcResult mvcResult= mockMvc.perform(request).andDo(MockMvcResultHandlers.print()).andReturn();


        int status=mvcResult.getResponse().getStatus();                 //得到返回代码
        String content=mvcResult.getResponse().getContentAsString();    //得到返回结果

        log.info("返回结果: " + content);

        Assert.assertEquals(200,status);                        //断言，判断返回代码是否正确
    }

}

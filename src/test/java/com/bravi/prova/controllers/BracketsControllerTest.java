package com.bravi.prova.controllers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BracketsControllerTest {
    private final String appAddr = "http://localhost:1147/brackets";
    @Autowired
    WebApplicationContext webApplicationContext;
    MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void brackets() throws Exception {
        MvcResult sendReceiveDoc = mockMvc.perform(get(appAddr)
                .content(""))
                .andReturn();
        String response = new String(sendReceiveDoc.getResponse().getContentAsByteArray());
        Assert.assertFalse(response.contains("Resultado:"));
    }

    @Test
    public void bracketsValid() throws Exception {
        String response = performFormPost("brackets={{}}");
        Assert.assertTrue(response.contains("É válido"));
    }

    @Test
    public void bracketsInValid() throws Exception {
        String response = performFormPost("brackets={{}}");
        Assert.assertTrue(response.contains("É válido"));
    }

    public String performFormPost(String content) throws Exception {
        MvcResult sendReceiveDoc = mockMvc.perform(post(appAddr)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(content))
                .andReturn();
        return new String(sendReceiveDoc.getResponse().getContentAsByteArray());
    }
}
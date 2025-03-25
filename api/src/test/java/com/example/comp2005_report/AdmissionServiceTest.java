package com.example.comp2005_report;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(Comp2005ReportApplication.class)
class AdmissionServiceTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetNothing() throws Exception {
        // arrange
        MockHttpServletRequestBuilder req = MockMvcRequestBuilders.get("/admissions/most").accept(MediaType.APPLICATION_JSON);

        // act
        MvcResult res = mockMvc.perform(req).andReturn();

        // assert
        System.out.println("Response Code: " +  res.getResponse().getStatus());
        System.out.println("Response Content: " + res.getResponse().getContentAsString());

        assertEquals(200, res.getResponse().getStatus());
        assertNotNull(res.getResponse().getContentAsString());
    }
}
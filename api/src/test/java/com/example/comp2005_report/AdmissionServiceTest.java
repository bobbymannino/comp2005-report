package com.example.comp2005_report;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;

@Testable
@WebMvcTest(AdmissionService.class)
class AdmissionServiceTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetMostAdmissionsMonth() throws Exception {
        // arrange
        MockHttpServletRequestBuilder req = MockMvcRequestBuilders.get("/admissions/most").accept(MediaType.APPLICATION_JSON);
        String reg = "^\\{\"busiestMonth\":\"\\w{3,}\",\"admissions\":\\d+}$";

        // act
        MvcResult res = mockMvc.perform(req).andReturn();
        String resContent = res.getResponse().getContentAsString();

        // assert
        assertEquals(200, res.getResponse().getStatus());
        assertNotNull(resContent);
        assertTrue(resContent.matches(reg));
    }

    @Test
    void testGetNeverAdmittedPatients() throws Exception {
        // arrange
        MockHttpServletRequestBuilder req = MockMvcRequestBuilders.get("/admissions/never").accept(MediaType.APPLICATION_JSON);
        String reg = "^\\{\"admissions\":\\[\\d?(,\\d)*]}$";

        // act
        MvcResult res = mockMvc.perform(req).andReturn();
        String resContent = res.getResponse().getContentAsString();

        // assert
        assertEquals(200, res.getResponse().getStatus());
        assertNotNull(resContent);
        assertTrue(resContent.matches(reg));
    }

    @Test
    void testGetReAdmittedPatients7Days() throws Exception {
        // arrange
        MockHttpServletRequestBuilder req = MockMvcRequestBuilders.get("/admissions/re").accept(MediaType.APPLICATION_JSON);
        String reg = "^\\{\"admissions\":\\[\\d?(,\\d)*]}$";

        // act
        MvcResult res = mockMvc.perform(req).andReturn();
        String resContent = res.getResponse().getContentAsString();
        System.out.println(resContent);

        // assert
        assertEquals(200, res.getResponse().getStatus());
        assertNotNull(resContent);
        assertTrue(resContent.matches(reg));
    }
}
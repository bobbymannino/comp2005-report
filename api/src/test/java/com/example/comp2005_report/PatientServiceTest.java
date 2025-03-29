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
@WebMvcTest(PatientService.class)
class PatientServiceTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetMultiStaffPatients() throws Exception {
        // arrange
        MockHttpServletRequestBuilder req = MockMvcRequestBuilders.get("/patients/multi-staff").accept(MediaType.APPLICATION_JSON);
        String reg = "^\\{\"patients\":\\[\\d?(,\\d)*]}$";

        // act
        MvcResult res = mockMvc.perform(req).andReturn();
        String resContent = res.getResponse().getContentAsString();

        // assert
        assertEquals(200, res.getResponse().getStatus());
        assertNotNull(resContent);
        assertTrue(resContent.matches(reg));
    }
}
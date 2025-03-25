package com.example.comp2005_report;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

//@Testable
class APIHelperTest {
    private final ObjectMapper mapper = new ObjectMapper();

//    @Test
    void testGetNothing() throws Exception {
        // arrange
        String res = APIHelper.get("/patients/1");

        System.out.println(res);

        // act
        PatientClass patient = mapper.readValue(res, PatientClass.class);

        // assert
        assertNotNull(patient);
    }
}
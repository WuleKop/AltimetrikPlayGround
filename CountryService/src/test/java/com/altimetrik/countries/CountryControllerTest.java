package com.altimetrik.countries;

import com.altimetrik.countries.controller.CountryController;
import com.altimetrik.countries.model.Country;
import com.altimetrik.countries.service.CountryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value= CountryController.class)
public class CountryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountryService countryService;

    private JacksonTester<List<Country>> jsonSuperHero;

    @BeforeEach
    public void init(){
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void getMatchingCountriesTest() throws Exception {

        List<Country> countries = new ArrayList<>();

        countries.add(new Country("BGD", "Bangladesh", "Dhaka","SAS","LMC",
                "IDX","90.4113", "23.7055"));
        countries.add(new Country("IND", "India", "New Delhi", "SAS","LMC",
                "IBD","77.225", "28.6353"));
        countries.add(new Country("BTN","Bhutan","Thimphu","SAS","LMC",
                "IDX","89.6177","27.5768"));
        countries.add(new Country("PAK","Pakistan","Islamabad","SAS","LMC",
                "IDB","72.8","30.5167"));


        when(countryService.getSimilarCountries("AFG", true,
                false, false)).thenReturn(countries);


        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.get("http://localhost:8080/api/countries/similar")
                        .param("countryCode", "AFG")
                        .param("regionCode", "true")
                        .param("incomeCode", "false")
                        .param("lendingCode", "false"))
                .andReturn().getResponse();
        Assertions.assertEquals(response.getStatus(), HttpStatus.CREATED.value());
        Assertions.assertEquals(response.getContentAsString(), jsonSuperHero.write((countries)).getJson());
    }
}

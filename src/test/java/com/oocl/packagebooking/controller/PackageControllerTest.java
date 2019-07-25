package com.oocl.packagebooking.controller;

import com.oocl.packagebooking.model.Customer;
import com.oocl.packagebooking.model.Package;
import com.oocl.packagebooking.service.PackageService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PackageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PackageService packageService;

    private List<Package> packages;

    @Before
    public void setUp() {
        packages = new ArrayList<Package>(){{
                new Package("NO10001", new Customer(), "123456789", 1, new Date());
                new Package("NO10001", new Customer(), "123456789", 1, new Date());
                new Package("NO10001", new Customer(), "123456789", 1, new Date());
        }};
    }

    @Test
    public void should_return_all_packages_when_request_for_all() throws Exception {
        Mockito.when(packageService.findAllPackages())
                .thenReturn(packages);
        mockMvc.perform(get("/packages")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(packages.size()));
    }

}
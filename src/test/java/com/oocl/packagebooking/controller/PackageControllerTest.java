package com.oocl.packagebooking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.packagebooking.common.Constants;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        packages = new ArrayList<Package>() {{
            new Package("NO10001", new Customer(), "123456789", 1, new Date());
            new Package("NO10001", new Customer(), "123456789", 1, new Date());
            new Package("NO10001", new Customer(), "123456789", 1, new Date());
        }};
    }

    @Test
    public void should_return_all_packages_when_request_for_all() throws Exception {
        Mockito.when(packageService.findByConditions(Mockito.anyInt()))
                .thenReturn(packages);
        mockMvc.perform(get("/packages")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(packages.size()));
    }

    @Test
    public void should_return_the_updated_package_when_request_to_update() throws Exception {
        Package packageInformation = new Package("NO10001", new Customer(), "123456789", Constants.BE_TAKEN_STATUS, new Date());
        Mockito.when(packageService.updatePackage(Mockito.anyInt(), Mockito.any()))
                .thenReturn(packageInformation);
        mockMvc.perform(put("/packages/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(packageInformation)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(0));
    }

    @Test
    public void should_return_the_new_package_with_id_when_request_to_insert() throws Exception {
        Package packageInformation = new Package("NO10001", new Customer(), "123456789", Constants.NO_ORDERED, new Date());
        packageInformation.setId(10010);
        Mockito.when(packageService.savePackage(Mockito.any()))
                .thenReturn(packageInformation);
        mockMvc.perform(post("/packages")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(packageInformation)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10010));
    }
}
package com.oocl.packagebooking.service;

import com.oocl.packagebooking.common.Constants;
import com.oocl.packagebooking.model.Customer;
import com.oocl.packagebooking.model.Package;
import com.oocl.packagebooking.repository.PackageRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PackageServiceTest {

    @Autowired
    PackageService packageService;

    @MockBean
    PackageRepository packageRepository;

    @Test
    public void should_return_all_the_status_is_1_packages_when_request_with_conditions() {
        // given
        List<Package> packages = new ArrayList<>();
        packages.add(new Package("NO10001", new Customer(), "123456789", 1, new Date()));

        Mockito.when(packageRepository.findAll((Specification<Package>)Mockito.any())).thenReturn(packages);

        // when
        List<Package> actualPackages = packageService.findByConditions(Constants.TO_TAKE_STATUS, Constants.HAVD_ORDERED);
        // then
        Assertions.assertEquals(packages.size(), actualPackages.size());
    }
}
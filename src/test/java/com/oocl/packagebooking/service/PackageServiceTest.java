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
        List<Package> actualPackages = packageService.findByConditions(Constants.HAVED_ORDERED);
        // then
        Assertions.assertEquals(packages.size(), actualPackages.size());
    }

    @Test
    public void should_return_the_updated_package_when_request_to_update() {
        // given
        Package givenPackage = new Package("NO10001", new Customer(), "123456789", 1, new Date());
        givenPackage.setId(1);
        Mockito.when(packageRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(givenPackage));
        Mockito.when(packageRepository.save(Mockito.any())).thenReturn(givenPackage);
        int id = 1;
        // when
        Package updatedPackage = packageService.updatePackage(id, givenPackage);
        // then
        Assertions.assertEquals(updatedPackage.getStatus(), givenPackage.getStatus());
    }
}
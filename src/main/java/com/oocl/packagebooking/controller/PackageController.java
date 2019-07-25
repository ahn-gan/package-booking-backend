package com.oocl.packagebooking.controller;

import com.oocl.packagebooking.model.Package;
import com.oocl.packagebooking.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/packages")
@CrossOrigin(origins = "http://localhost:8081")
public class PackageController {

    @Autowired
    private PackageService packageService;

    @GetMapping
    public ResponseEntity findByConditions(@RequestParam(value = "status", defaultValue = "-1") int status) {
        return ResponseEntity.ok(packageService.findByConditions(status));
    }

    @PutMapping("/{id}")
    public ResponseEntity updateConditions(@PathVariable int id, @RequestBody Package packageInformation) {
        Package resultPackage = packageService.updatePackage(id, packageInformation);
        return resultPackage != null ? ResponseEntity.ok(resultPackage) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity addPackageInformation(@RequestBody Package packageInformation) throws Exception {
        return ResponseEntity.ok(packageService.savePackage(packageInformation));
    }

}

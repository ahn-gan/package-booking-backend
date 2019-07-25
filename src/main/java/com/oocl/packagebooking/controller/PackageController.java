package com.oocl.packagebooking.controller;

import com.oocl.packagebooking.dto.ParamsDto;
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
    public ResponseEntity findByConditions(@RequestParam(value = "status", defaultValue = "0") int status, @RequestParam(value = "hasOrdered", defaultValue = "0") int hasOrdered) {
        return ResponseEntity.ok(packageService.findByConditions(status, hasOrdered));
    }

}

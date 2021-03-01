package com.epam.esm.controller;

import com.epam.esm.service.impl.FillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fill")
public class FillDBController {

    @Autowired
    private FillService fillService;

    @GetMapping
    public Boolean fill() {
        fillService.fill();
        return true;
    }
}

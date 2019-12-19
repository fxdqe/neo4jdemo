package com.hdu.neo4jdemo.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/journal")
public class IndexPageController {

    @RequestMapping
    public String index() {
        return "index.html";
    }
}

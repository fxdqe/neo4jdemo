package com.hdu.neo4jdemo.controller;

import com.hdu.neo4jdemo.services.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/")
public class PaperController {

    @Autowired
    private final PaperService paperService;

    public PaperController(PaperService paperService) {
        this.paperService = paperService;
    }

    @GetMapping("/graph")
    @ResponseBody
    public Map<String, Object> graph(@RequestParam(value = "limit",required = false) Integer limit) {
        return paperService.graph(limit == null ? 100 : limit);
    }
}

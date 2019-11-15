package com.hdu.neo4jdemo.controller;

import java.util.Collection;
import java.util.Map;

import com.hdu.neo4jdemo.domain.Paper;
import com.hdu.neo4jdemo.services.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class PaperController {

    private final PaperService paperService;

    public PaperController(PaperService paperService) {
        this.paperService = paperService;
    }

    @GetMapping("/graph")
    public Map<String, Object> graph(@RequestParam(value = "limit",required = false) Integer limit) {
        return paperService.graph(limit == null ? 100 : limit);
    }
}

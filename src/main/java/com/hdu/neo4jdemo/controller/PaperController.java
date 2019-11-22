package com.hdu.neo4jdemo.controller;

import com.hdu.neo4jdemo.domain.Paper;
import com.hdu.neo4jdemo.services.impl.PaperServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/paper")
public class PaperController {

    @Autowired
    private final PaperServiceImpl paperService;

    public PaperController(PaperServiceImpl paperService) {
        this.paperService = paperService;
    }

    @RequestMapping("/graph")
    public Collection<Paper> graph(@RequestParam(value = "limit",required = false) Integer limit) {
        return paperService.graph(limit == null ? 100 : limit);
    }

//    @RequestMapping("/name1/{name}/{limit}")
    @RequestMapping("/name1/{name}")
    public List<Paper> findByName1(@PathVariable(value = "name") String name,@PathVariable(value = "limit",required = false) Integer limit){
        return paperService.findByName1(name, limit == null ? 20 : limit);
    }
}

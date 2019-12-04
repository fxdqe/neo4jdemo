package com.hdu.neo4jdemo.api.controller;

import com.hdu.neo4jdemo.api.entity.Paper;
import com.hdu.neo4jdemo.api.repositories.PaperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/paper")
public class PaperController {

    @Autowired
    private final PaperRepository paperRepository;

    public PaperController(PaperRepository paperRepository) {
        this.paperRepository = paperRepository;
    }

    @GetMapping("/getPapers")
    public List<Paper> getPapers() {
        return (List<Paper>) paperRepository.findAll();
    }

    @GetMapping("/getPaper/{name}")
    public Paper getPaper(@PathVariable String name) {
        return paperRepository.findByName(name);
    }

    @GetMapping("/getHello/{hi}")
    public String getHello(@PathVariable String hi){
        return "Hello, "+ hi;
    }

//    @RequestMapping("/graph")
//    public Collection<Paper> graph(@RequestParam(value = "limit",required = false) Integer limit) {
//        return paperService.graph(limit == null ? 100 : limit);
//    }
//
////    @RequestMapping("/name1/{name}/{limit}")
//    @RequestMapping({"/name1/{name1}/{limit}","/name1/{name1}"})
//    public String findByName1(@PathVariable(value = "name1") String name1,@PathVariable(value = "limit",required = false) Integer limit){
//        return paperService.findByName1(name1, limit == null ? 20 : limit).toString();
//    }
}

package com.hdu.neo4jdemo.api.controller;


import com.hdu.neo4jdemo.api.services.impl.QuestionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    QuestionServiceImpl questionServiceImpl;

    @GetMapping("/hanlp/{question}")
    public String hanlptest(@PathVariable String question) {
        return questionServiceImpl.hanlptest(question);
    }

    @GetMapping("/answer/{question}")
    public String answer(@PathVariable String question) {
        return questionServiceImpl.answer(question);
    }
}

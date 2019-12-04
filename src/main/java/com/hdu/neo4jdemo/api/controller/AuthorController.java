package com.hdu.neo4jdemo.api.controller;

import com.hdu.neo4jdemo.api.entity.Author;
import com.hdu.neo4jdemo.api.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    public final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping("/getAuthor/{name}")
    public Author getAuthor(@PathVariable String name){
        return authorRepository.findByName(name);
    }

    @GetMapping("/getHello/")
    public String getHello(){
        return "Hello";
    }

    @GetMapping("/getHello/{hi}")
    public String getHelloHi(@PathVariable String hi){
        return "Hello, "+ hi;
    }
}

package com.hdu.neo4jdemo.api.controller;

import com.hdu.neo4jdemo.api.entity.node.Author;
import com.hdu.neo4jdemo.api.entity.node.Paper;
import com.hdu.neo4jdemo.api.repositories.AuthorRepository;
import com.hdu.neo4jdemo.api.repositories.PaperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/paper")
public class PaperController {

    @Autowired
    private final PaperRepository paperRepository;

    @Autowired
    private final AuthorRepository authorRepository;

    public PaperController(PaperRepository paperRepository) {
        this.authorRepository = null;
        this.paperRepository = paperRepository;
    }

    public PaperController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
        this.paperRepository = null;
    }

    public PaperController(PaperRepository paperRepository, AuthorRepository authorRepository) {
        this.paperRepository = paperRepository;
        this.authorRepository = authorRepository;
    }



    @GetMapping("/getPapers")
    public List<Paper> getPapers() {
        return (List<Paper>) paperRepository.findAll();
    }

    @GetMapping("/getPaper/name/{name}")
    public Paper getByName(@PathVariable String name) {
        return paperRepository.findOneByName(name);
    }

    //模糊查询
    @GetMapping("/getPaper/fname/{name}")
    public Paper fuzzyGetByName(@PathVariable String name) {
        name = "*" + name + "*";
        return paperRepository.findByNameLike(name);
    }

    //模糊查询
    @GetMapping("/getPaper/classification/{classification}")
    public List<Paper> fuzzyGetByClassification(@PathVariable String classification) {
        classification = "*" + classification + "*";
        return paperRepository.findByClassificationLike(classification);
    }


    /*
     *  论文名字查询作者
     */
    @GetMapping("/getAuthor1/name/{name}")
    public Author getAuthor1(@PathVariable String name) {
        return authorRepository.findAuthor1ByName(name);
    }

    @GetMapping("/getAuthor2/name/{name}")
    public Author getAuthor2(@PathVariable String name) {
        return authorRepository.findAuthor2ByName(name);
    }

    @GetMapping("/getAuthor3/name/{name}")
    public Author getAuthor3(@PathVariable String name) {
        return authorRepository.findAuthor3ByName(name);
    }

    @GetMapping("/getAuthor4/name/{name}")
    public Author getAuthor4(@PathVariable String name) {
        return authorRepository.findAuthor4ByName(name);
    }

    @GetMapping("/getAuthors/name/{name}")
    public List<Author> getAuthors(@PathVariable String name) {
        ArrayList<Author> authors = new ArrayList<>();
        authors.add(getAuthor1(name));
        authors.add(getAuthor2(name));
        authors.add(getAuthor3(name));
        authors.add(getAuthor4(name));
        authors.add(null);
        // 去除null项
        List<String> list = new ArrayList<>();
        list.add(null);
        authors.removeAll(list);
        return authors;
    }
}

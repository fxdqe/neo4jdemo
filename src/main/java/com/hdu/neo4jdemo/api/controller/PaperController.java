package com.hdu.neo4jdemo.api.controller;

import com.hdu.neo4jdemo.api.entity.node.Author;
import com.hdu.neo4jdemo.api.entity.node.Paper;
import com.hdu.neo4jdemo.api.repositories.PaperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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

    public PaperController(PaperRepository paperRepository) {
        this.paperRepository = paperRepository;
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
        return paperRepository.findAuthor1ByName(name);
    }

    @GetMapping("/getAuthor2/name/{name}")
    public Author getAuthor2(@PathVariable String name) {
        return paperRepository.findAuthor2ByName(name);
    }

    @GetMapping("/getAuthor3/name/{name}")
    public Author getAuthor3(@PathVariable String name) {
        return paperRepository.findAuthor3ByName(name);
    }

    @GetMapping("/getAuthor4/name/{name}")
    public Author getAuthor4(@PathVariable String name) {
        return paperRepository.findAuthor4ByName(name);
    }

    @GetMapping("/getAuthors/name/{name}")
    public List<Author> getAuthors(@PathVariable String name) {
        ArrayList<Author> authors = new ArrayList<>();
        Author author = getAuthor1(name);
        if (!StringUtils.isEmpty(author)) {
            authors.add(author);
            author = getAuthor2(name);
            if (!StringUtils.isEmpty(author)) {
                authors.add(author);
                author = getAuthor3(name);
                if (!StringUtils.isEmpty(author)) {
                    authors.add(author);
                    author = getAuthor4(name);
                    if (!StringUtils.isEmpty(author)) {
                        authors.add(author);
                    }
                }
            }
        }
        return authors;
    }
}

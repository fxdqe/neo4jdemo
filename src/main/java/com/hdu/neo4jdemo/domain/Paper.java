package com.hdu.neo4jdemo.domain;

import java.util.*;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@NodeEntity
public class Paper {

    @Id
    @GeneratedValue
    private Integer id;
    private String paper_id;
    private String name;
    private String Abstract;
    private Integer area;
    private String url;
    private Integer year;

    @Relationship(type = "RELATED_TO")
    private Subject subject;

    @Relationship(type = "first_author", direction = Relationship.INCOMING)
    private Author author1;

    @Relationship(type = "second_author", direction = Relationship.INCOMING)
    private Author author2;

    @Relationship(type = "third_author", direction = Relationship.INCOMING)
    private Author author3;

    @Relationship(type = "fourth_author", direction = Relationship.INCOMING)
    private Author author4;

    public Paper() {
    }

    public Paper(Integer id, String paper_id, String name, String anAbstract, Integer area, String url, Integer year) {
        this.id = id;
        this.paper_id = paper_id;
        this.name = name;
        this.Abstract = anAbstract;
        this.area = area;
        this.url = url;
        this.year = year;
    }

    public Integer getId() {
        return id;
    }

    public String getPaper_id() {
        return paper_id;
    }

    public String getName() {
        return name;
    }

    public String getAbstract() {
        return Abstract;
    }

    public Integer getArea() {
        return area;
    }

    public String getUrl() {
        return url;
    }

    public Integer getYear() {
        return year;
    }

    public Subject getSubject() {
        return subject;
    }

    public Author getAuthor1(){
        return author1;
    }

    public Author getAuthor2() {
        return author2;
    }

    public Author getAuthor3() {
        return author3;
    }

    public Author getAuthor4() {
        return author4;
    }

    public  List<Map<String, Object>> getAuthor(){
        List<Map<String, Object>> authors = new ArrayList<>();
        // 1
        Author author = getAuthor1();
        if (author == null)
            return authors;
        authors.add(map("name",author,"label","first_author"));
        // 2
        author = getAuthor2();
        if (author == null)
            return authors;
        authors.add(map("name",author,"label","second_author"));
        // 3
        author = getAuthor3();
        if (author == null)
            return authors;
        authors.add(map("name",author,"label","third_author"));
        // 4
        author = getAuthor4();
        if (author == null)
            return authors;
        authors.add(map("name",author,"label","fourth_author"));
        return authors;
    }

    private Map<String, Object> map(String key1, Object value1, String key2, Object value2) {
        Map<String, Object> result = new HashMap<String, Object>(2);
        result.put(key1, value1);
        result.put(key2, value2);
        return result;
    }

}

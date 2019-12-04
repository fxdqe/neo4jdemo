package com.hdu.neo4jdemo.api.entity;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

@NodeEntity
public class Subject {

    @Id
    @GeneratedValue
    private Long id;

    private String code;
    private String name;

    @Relationship(type = "RELATED_TO")
    private List<Paper> papers = new ArrayList<>();

    public Subject() {
    }

    public Subject(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }



}

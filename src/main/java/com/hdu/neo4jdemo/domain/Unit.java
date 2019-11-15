package com.hdu.neo4jdemo.domain;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

@NodeEntity
public class Unit {

    @Id
    @GeneratedValue
    private String name;
    private Integer type;

    @Relationship(type = "made_in")
    private List<Paper> papers = new ArrayList<>();

    public Unit(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public Unit() {
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public List<Paper> getPapers() {
        return papers;
    }
}

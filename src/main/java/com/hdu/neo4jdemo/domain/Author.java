package com.hdu.neo4jdemo.domain;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
public class Author {
    private String name;
    private Integer cnki_code;
    private Integer baidu_code;

    @Relationship(type = "first_author")
    private List<Paper> papers1;

    @Relationship(type = "second_author")
    private List<Paper> papers2;

    @Relationship(type = "third_author")
    private List<Paper> papers3;

    @Relationship(type = "fourth_author")
    private List<Paper> papers4;

    public String getName() {
        return name;
    }

    public Integer getCnki_code() {
        return cnki_code;
    }

    public Integer getBaidu_code() {
        return baidu_code;
    }

    public List<Paper> getPapers1() {
        return papers1;
    }

    public List<Paper> getPapers2() {
        return papers2;
    }

    public List<Paper> getPapers3() {
        return papers3;
    }

    public List<Paper> getPapers4() {
        return papers4;
    }
}

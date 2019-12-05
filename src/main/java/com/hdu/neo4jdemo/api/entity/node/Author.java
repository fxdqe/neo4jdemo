package com.hdu.neo4jdemo.api.entity.node;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity(label = "author")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    @Id
    @GeneratedValue
    private Long id;

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

}

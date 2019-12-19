package com.hdu.neo4jdemo.api.entity.node;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity(label = "paper")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paper {

    @Id
    @GeneratedValue
    private long id;

    private Integer pid;
    private String paper_id;
    private String name;
    private String Abstract;
    private String classification;
    private Integer year;
    private Integer area;
    private String url;
    private String unit;
    private String unit_type;
    private String keywords;

    @JsonIgnore
    @Relationship(type = "is")
    private Subject subject;

    @JsonIgnore
    @Relationship(type = "FIRST_AUTHOR")
    private Author author1;

    @JsonIgnore
    @Relationship(type = "SECOND_AUTHOR")
    private Author author2;

    @JsonIgnore
    @Relationship(type = "THIRD_AUTHOR")
    private Author author3;

    @JsonIgnore
    @Relationship(type = "FOURTH_AUTHOR")
    private Author author4;

}

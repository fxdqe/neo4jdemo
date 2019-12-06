package com.hdu.neo4jdemo.api.entity.node;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

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

    @JsonIgnore
    @Relationship(type = "FIRST_AUTHOR", direction = Relationship.INCOMING)
    private Paper paper1;

    @JsonIgnore
    @Relationship(type = "SECOND_AUTHOR", direction = Relationship.INCOMING)
    private Paper paper2;

    @JsonIgnore
    @Relationship(type = "THIRD_AUTHOR", direction = Relationship.INCOMING)
    private Paper paper3;

    @JsonIgnore
    @Relationship(type = "FOURTH_AUTHOR", direction = Relationship.INCOMING)
    private Paper paper4;

}

package com.hdu.neo4jdemo.api.entity.node;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity(label = "journal")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Journal {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String no;
    private Integer quality;
    private String url;

    @JsonIgnore
    @Relationship(type = "RELEASED_ON", direction = Relationship.INCOMING)
    private Paper paper;
}

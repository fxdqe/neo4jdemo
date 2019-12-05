package com.hdu.neo4jdemo.api.entity.relationship;

import com.hdu.neo4jdemo.api.entity.node.Author;
import com.hdu.neo4jdemo.api.entity.node.Paper;
import org.neo4j.ogm.annotation.*;
import org.springframework.stereotype.Component;

@Component
@RelationshipEntity(type = "SECOND_AUTHOR")
public class SECONDAUTHOR {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private Author from;

    @EndNode
    private Paper to;

    public Long getId() {
        return id;
    }

    public Author getFrom() {
        return from;
    }

    public Paper getTo() {
        return to;
    }
}

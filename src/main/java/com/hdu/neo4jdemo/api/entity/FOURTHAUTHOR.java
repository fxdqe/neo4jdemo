package com.hdu.neo4jdemo.api.entity;

import org.neo4j.ogm.annotation.*;
import org.springframework.stereotype.Component;

@Component
@RelationshipEntity(type = "FOURTH_AUTHOR")
public class FOURTHAUTHOR {
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

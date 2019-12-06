package com.hdu.neo4jdemo.api.entity.relationship;

import com.hdu.neo4jdemo.api.entity.node.Author;
import com.hdu.neo4jdemo.api.entity.node.Paper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@RelationshipEntity(type = "SECOND_AUTHOR")
public class SECOND_AUTHOR {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private Author from;

    @EndNode
    private Paper to;
}

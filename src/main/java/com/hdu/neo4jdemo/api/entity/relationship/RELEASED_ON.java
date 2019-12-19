package com.hdu.neo4jdemo.api.entity.relationship;

import com.hdu.neo4jdemo.api.entity.node.Journal;
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
@RelationshipEntity(type = "RELEASED_ON")
public class RELEASED_ON {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private Paper from;

    @EndNode
    private Journal to;
}

package com.hdu.neo4jdemo.api.entity.relationship;

import com.hdu.neo4jdemo.api.entity.node.Paper;
import com.hdu.neo4jdemo.api.entity.node.Subject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@RelationshipEntity(type = "RELATED_TO")
public class RELATED_TO {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private Paper from;

    @EndNode
    private Subject to;
}

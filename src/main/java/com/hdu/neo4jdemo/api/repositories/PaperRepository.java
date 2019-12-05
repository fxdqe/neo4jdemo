package com.hdu.neo4jdemo.api.repositories;

import com.hdu.neo4jdemo.api.entity.node.Paper;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaperRepository extends Neo4jRepository<Paper, Long> {

    @Query("MATCH (p:paper) where p.name = {name} return p limit 1")
    Paper findByName(@Param("name") String name);

}


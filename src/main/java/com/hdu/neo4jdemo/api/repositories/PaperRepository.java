package com.hdu.neo4jdemo.api.repositories;

import com.hdu.neo4jdemo.api.entity.node.Author;
import com.hdu.neo4jdemo.api.entity.node.Paper;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaperRepository extends Neo4jRepository<Paper, Long> {

    @Query("MATCH (p:paper) where p.name = {name} return p limit 1")
    Paper findOneByName(@Param("name") String name);

    Paper findByNameLike(String name);

    List<Paper> findByClassificationLike(String classification);

    @Query("MATCH (a:author)-[r:FIRST_AUTHOR]->(p:paper) where p.name= {name} return a")
    Author findAuthor1ByName(@Param("name") String name);

    @Query("MATCH (a:author)-[r:SECOND_AUTHOR]->(p:paper) where p.name= {name} return a")
    Author findAuthor2ByName(@Param("name") String name);

    @Query("MATCH (a:author)-[r:THIRD_AUTHOR]->(p:paper) where p.name= {name} return a")
    Author findAuthor3ByName(@Param("name") String name);

    @Query("MATCH (a:author)-[r:FOURTH_AUTHOR]->(p:paper) where p.name= {name} return a")
    Author findAuthor4ByName(@Param("name") String name);
}
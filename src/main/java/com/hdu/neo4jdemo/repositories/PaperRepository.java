package com.hdu.neo4jdemo.repositories;

import com.hdu.neo4jdemo.domain.Paper;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;
import java.util.List;

@RepositoryRestResource(collectionResourceRel = "paper", path = "paper")
public interface PaperRepository extends Neo4jRepository<Paper, Long> {

    @Query("match (a:author)-[:first_author]->(p:paper) where a.name='{name1}' return p limit {limit}")
    List<Paper> findByName1(@Param(value = "name") String name, @Param(value = "limit") Integer limit);

    Collection<Paper> findByAreaLike(@Param("area") Integer area);

    @Query("MATCH (a:author)-[r:first_author]->(p:paper) RETURN a,r,p LIMIT {limit}")
    Collection<Paper> graph(@Param(value = "limit") int limit);
}
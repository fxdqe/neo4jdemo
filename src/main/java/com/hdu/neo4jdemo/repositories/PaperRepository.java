package com.hdu.neo4jdemo.repositories;

import java.util.Collection;

import com.hdu.neo4jdemo.domain.Paper;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "movies", path = "movies")
public interface PaperRepository extends Neo4jRepository<Paper, Long> {

    Paper findByName(@Param("name1") String name);

    Collection<Paper> findByAreaLike(@Param("area") Integer area);

    // comment test
    @Query("MATCH (a:author)-[r:first_author]->(p:paper) RETURN a,r,p LIMIT {limit}")
    Collection<Paper> graph(@Param("limit") int limit);
}
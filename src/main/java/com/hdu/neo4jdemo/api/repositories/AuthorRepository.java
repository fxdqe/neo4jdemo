package com.hdu.neo4jdemo.api.repositories;

import com.hdu.neo4jdemo.api.entity.node.Author;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends Neo4jRepository<Author, Long> {

    @Query("MATCH (a:author) where a.name = {name} return a limit 1")
    Author findByName(@Param("name") String name);
}

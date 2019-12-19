package com.hdu.neo4jdemo.api.repositories;

import com.hdu.neo4jdemo.api.entity.node.Journal;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalRepository extends Neo4jRepository<Journal, Long> {

    @Query("MATCH (j:journal) where j.name = {name} return j.url limit 1")
    String findUrlByName(@Param("name") String journalName);
}

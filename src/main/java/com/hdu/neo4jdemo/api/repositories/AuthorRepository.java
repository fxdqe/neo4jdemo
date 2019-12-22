package com.hdu.neo4jdemo.api.repositories;

import com.hdu.neo4jdemo.api.entity.node.Author;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends Neo4jRepository<Author, Long> {

    @Query("MATCH (a:author) where a.name = {name} return a limit 1")
    Author findByName(@Param("name") String name);

    @Query("MATCH (a:author)-[r:FIRST_AUTHOR]->(p:paper) WHERE p.name= {name} RETURN a")
    Author findAuthor1ByName(@Param("name") String name);

    @Query("MATCH (a:author)-[r:SECOND_AUTHOR]->(p:paper) WHERE p.name= {name} RETURN a")
    Author findAuthor2ByName(@Param("name") String name);

    @Query("MATCH (a:author)-[r:THIRD_AUTHOR]->(p:paper) WHERE p.name= {name} RETURN a")
    Author findAuthor3ByName(@Param("name") String name);

    @Query("MATCH (a:author)-[r:FOURTH_AUTHOR]->(p:paper) WHERE p.name= {name} RETURN a")
    Author findAuthor4ByName(@Param("name") String name);

    @Query("MATCH (a:author)-[r]->(p:paper) WHERE p.pid = {id} RETURN a.name")
    List<String> findNamesByPaperId(@Param("id") Integer id);

    @Query("MATCH (a:author)-[r:FIRST_AUTHOR]->(p:paper) WHERE p.pid = {id} RETURN a.name LIMIT 1")
    String findFIRSTByPaperId(@Param("id") Integer id);

    @Query("MATCH (a:author)-[r:SECOND_AUTHOR]->(p:paper) WHERE p.pid = {id} RETURN a.name LIMIT 1")
    String findSECONDByPaperId(@Param("id") Integer id);

    @Query("MATCH (a:author)-[r:THIRD_AUTHOR]->(p:paper) WHERE p.pid = {id} RETURN a.name LIMIT 1")
    String findTHIRDByPaperId(@Param("id") Integer id);

    @Query("MATCH (a:author)-[r:FOURTH_AUTHOR]->(p:paper) WHERE p.pid = {id} RETURN a.name LIMIT 1")
    String findFOURTHByPaperId(@Param("id") Integer id);

    @Query("MATCH (a:author) WHERE a.cnki_code = {id} RETURN a.name LIMIT 1")
    String findNameById(@Param("id") Integer authorId);

    @Query("MATCH (a:author) WHERE a.name = {name} RETURN a.id LIMIT 1")
    Integer findIdByName(@Param("name") String authorName);

    @Query("MATCH (a:author)-[r:BELONG_TO]->(o:Organization) WHERE a.cnki_code = {id} RETURN o.name LIMIT 1")
    String findOrganizationById(@Param("id") Integer authorId);

    @Query("MATCH (a:author)-[r1]->(p:paper)-[r:RELEASED_ON]->(j:journal) WHERE a.name = {name} RETURN j.name + ' ,' + j.no")
    List<String> findJournalNameByName(@Param("name") String authorName);

    @Query("MATCH (a:author)-[r1]->(p:paper)-[r:RELEASED_ON]->(j:journal) WHERE a.cnki_code = {id} RETURN j.name + ' ,' + j.no")
    List<String> findJournalNameById(@Param("id") Integer authorId);
}

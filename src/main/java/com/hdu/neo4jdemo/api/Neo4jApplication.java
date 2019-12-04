package com.hdu.neo4jdemo.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author hzl
 */
@SpringBootApplication
@EnableNeo4jRepositories(basePackages = "com.hdu.neo4jdemo.api.repositories")
@EnableTransactionManagement
public class Neo4jApplication {
    public static void main(String[] args) {
        SpringApplication.run(Neo4jApplication.class, args);
    }
}
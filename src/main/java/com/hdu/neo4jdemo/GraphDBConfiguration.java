package com.hdu.neo4jdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author hzl
 */
@SpringBootApplication
@EnableNeo4jRepositories("com.hdu.neo4jdemo.repositories")
public class GraphDBConfiguration {

    public static void main(String[] args) {
        SpringApplication.run(GraphDBConfiguration.class, args);
    }
}
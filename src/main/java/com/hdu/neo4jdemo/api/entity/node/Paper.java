package com.hdu.neo4jdemo.api.entity.node;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashMap;
import java.util.Map;

@NodeEntity(label = "paper")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paper {

    @Id
    @GeneratedValue
    private long id;

    private Integer pid;
    private String paper_id;
    private String name;
    private String Abstract;
    private String classification;
    private Integer year;
    private Integer area;
    private String url;
    private String unit;
    private String unit_type;

    @Relationship(type = "RELATED_TO")
    private Subject subject;

    @Relationship(type = "first_author", direction = Relationship.INCOMING)
    private Author author1;

    @Relationship(type = "second_author", direction = Relationship.INCOMING)
    private Author author2;

    @Relationship(type = "third_author", direction = Relationship.INCOMING)
    private Author author3;

    @Relationship(type = "fourth_author", direction = Relationship.INCOMING)
    private Author author4;


    public Author getAuthor1(){
        return author1;
    }

    public Author getAuthor2() {
        return author2;
    }

    public Author getAuthor3() {
        return author3;
    }

    public Author getAuthor4() {
        return author4;
    }

    public  Map<String, Object> getAuthors(){
        Map<String, Object> authors = new HashMap<>();
        // 1
        Author author = getAuthor1();
        if (author == null)
            return authors;
        authors.put("first_author",author);
        // 2
        author = getAuthor2();
        if (author == null)
            return authors;
        authors.put("second_author",author);
        // 3
        author = getAuthor3();
        if (author == null)
            return authors;
        authors.put("third_author",author);
        // 4
        author = getAuthor4();
        if (author == null)
            return authors;
        authors.put("fourth_author",author);
        return authors;
    }

}

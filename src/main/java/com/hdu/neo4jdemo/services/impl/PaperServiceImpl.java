package com.hdu.neo4jdemo.services.impl;


import com.hdu.neo4jdemo.domain.Paper;
import com.hdu.neo4jdemo.repositories.PaperRepository;
import com.hdu.neo4jdemo.services.PaperService;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class PaperServiceImpl implements PaperService {

    private final static Logger LOG = LoggerFactory.getLogger(PaperServiceImpl.class);

    private final PaperRepository paperRepository;
    public PaperServiceImpl(PaperRepository paperRepository) {
        this.paperRepository = paperRepository;
    }

    private Driver createDrive(){
        return GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "123456" ) );
    }


    private Map<String, Object> toD3Format(Collection<Paper> papers) {
        List<Map<String, Object>> nodes = new ArrayList<>();
        List<Map<String, Object>> rels = new ArrayList<>();
        int i = 0;
        Iterator<Paper> result = papers.iterator();
        while (result.hasNext()) {
            Paper paper = result.next();
            nodes.add(map("name", paper.getName(), "label", "paper"));
            int target = i;
            i++;
//            for (Map<String, Object> author : paper.getAuthors()) {
//                Map<String, Object> actor = map("title", author.get("name"), "label", "actor");
//                int source = nodes.indexOf(actor);
//                if (source == -1) {
//                    nodes.add(actor);
//                    source = i++;
//                }
//                rels.add(map("source", source, "target", target));
//            }

            for(String key : paper.getAuthors().keySet()){

            }
        }
        return map("nodes", nodes, "links", rels);
    }

    private Map<String, Object> map(String key1, Object value1, String key2, Object value2) {
        Map<String, Object> result = new HashMap<String, Object>(2);
        result.put(key1, value1);
        result.put(key2, value2);
        return result;
    }


    @Transactional(readOnly = true)
    public List<Paper> findByName1(String name, Integer limit) {
        LOG.info("PSImpl"+ name+ limit);
        List<Paper> result = paperRepository.findByName1(name,limit);
        return result;
    }

    @Transactional(readOnly = true)
    public Collection<Paper> findByAreaLike(int area) {
        Collection<Paper> result = paperRepository.findByAreaLike(area);
        return result;
    }

    @Transactional(readOnly = true)
    public Collection<Paper> graph(int limit) {
        Collection<Paper> result = paperRepository.graph(limit);
        return result;
    }
}

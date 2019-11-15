package com.hdu.neo4jdemo.services;

import java.util.*;

import com.hdu.neo4jdemo.domain.Author;
import com.hdu.neo4jdemo.domain.Paper;
import com.hdu.neo4jdemo.repositories.PaperRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaperService {

    private final static Logger LOG = LoggerFactory.getLogger(PaperService.class);

    private final PaperRepository paperRepository;
    public PaperService(PaperRepository paperRepository) {
        this.paperRepository = paperRepository;
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
            for (Map<String, Object> author : paper.getAuthor()) {
                Map<String, Object> actor = map("title", author.getName(), "label", "actor");
                int source = nodes.indexOf(actor);
                if (source == -1) {
                    nodes.add(actor);
                    source = i++;
                }
                rels.add(map("source", source, "target", target));
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
    public Paper findByName(String name) {
        Paper result = paperRepository.findByName(name);
        return result;
    }

    @Transactional(readOnly = true)
    public Collection<Paper> findByAreaLike(int area) {
        Collection<Paper> result = paperRepository.findByAreaLike(area);
        return result;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> graph(int limit) {
        Collection<Paper> result = paperRepository.graph(limit);
        return result;
    }
}

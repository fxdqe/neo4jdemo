package com.hdu.neo4jdemo.services;

import com.hdu.common.entity.RestfulResult;
import com.hdu.neo4jdemo.domain.Paper;
import com.hdu.neo4jdemo.repositories.PaperRepository;
import org.neo4j.driver.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.neo4j.driver.Values.parameters;




@Service
public class PaperService {

    private final static Logger LOG = LoggerFactory.getLogger(PaperService.class);

    private final PaperRepository paperRepository;
    public PaperService(PaperRepository paperRepository) {
        this.paperRepository = paperRepository;
    }

    private Driver createDrive(){
        return GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "123456" ) );
    }

    @RequestMapping(value = "test")
    public void test(HttpServletRequest request, HttpServletResponse response) {
        RestfulResult restfulResult = new RestfulResult();

        try{
            Driver driver = createDrive();
            Session session = driver.session();

            session.run( "CREATE (a:Person {name: {name}, title: {title}})",
                    parameters( "name", "Arthur001", "title", "King001" ) );

            StatementResult result = session.run( "MATCH (a:Person) WHERE a.name = {name} " +
                            "RETURN a.name AS name, a.title AS title",
                    parameters( "name", "Arthur001" ) );

            while ( result.hasNext() )
            {
                Record record = result.next();
                System.out.println( record.get( "title" ).asString() + " " + record.get( "name" ).asString() + " " + record.get( "id" ).asString() );
            }

            session.close();
            driver.close();

        }catch(Exception e){
            restfulResult.setResult(Constants.RESULT_STATE_ERROR);
            restfulResult.setMessage(e.getMessage());
        }

        CommUtils.printDataJason(response, restfulResult);
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

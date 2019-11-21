package com.hdu.neo4jdemo.services;

import com.hdu.neo4jdemo.domain.Paper;

import java.util.Collection;
import java.util.Map;

public interface PaperService{

    Paper findByName(String name);

    Collection<Paper> findByAreaLike(int area);

    Map<String, Object> graph(int limit);

}
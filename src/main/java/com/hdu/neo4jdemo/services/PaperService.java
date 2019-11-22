package com.hdu.neo4jdemo.services;

import com.hdu.neo4jdemo.domain.Paper;

import java.util.Collection;
import java.util.List;

public interface PaperService{

    List<Paper> findByName1(String name, Integer limit);

    Collection<Paper> findByAreaLike(int area);

    Collection<Paper> graph(int limit);

}
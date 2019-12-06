package com.hdu.neo4jdemo.api.services.impl;

import com.hdu.neo4jdemo.api.repositories.PaperRepository;
import com.hdu.neo4jdemo.api.services.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaperServiceImpl implements PaperService {

    @Autowired
    private final PaperRepository paperRepository;

    public PaperServiceImpl(PaperRepository paperRepository) {
        this.paperRepository = paperRepository;
    }

}

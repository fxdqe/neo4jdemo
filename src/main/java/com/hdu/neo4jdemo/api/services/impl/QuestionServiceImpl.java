package com.hdu.neo4jdemo.api.services.impl;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.hdu.neo4jdemo.api.services.QuestionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

@Service
public class QuestionServiceImpl implements QuestionService {

    public String hanlptest(String question) {
        return getSegment(question);
    }

    private String getSegment(String query) {
        Segment segment = HanLP.newSegment();
        segment.enableOffset(true);
        List<String> segs = new ArrayList<>();
        List<Term> list = segment.seg(query);
        ListIterator<Term> lit = list.listIterator();
        while (lit.hasNext()) {
            Term t = lit.next();
            String seg = t.word + " " + t.nature + " " + t.offset;
            segs.add(seg);
        }
        return segs.toString();
    }
}

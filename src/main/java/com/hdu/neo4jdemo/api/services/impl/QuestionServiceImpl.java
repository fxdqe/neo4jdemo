package com.hdu.neo4jdemo.api.services.impl;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.hdu.neo4jdemo.api.process.QuestionProcess;
import com.hdu.neo4jdemo.api.repositories.AuthorRepository;
import com.hdu.neo4jdemo.api.repositories.JournalRepository;
import com.hdu.neo4jdemo.api.repositories.PaperRepository;
import com.hdu.neo4jdemo.api.services.QuestionService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PaperRepository paperRepository;

    @Autowired
    private JournalRepository journalRepository;

    public String hanlptest(String question) {
        return getSegment(question);
    }

    private String getSegment(String query) {
        Segment segment = HanLP.newSegment();
        segment.enableOffset(true);
        List<String> segs = new ArrayList<>();
        List<Term> list = segment.seg(query);
        for (Term t : list) {
            String seg = t.word + " " + t.nature + " " + t.offset;
            segs.add(seg);
        }
        return segs.toString();
    }

    //    public String answer(String question) throws NumberFormatException{
    public String answer(String question) {
        QuestionProcess questionProcess = new QuestionProcess();
        ArrayList<String> reStrings = (ArrayList<String>) questionProcess.analyQuery(question);
        int modelIndex = Integer.parseInt(reStrings.get(0));
        StringBuilder answer = new StringBuilder();
        String authorName;
        List<String> authorNames;
        List<String> paperNames;
        List<String> journalNames;
        Integer paperId;
        Integer authorId;
        String Abstract;
        String url;
        String unit;
        String organization;
        String journalName;
        int count = 0;
        // 匹配问题模板
        switch (modelIndex) {
            case 0:
                // 论文id -> 所有作者name
                paperId = Integer.parseInt(reStrings.get(1));
                authorNames = authorRepository.findNamesByPaperId(paperId);
                if (authorNames != null) {
                    ListIterator<String> lit = authorNames.listIterator();
                    while (lit.hasNext()) {
                        String res = lit.next();
                        if (count == 0)
                            answer.append(res);
                        else
                            answer.append(" ,").append(lit);
                        count++;
                    }
                }
                break;
            case 1:
                // 论文id -> 第一作者name
                paperId = Integer.parseInt(reStrings.get(1));
                authorName = authorRepository.findFIRSTByPaperId(paperId);
                if (authorName != null)
                    answer.append(authorName);
                break;
            case 2:
                // 论文id -> 第二作者name
                paperId = Integer.parseInt(reStrings.get(1));
                authorName = authorRepository.findSECONDByPaperId(paperId);
                if (authorName != null)
                    answer.append(authorName);
                break;
            case 3:
                // 论文id -> 第三作者name
                paperId = Integer.parseInt(reStrings.get(1));
                authorName = authorRepository.findTHIRDByPaperId(paperId);
                if (authorName != null)
                    answer.append(authorName);
                break;
            case 4:
                // 论文id -> 第四作者name
                paperId = Integer.parseInt(reStrings.get(1));
                authorName = authorRepository.findFOURTHByPaperId(paperId);
                if (authorName != null)
                    answer.append(authorName);
                break;
            case 5:
                // 作者name -> 论文name
                authorName = reStrings.get(1);
                paperNames = paperRepository.findByAuthorName(authorName);
                if (paperNames != null) {
                    ListIterator<String> lit = paperNames.listIterator();
                    while (lit.hasNext()) {
                        String res = lit.next();
                        if (count == 0)
                            answer.append(res);
                        else
                            answer.append(" ,").append(res);
                        count++;
                    }
                }
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                // 论文id -> 摘要
                paperId = Integer.parseInt(reStrings.get(1));
                Abstract = paperRepository.findAbstractById(paperId);
                if (Abstract != null)
                    answer.append(Abstract);
                break;
            case 10:
                // 论文id -> url
                paperId = Integer.parseInt(reStrings.get(1));
                url = paperRepository.findUrlById(paperId);
                if (url != null)
                    answer.append(url);
                break;
            case 11:

                break;
            case 12:
                // 作者id -> 作者name
                authorId = Integer.parseInt(reStrings.get(1));
                authorName = authorRepository.findNameById(authorId);
                if (authorName != null)
                    answer.append(authorName);
                break;
            case 13:
                // 作者name -> 作者id
                authorName = reStrings.get(1);
                authorId = authorRepository.findIdByName(authorName);
                if (authorId != null)
                    answer.append(authorId);
                break;
            case 14:
                // 论文name -> 论文unit
                break;
            case 15:
                // 论文id -> 论文unit
                paperId = Integer.parseInt(reStrings.get(1));
                unit = paperRepository.findUnitById(paperId);
                if (unit != null)
                    answer.append(unit);
                break;
            case 16:
                // 作者id -> 作者Organization
                authorId = Integer.parseInt(reStrings.get(1));
                organization = authorRepository.findOrganizationById(authorId);
                if (organization != null)
                    answer.append(organization);
                break;
            case 17:
                // 论文id -> 报刊name
                paperId = Integer.parseInt(reStrings.get(1));
                journalName = paperRepository.findJournalNameById(paperId);
                if (journalName != null)
                    answer.append(journalName);
                break;
            case 18:
                // 论文name -> 报刊name
                break;
            case 19:
                //作者name -> 报刊name
                authorName = reStrings.get(1);
                journalNames = authorRepository.findJournalNameByName(authorName);
                if (journalNames != null) {
                    ListIterator<String> lit = journalNames.listIterator();
                    while (lit.hasNext()) {
                        String res = lit.next();
                        if (count == 0)
                            answer.append(res);
                        else
                            answer.append(" ;").append(res);
                        count++;
                    }
                }
                break;
            case 20:
                // 作者id -> 报刊name
                authorId = Integer.parseInt(reStrings.get(1));
                journalNames = authorRepository.findJournalNameById(authorId);
                if (journalNames != null) {
                    ListIterator<String> lit = journalNames.listIterator();
                    while (lit.hasNext()) {
                        String res = lit.next();
                        if (count == 0)
                            answer.append(res);
                        else
                            answer.append(" ;").append(res);
                        count++;
                    }
                }
                break;
            case 21:
                // 报刊name -> 报刊url
                journalName = reStrings.get(1);
                url = journalRepository.findUrlByName(journalName);
                if (url != null)
                    answer.append(url);
                break;
            default:
                break;
        }

        String finalAnswer = answer.toString();
        System.out.println(finalAnswer);
        if (!finalAnswer.equals("") && !finalAnswer.equals("\n")) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("result", finalAnswer);
            String js = JSONObject.toJSONString(map);
            System.out.println(js);
            return js;
        } else {
            return "sorry,我没有找到你要的答案";
        }
    }
}

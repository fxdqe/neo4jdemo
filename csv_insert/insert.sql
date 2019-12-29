CREATE CONSTRAINT ON (p:paper) ASSERT p.pid IS UNIQUE;
CREATE CONSTRAINT ON (s:subject) ASSERT s.code IS UNIQUE;
CREATE CONSTRAINT ON (a:author) ASSERT a.cnki_code IS UNIQUE

LOAD CSV with HEADERS FROM 'file:///new_sep.csv' AS line WITH line 
WHERE NOT line.zw_subject_code IS NULL
CREATE(p:paper {pid: tointeger(line.id), paper_id: line.PAPER_ID, name: line.name,
	abstract: line.abstract, year: tointeger(line.year), 
	area:tointeger(line.area), url:line.url, classification: line.classification, 
	unit: line.unit, unit_type: line.unit_type})
MERGE (s:subject {code:line.zw_subject_code, name:line.subject_code})
MERGE (j:journal {name:line.journal_name, no:line.journal_no, 
	quality:line.journal_quality, url:line.journal_url})
    
CREATE (p)-[r:RELATED_TO]->(s)
CREATE (p)-[a:RELEASED_ON]->(j)

/////////////////////////////////////////////////////////////////////////////////
LOAD CSV with HEADERS FROM 'file:///new_sep.csv' AS line WITH line
WHERE line.zw_subject_code IS NULL
CREATE(p:paper {pid: tointeger(line.id), paper_id: line.PAPER_ID, name: line.name,
	abstract: line.abstract, year: tointeger(line.year), 
	area:tointeger(line.area), url:line.url, classification: line.classification, 
	unit: line.unit, unit_type: line.unit_type})
MERGE (s:subject {code:'null', name:'其他'})

CREATE (p)-[r:RELATED_TO]->(s)
////////////////////////////////////////////////////////////////////////////////



开始导入有cnki的作者



/////////////////////////////////////////////////////////////////////////////////

LOAD CSV with HEADERS FROM 'file:///new_sep.csv' AS line WITH line 
WITH line
where not line.first_author_code IS NULL
match (p:paper {pid: tointeger(line.id)})
MERGE (a:author {name:line.first_author, cnki_code:tointeger(line.first_author_code),baidu_code:tointeger(line.first_author_baidu)})

CREATE (a)-[r1:FIRST_AUTHOR]->(p)

////////////////////////////////////////////////////////////////////////////////
LOAD CSV with HEADERS FROM 'file:///new_sep.csv' AS line WITH line 
WITH line
where not line.second_author_code IS NULL and not line.second_author_baidu IS NULL
match (p:paper {pid: tointeger(line.id)})
MERGE (b:author {cnki_code:tointeger(line.second_author_code)})
ON CREATE SET b.name=line.second_author,b.baidu_code=tointeger(line.second_author_baidu)
CREATE (b)-[r2:SECOND_AUTHOR]->(p)

////////////////////////////////////////////////////////////////////////////////
LOAD CSV with HEADERS FROM 'file:///new_sep.csv' AS line WITH line 
WITH line
where not line.third_author_code IS NULL and not line.third_author_baidu IS NULL
match (p:paper {pid: tointeger(line.id)})
MERGE (c:author {cnki_code:tointeger(line.third_author_code)})
ON CREATE SET c.name=line.third_author,c.baidu_code=tointeger(line.third_author_baidu)
CREATE (c)-[r3:THIRD_AUTHOR]->(p)
/////////////////////////////////////////////////////////////////////////////////
LOAD CSV with HEADERS FROM 'file:///new_sep.csv' AS line WITH line 
WITH line
where not line.fourth_author_code IS NULL and not line.fourth_author_baidu IS NULL
match (p:paper {pid: tointeger(line.id)})
MERGE (d:author {cnki_code:tointeger(line.fourth_author_code)})
ON CREATE SET d.name=line.fourth_author,d.baidu_code=tointeger(line.fourth_author_baidu)
CREATE (d)-[r4:FOURTH_AUTHOR]->(p)
//////////////////////////////////////////////////////////////////////////////////



开始导入无cnki的作者



/////////////////////////////////////////////////////////////////////////////////

LOAD CSV with HEADERS FROM 'file:///new_sep.csv' AS line WITH line 
WITH line
where line.first_author_code IS NULL and line.first_author IS NOT NULL
match (p:paper {pid: tointeger(line.id)})
MERGE (a:author {name:line.first_author,baidu_code:tointeger(line.first_author_baidu)})

CREATE (a)-[r1:FIRST_AUTHOR]->(p)
////////////////////////////////////////////////////////////////////////////////

LOAD CSV with HEADERS FROM 'file:///new_sep.csv' AS line
WITH line
where line.second_author_code IS NULL and line.second_author IS NOT NULL
match (p:paper {pid: tointeger(line.id)})
MERGE (a:author {name:line.second_author,baidu_code:tointeger(line.second_author_baidu)})
CREATE (a)-[r2:SECOND_AUTHOR]->(p)

////////////////////////////////////////////////////////////////////////////////
LOAD CSV with HEADERS FROM 'file:///new_sep.csv' AS line
WITH line
where line.third_author_code IS NULL and line.third_author IS NOT NULL
match (p:paper {pid: tointeger(line.id)})
MERGE (a:author {name:line.third_author,baidu_code:tointeger(line.third_author_baidu)})
CREATE (a)-[r3:THIRD_AUTHOR]->(p)
/////////////////////////////////////////////////////////////////////////////////
LOAD CSV with HEADERS FROM 'file:///new_sep.csv' AS line
WITH line
where line.fourth_author_code IS NULL and line.fourth_author IS NOT NULL
match (p:paper {pid: tointeger(line.id)})
MERGE (a:author {name:line.fourth_author,baidu_code:tointeger(line.fourth_author_baidu)})
CREATE (a)-[r4:FOURTH_AUTHOR]->(p)

////////////////////////////////////////////////////////////////


导入unit


////////////////////////////////////////////////////////////////
LOAD CSV with HEADERS FROM 'file:///new_sep.csv' AS line WITH line
WITH line
where line.first_author IS NOT NULL and line.first_author_unit IS NOT NULL
match (a:author {name: line.first_author, baidu_code:line.first_author_baidu})
merge (o:organization {name: line.first_author_unit})
CREATE (a)-[r:RESEARCHED_IN]->(o)
////////////////////////////////////////////////////////////////
LOAD CSV with HEADERS FROM 'file:///new_sep.csv' AS line WITH line
WITH line
where line.second_author IS NOT NULL and line.second_author_unit IS NOT NULL
match (a:author {name: line.second_author, baidu_code:line.second_author_baidu})
merge (o:organization {name: line.second_author_unit})
CREATE (a)-[r:RESEARCHED_IN]->(o)
////////////////////////////////////////////////////////////////
LOAD CSV with HEADERS FROM 'file:///new_sep.csv' AS line WITH line
WITH line
where line.third_author IS NOT NULL and line.third_author_unit IS NOT NULL
match (a:author {name: line.third_author, baidu_code:line.third_author_baidu})
merge (o:organization {name: line.third_author_unit})
CREATE (a)-[r:RESEARCHED_IN]->(o)
////////////////////////////////////////////////////////////////
LOAD CSV with HEADERS FROM 'file:///new_sep.csv' AS line WITH line
WITH line
where line.fourth_author IS NOT NULL and line.fourth_author_unit IS NOT NULL
match (a:author {name: line.fourth_author, baidu_code:line.fourth_author_baidu})
merge (o:organization {name: line.fourth_author_unit})
CREATE (a)-[r:RESEARCHED_IN]->(o)
////////////////////////////////////////////////////////////////
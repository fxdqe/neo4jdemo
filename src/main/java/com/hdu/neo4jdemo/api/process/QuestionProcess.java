package com.hdu.neo4jdemo.api.process;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.classification.NaiveBayes;
import org.apache.spark.mllib.classification.NaiveBayesModel;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class QuestionProcess {

    /**
     * 分类标签号和问句模板对应表
     */
    private Map<Double, String> questionsPattern;

    /**
     * Spark贝叶斯分类器
     */
    private NaiveBayesModel nbModel;

    /**
     * 词语和下标的对应表   == 词汇表
     */
    private Map<String, Integer> vocabulary;

    /**
     * 关键字与其词性的map键值对集合 == 句子抽象
     */
    private Map<String, String> abstractMap;

    /**
     * 分类模板索引
     */
    private int modelIndex = 0;

    public QuestionProcess() {
        questionsPattern = loadQuestionsPattern();
        vocabulary = loadVocabulary();
        nbModel = loadClassifierModel();
    }

    public List<String> analyQuery(String queryString) {

        System.out.println("原始句子：" + queryString);
        System.out.println("========HanLP开始分词========");

        // 将关键字进行词性抽象
        String abstr = queryAbstract(queryString);
        System.out.println("句子抽象化结果：" + abstr);

        // 将抽象的句子与spark训练集中的模板进行匹配，拿到句子对应的模板
        String strPatt = queryClassify(abstr);
        System.out.println("句子套用模板结果：" + strPatt);

        // 模板还原成句子
        String finalPattern = queryExtenstion(strPatt);
        System.out.println("原始句子替换成系统可识别的结果：" + finalPattern);// 但丁密码 制作 导演列表

        ArrayList<String> resultList = new ArrayList<>();
        resultList.add(String.valueOf(modelIndex));
        String[] finalPatternArray = finalPattern.split(" ");
        resultList.addAll(Arrays.asList(finalPatternArray));
        return resultList;
    }

    private List<Term> querySeg(String text) {
        Segment segment = HanLP.newSegment();
        segment.enableOffset(true);
        segment.enableCustomDictionary(true);
        return segment.seg(text);
    }

    private String queryAbstract(String text) {
        List<Term> terms = querySeg(text);
        StringBuilder abstractQuery = new StringBuilder();
        abstractMap = new HashMap<>();
        for (Term term : terms) {
            String word = term.word;
            String nature = term.nature.toString();
            if (nature.equals("m")) {        //m 数词（数字）
                abstractQuery.append("m ");
                abstractMap.put("m", word);
            } else if (nature.equals("nr")) {    //nr 人名
                abstractQuery.append("nr ");
                abstractMap.put("nr", word);
            } else if (nature.equals("ORG")) {  //ORG  unit
                abstractQuery.append("ORG ");
                abstractMap.put("ORG", word);
            } else if (nature.equals("SUB") || word.equals("电子信息") || word.equals("生物医药")) { //SUB 学科Subject 分词分不出来，把"电子信息"和"生物医药"单独提取
                abstractQuery.append("SUB ");
                abstractMap.put("SUB", word);
            } else if (nature.equals("jr")) { //jr 期刊journal
                abstractQuery.append("jr ");
                abstractMap.put("jr", word);
            } else {
                abstractQuery.append(word).append(" ");
            }
        }
//        System.out.println(abstractQuery);
        return abstractQuery.toString();
    }

    public Map<String, Integer> loadVocabulary() {
        vocabulary = new HashMap<>();
        File file = new File("question/vocabulary.txt");
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(":");
                int index = Integer.parseInt(tokens[0]);
                String word = tokens[1];
                vocabulary.put(word, index);
            }
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
        return vocabulary;
    }

    // 生成贝叶斯模型
    private NaiveBayesModel loadClassifierModel() {
        //生成Spark对象
        SparkConf conf = new SparkConf().setAppName("NBAnalyse").setMaster("local[*]");
        //创建Spark核心RDD对象
        try (JavaSparkContext sc = new JavaSparkContext(conf)) {
            //生成训练集（局部向量，监督学习）
            List<LabeledPoint> labeledPointList = new LinkedList<>();
            String[] sentences = null;
            //开始导入样本集
            String timeQuestions = loadFile("./src/main/resources/question/1论文id_所有作者name.txt");
            sentences = timeQuestions.split("`");
            for (String sentence : sentences) {
                double[] array = sentenceToArrays(sentence);
                LabeledPoint labeledPoint = new LabeledPoint(0.0, Vectors.dense(array));
                labeledPointList.add(labeledPoint);
            }
            timeQuestions = loadFile("./src/main/resources/question/2论文id_第一作者name.txt");
            sentences = timeQuestions.split("`");
            for (String sentence : sentences) {
                double[] array = sentenceToArrays(sentence);
                LabeledPoint labeledPoint = new LabeledPoint(1.0, Vectors.dense(array));
                labeledPointList.add(labeledPoint);
            }
            timeQuestions = loadFile("./src/main/resources/question/3论文id_第二作者name.txt");
            sentences = timeQuestions.split("`");
            for (String sentence : sentences) {
                double[] array = sentenceToArrays(sentence);
                LabeledPoint labeledPoint = new LabeledPoint(2.0, Vectors.dense(array));
                labeledPointList.add(labeledPoint);
            }
            timeQuestions = loadFile("./src/main/resources/question/4论文id_第三作者name.txt");
            sentences = timeQuestions.split("`");
            for (String sentence : sentences) {
                double[] array = sentenceToArrays(sentence);
                LabeledPoint labeledPoint = new LabeledPoint(3.0, Vectors.dense(array));
                labeledPointList.add(labeledPoint);
            }
            timeQuestions = loadFile("./src/main/resources/question/5论文id_第四作者name.txt");
            sentences = timeQuestions.split("`");
            for (String sentence : sentences) {
                double[] array = sentenceToArrays(sentence);
                LabeledPoint labeledPoint = new LabeledPoint(4.0, Vectors.dense(array));
                labeledPointList.add(labeledPoint);
            }
            timeQuestions = loadFile("./src/main/resources/question/6作者name_论文name.txt");
            sentences = timeQuestions.split("`");
            for (String sentence : sentences) {
                double[] array = sentenceToArrays(sentence);
                LabeledPoint labeledPoint = new LabeledPoint(5.0, Vectors.dense(array));
                labeledPointList.add(labeledPoint);
            }
            timeQuestions = loadFile("./src/main/resources/question/7关键词_论文name.txt");
            sentences = timeQuestions.split("`");
            for (String sentence : sentences) {
                double[] array = sentenceToArrays(sentence);
                LabeledPoint labeledPoint = new LabeledPoint(6.0, Vectors.dense(array));
                labeledPointList.add(labeledPoint);
            }
            timeQuestions = loadFile("./src/main/resources/question/8论文name_论文id.txt");
            sentences = timeQuestions.split("`");
            for (String sentence : sentences) {
                double[] array = sentenceToArrays(sentence);
                LabeledPoint labeledPoint = new LabeledPoint(7.0, Vectors.dense(array));
                labeledPointList.add(labeledPoint);
            }
            timeQuestions = loadFile("./src/main/resources/question/9论文name_论文abst.txt");
            sentences = timeQuestions.split("`");
            for (String sentence : sentences) {
                double[] array = sentenceToArrays(sentence);
                LabeledPoint labeledPoint = new LabeledPoint(8.0, Vectors.dense(array));
                labeledPointList.add(labeledPoint);
            }
            timeQuestions = loadFile("./src/main/resources/question/10论文id_论文abst.txt");
            sentences = timeQuestions.split("`");
            for (String sentence : sentences) {
                double[] array = sentenceToArrays(sentence);
                LabeledPoint labeledPoint = new LabeledPoint(9.0, Vectors.dense(array));
                labeledPointList.add(labeledPoint);
            }
            timeQuestions = loadFile("./src/main/resources/question/11论文id_论文url.txt");
            sentences = timeQuestions.split("`");
            for (String sentence : sentences) {
                double[] array = sentenceToArrays(sentence);
                LabeledPoint labeledPoint = new LabeledPoint(10.0, Vectors.dense(array));
                labeledPointList.add(labeledPoint);
            }
            timeQuestions = loadFile("./src/main/resources/question/12论文name_论文url.txt");
            sentences = timeQuestions.split("`");
            for (String sentence : sentences) {
                double[] array = sentenceToArrays(sentence);
                LabeledPoint labeledPoint = new LabeledPoint(11.0, Vectors.dense(array));
                labeledPointList.add(labeledPoint);
            }
            timeQuestions = loadFile("./src/main/resources/question/13作者id_作者name.txt");
            sentences = timeQuestions.split("`");
            for (String sentence : sentences) {
                double[] array = sentenceToArrays(sentence);
                LabeledPoint labeledPoint = new LabeledPoint(12.0, Vectors.dense(array));
                labeledPointList.add(labeledPoint);
            }
            timeQuestions = loadFile("./src/main/resources/question/14作者name_作者id.txt");
            sentences = timeQuestions.split("`");
            for (String sentence : sentences) {
                double[] array = sentenceToArrays(sentence);
                LabeledPoint labeledPoint = new LabeledPoint(13.0, Vectors.dense(array));
                labeledPointList.add(labeledPoint);
            }
            timeQuestions = loadFile("./src/main/resources/question/15论文name_论文unit.txt");
            sentences = timeQuestions.split("`");
            for (String sentence : sentences) {
                double[] array = sentenceToArrays(sentence);
                LabeledPoint labeledPoint = new LabeledPoint(14.0, Vectors.dense(array));
                labeledPointList.add(labeledPoint);
            }
            timeQuestions = loadFile("./src/main/resources/question/16论文id_论文unit.txt");
            sentences = timeQuestions.split("`");
            for (String sentence : sentences) {
                double[] array = sentenceToArrays(sentence);
                LabeledPoint labeledPoint = new LabeledPoint(15.0, Vectors.dense(array));
                labeledPointList.add(labeledPoint);
            }
            timeQuestions = loadFile("./src/main/resources/question/17作者name_作者unit.txt");
            sentences = timeQuestions.split("`");
            for (String sentence : sentences) {
                double[] array = sentenceToArrays(sentence);
                LabeledPoint labeledPoint = new LabeledPoint(16.0, Vectors.dense(array));
                labeledPointList.add(labeledPoint);
            }
            timeQuestions = loadFile("./src/main/resources/question/18论文id_报刊.txt");
            sentences = timeQuestions.split("`");
            for (String sentence : sentences) {
                double[] array = sentenceToArrays(sentence);
                LabeledPoint labeledPoint = new LabeledPoint(17.0, Vectors.dense(array));
                labeledPointList.add(labeledPoint);
            }
            timeQuestions = loadFile("./src/main/resources/question/19作者name_报刊.txt");
            sentences = timeQuestions.split("`");
            for (String sentence : sentences) {
                double[] array = sentenceToArrays(sentence);
                LabeledPoint labeledPoint = new LabeledPoint(18.0, Vectors.dense(array));
                labeledPointList.add(labeledPoint);
            }
            timeQuestions = loadFile("./src/main/resources/question/20论文name_报刊.txt");
            sentences = timeQuestions.split("`");
            for (String sentence : sentences) {
                double[] array = sentenceToArrays(sentence);
                LabeledPoint labeledPoint = new LabeledPoint(19.0, Vectors.dense(array));
                labeledPointList.add(labeledPoint);
            }
            timeQuestions = loadFile("./src/main/resources/question/21报刊_url.txt");
            sentences = timeQuestions.split("`");
            for (String sentence : sentences) {
                double[] array = sentenceToArrays(sentence);
                LabeledPoint labeledPoint = new LabeledPoint(20.0, Vectors.dense(array));
                labeledPointList.add(labeledPoint);
            }
            //创建RDD
            JavaRDD<LabeledPoint> trainingRDD = sc.parallelize(labeledPointList);
            //学习模型
            return NaiveBayes.train(trainingRDD.rdd());
        }
    }

    private String loadFile(String filename) {
        File file = new File(filename);
        StringBuilder content = new StringBuilder();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                content.append(line).append("`");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    // 对数据进行预测predict 句子模板在 spark贝叶斯分类器中的索引【位置】根据词汇使用的频率推断出句子对应哪一个模板
    public String queryClassify(String sentence) {
        double[] testArray = sentenceToArrays(sentence);
        Vector v = Vectors.dense(testArray);

        double index = nbModel.predict(v);
        modelIndex = (int) index;
        System.out.println("the model index is " + index);
        Vector vRes = nbModel.predictProbabilities(v);
        System.out.println("问题模板分类各概率：" + Arrays.toString(vRes.toArray()));
        return questionsPattern.get(index);
    }

    private double[] sentenceToArrays(String sentence) {
        double[] vector = new double[vocabulary.size()];
        // 模板对照词汇表的大小进行初始化，全部为0.0
        for (int i = 0; i < vocabulary.size(); i++) {
            vector[i] = 0;
        }
        // 拿分词的结果和词汇表里面的关键特征进行匹配
        List<Term> terms = querySeg(sentence);
        for (Term term : terms) {
            String word = term.word;
            // 如果命中，0.0 改为 1.0
            if (vocabulary.containsKey(word)) {
                int index = vocabulary.get(word);
                vector[index] = 1;
            }
        }
        return vector;
    }

    // 句子还原
    private String queryExtenstion(String queryPattern) {
        Set<String> set = abstractMap.keySet();
        for (String key : set) {
            // 如果句子模板中含有抽象的词性
            if (queryPattern.contains(key)) {
                //则替换抽象词性为具体的值
                String value = abstractMap.get(key);
                queryPattern = queryPattern.replace(key, value);
            }
        }
        String extendedQuery = queryPattern;
        // 当前句子处理完，抽象map清空释放空间并置空，等待下一个句子的处理
        abstractMap.clear();
        abstractMap = null;
        return extendedQuery;
    }

    // 加载问题模板分类器标签
    private Map<Double, String> loadQuestionsPattern() {
        questionsPattern = new HashMap<>();
        File file = new File("question/question_classification.txt");
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(":");
                double index = Double.parseDouble(tokens[0]);
                String pattern = tokens[1];
                questionsPattern.put(index, pattern);
            }
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
        return questionsPattern;
    }
}

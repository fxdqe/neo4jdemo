import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.CustomDictionary;
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
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Test01 {

    public static void main(String[] args) throws IOException {

        //Hanlp分词器
        int a=0,b=0,c=0,d=0,e=0,f=0;
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String lineStr = bf.readLine();
        try{
            Segment segment = HanLP.newSegment();
            segment.enableCustomDictionary(true);
            /**
             * 自定义分词+词性
             */
            CustomDictionary.add("作者","n0 0");
            CustomDictionary.add("论文","n1 0");
            CustomDictionary.add("时间","n2 0");
            CustomDictionary.add("ID","n3 0");
            CustomDictionary.add("id","n3 0");
            CustomDictionary.add("Id","n3 0");
            CustomDictionary.add("学校","n4 0");
            CustomDictionary.add("谁","n5 0");
            List<Term> seg = segment.seg(lineStr);

            for (Term term : seg) {
                System.out.println(term.toString());
                if(term.toString().equals("作者/n0")){ a = 1;}
                if(term.toString().equals("论文/n1")){ b = 1;}
                if(term.toString().equals("时间/n2")){ c = 1;}
                if(term.toString().equals("Id/n3")||term.toString().equals("ID/n3")||term.toString().equals("id/n3")){ d  = 1;}
                if(term.toString().equals("学校/n4")){ e = 1;}
                if(term.toString().equals("谁/n5")){ f = 1;}
                System.out.println("a:"+a+",b:"+b+",c:"+c+",d:"+d+",e:"+e+",f："+f);
            }
        }catch(Exception ex){
            System.out.println(ex.getClass()+","+ex.getMessage());
        }



        //朴素贝叶斯分词器
        /**
         * 本地模式，*表示启用多个线程并行计算
         */
        SparkConf conf = new SparkConf().setAppName("NaiveBayesTest").setMaster("local[*]");
        JavaSparkContext sc = new JavaSparkContext(conf);
        /**
         * MLlib的本地向量主要分为两种，DenseVector和SparseVector
         * 前者是用来保存稠密向量，后者是用来保存稀疏向量
         */
        //此处定义向量（作者,论文,时间,id,学校,谁），1表示有，0表示无
        //稠密向量 == 连续的
//        Vector vMale = Vectors.dense(1,0,1,0,1,0)
        Vector n0 = Vectors.dense(1,0,0,0,0,0);
        Vector n1 = Vectors.dense(0,1,0,0,0,0);
        Vector n2 = Vectors.dense(0,0,1,0,0,0);
        Vector n3 = Vectors.dense(0,0,0,1,0,0);
        Vector n4 = Vectors.dense(1,0,0,0,1,0);
        Vector n5 = Vectors.dense(1,0,0,1,0,0);
        Vector n6 = Vectors.dense(1,1,0,0,0,1);
        //稀疏向量 == 间隔的、指定的，未指定位置的向量值默认 = 0.0
//        int len = 6;
//        int[] index = new int[]{0,1,2,3,5};
//        double[] values = new double[]{1,1,1,1,1};
        //索引0、1、2、3、5位置上的向量值=1，索引4没给出，默认0
//        Vector vFemale = Vectors.sparse(len, index, values);
        //System.err.println("vFemale == "+vFemale);
        /**
         * labeled point 是一个局部向量，要么是密集型的要么是稀疏型的
         * 用一个label/response进行关联
         * 在MLlib里，labeled points 被用来监督学习算法
         * 我们使用一个double数来存储一个label，因此我们能够使用labeled points进行回归和分类
         * 在二进制分类里，一个label可以是 0（负数）或者 1（正数）
         * 在多级分类中，labels可以是class的索引，从0开始：0,1,2,......
         */

        //训练集生成 ，规定数据结构为LabeledPoint == 构建方式:稠密向量模式  ，1.0:类别编号 == 男性
//        LabeledPoint train_one = new LabeledPoint(1.0,vMale);  //(1.0, 0.0, 1.0, 0.0, 1.0, 0.0）
        //训练集生成 ，规定数据结构为LabeledPoint == 构建方式:稀疏向量模式  ，2.0:类别编号 == 女性
//        LabeledPoint train_two = new LabeledPoint(2.0,vFemale); //(1.0, 1.0, 1.0, 1.0, 0.0, 1.0）
        //我们也可以给同一个类别增加多个训练集
//        LabeledPoint train_three = new LabeledPoint(2.0,Vectors.dense(0,1,1,1,0,1));
        LabeledPoint train_one = new LabeledPoint(0.0,n0);
        LabeledPoint train_two = new LabeledPoint(1.0,n1);
        LabeledPoint train_three = new LabeledPoint(2.0,n2);
        LabeledPoint train_four = new LabeledPoint(3.0,n3);
        LabeledPoint train_five = new LabeledPoint(4.0,n4);
        LabeledPoint train_six = new LabeledPoint(3.0,n5);
        LabeledPoint train_seven = new LabeledPoint(0.0,n6);





        //List存放训练集【三个训练样本数据】
        List<LabeledPoint> trains = new ArrayList<>();
        trains.add(train_one);
        trains.add(train_two);
        trains.add(train_three);
        trains.add(train_four);
        trains.add(train_five);
        trains.add(train_six);
        trains.add(train_seven);

        /**
         * SPARK的核心是RDD(弹性分布式数据集)
         * Spark是Scala写的,JavaRDD就是Spark为Java写的一套API
         * JavaSparkContext sc = new JavaSparkContext(sparkConf);    //对应JavaRDD
         * SparkContext	    sc = new SparkContext(sparkConf)    ;    //对应RDD
         * 数据类型为LabeledPoint
         */
        JavaRDD<LabeledPoint> trainingRDD = sc.parallelize(trains);

        /**
         * 利用Spark进行数据分析时，数据一般要转化为RDD
         * JavaRDD转Spark的RDD
         */
        NaiveBayesModel nb_model = NaiveBayes.train(trainingRDD.rdd());

        //测试集生成  == 以下的向量表示，这个人具有特征
        double []  dTest = {a,b,c,d,e,f};
        Vector vTest =  Vectors.dense(dTest);//测试对象为单个vector，或者是RDD化后的vector

        //朴素贝叶斯用法
        int modelIndex =(int) nb_model.predict(vTest);
        System.out.println("标签分类编号："+modelIndex);// 分类结果 == 返回分类的标签值
        /**
         * 计算测试目标向量与训练样本数据集里面对应的各个分类标签匹配的概率结果
         */
        System.out.println(nb_model.predictProbabilities(vTest));
        if(modelIndex == 0){
            System.out.println("答案：贝叶斯分类器推断你问的是作者");
        }else if(modelIndex == 1){
            System.out.println("答案：贝叶斯分类器推断你问的是论文");
        }else if(modelIndex == 2){
            System.out.println("答案：贝叶斯分类器推断你问的是时间");
        }else if(modelIndex == 3){
            System.out.println("答案：贝叶斯分类器推断你问的是是id");
        }else if(modelIndex == 4){
            System.out.println("答案：贝叶斯分类器推断你问的是学校");
        }
        //最后不要忘了释放资源
        sc.close();
    }

}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package learning.naivebayes.io;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import learning.naivebayes.Classification;
import learning.stats.*;
import learning.Constants;
import learning.util.Utilities;

import org.apache.log4j.*;

//import toolbox.stats.DataList;
import toolbox.stats.DataList;
import toolbox.information.Shannon;

/**
 *
 * @author paul
 */
public class CSVReaderTest {
    
    private static Logger logger;
    private static final int SURVIVED_COLUMN = 1;
    public static final String LINE_ONE = "1,0,3,\"Braund, Mr. Owen Harris\",male,22,1,0,A/5 21171,7.25,,S,FALSE";
    
    public CSVReaderTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        logger = Logger.getLogger(CSVReaderTest.class);
        logger.addAppender(new ConsoleAppender(new PatternLayout( Constants.DEFAULT_LOG_FORMAT)));
        logger.setLevel(Level.DEBUG);
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testLoadFromFile() {
        logger.info("\ntesting loadFromFile()");
        String filename = "titanic.csv";
        //filename = "titanic.csv";
        List<List> data = (List<List>)CSVReader.loadFromFile(filename, 10000);
        logger.debug(data.size());
        int count = 0;
        StringBuilder sb = new StringBuilder();
        for(List features : data) {
            sb = new StringBuilder();
            sb.append(count++).append(" ");
            int i = 0;
            for(Object feature : features) {
                sb.append(i++).append(" ").append(feature).append(" ");
            }
            logger.debug(sb);
        }
    }

    @Test
    public void testGetDistributions() {
        logger.info("\ntesting getDistributions()");
        int[] featureColumns = { 2, 5, 13 };//{ 2, 5, 13 };
        try {
            ProbDist<Classification> result = CSVReader.getDistributions("titanic.csv", SURVIVED_COLUMN, featureColumns, Constants.DEFAULT_SEPARATOR);
            //result.display();
            List<Classification> classifications = result.getValues();
            List<Double> probs = result.getProbabilities();
            for(Classification c : classifications) {
                logger.debug(c.getName());
                List<ProbDist> featureDists = c.getFeatureCPDs();
                for(ProbDist dist : featureDists) {
                    //dist.display();
                }
                logger.debug("");
            }
            //TODO:  supply data distributions
            /*learning.titanic.TitanicDistData tdd = new learning.titanic.TitanicDistData();
            ProbDist<Classification> titanicDists = tdd.getClassificationDist();
            classifications = titanicDists.getValues();
            for(Classification c : classifications) {
                logger.debug(c.getName());
                List<ProbDist> featureDists = c.getFeatureCPDs();
                for(ProbDist dist : featureDists) {
                    dist.display();
                }
                logger.debug();
            }*/
        } catch(Exception e) {
            logger.error(e.getClass() + " in testGetDistributions():  " + e.getMessage());
        }
    }
    
    @Test
    public void testGetClassificationDists() {
        logger.info("\ntesting getClassificationDists()");
        
        //TODO:  Test bad inputs.
        try {
            ProbDist<String> result = CSVReader.getClassificationDists("titanic.csv", SURVIVED_COLUMN, Constants.DEFAULT_SEPARATOR);
            logger.debug(result.toString());
            assertEquals(2, result.getProbabilities().size());
            assertEquals(.6161, result.getProbabilities().get(result.getValues().indexOf("0")), .001);
            assertEquals(.3838, result.getProbabilities().get(result.getValues().indexOf("1")), .001);
        } catch(IOException e) {
            fail(e.getClass() + " " + e.getMessage());
        }
    }
    
    @Test
    public void testGetFeatureDists() {
        logger.info("\ntesting getFeatureDists()");
        int[] featureColumns = { 2, 5, 13 };
        try {
            List<ProbDist> dists = CSVReader.getFeatureDists("titanic.csv", SURVIVED_COLUMN, "0", featureColumns, Constants.DEFAULT_SEPARATOR);
            //Utilities.showList(dists);
            assertEquals(.1457, (Double)dists.get(0).getProbabilities().get(1), .0001);
            assertEquals(.1457, dists.get(0).probatilityOf("1"), .0001);
            assertEquals(.8525, dists.get(1).probatilityOf("male"), .001);
            
            dists = CSVReader.getFeatureDists("titanic.csv", SURVIVED_COLUMN, "1", featureColumns, Constants.DEFAULT_SEPARATOR);
            //Utilities.showList(dists);
        } catch(Exception e) {
            logger.error(e.getClass() + " " + e.getMessage());
        }
    }
    
    @Test
    public void testParseFile() {
        logger.info("\ntesting parseFile()");
        int[] featureColumns = { 3, 6, 13 };
        //TODO:  fill this function out
        try {
            List<List<String>> data = (List<List<String>>)CSVReader.parseFile("titanic.csv", SURVIVED_COLUMN, "0", featureColumns, Constants.DEFAULT_SEPARATOR);
            for(List<String> curList : data) {
                //logger.debug(curList.size());
                //Utilities.showList(curList);
            }
        } catch(Exception e) {
            logger.error(e.getClass() + " " + e.getMessage());
        }
    }
    
    @Test
    public void testParseLineInt() {
        logger.info("\ntesting parseLine(String line, int column, String columnSeparator)");
        //String result = "result";
        assertEquals(Constants.UNKNOWN, CSVReader.parseLine(null, -1, null));
        assertEquals(Constants.UNKNOWN, CSVReader.parseLine(LINE_ONE, -1, null));
        assertEquals(Constants.UNKNOWN, CSVReader.parseLine(LINE_ONE, 0, null));
        assertEquals(Constants.UNKNOWN, CSVReader.parseLine(LINE_ONE, -1, Constants.DEFAULT_SEPARATOR));
        assertEquals(Constants.UNKNOWN, CSVReader.parseLine(LINE_ONE, 1, null));
        
        assertEquals("0", CSVReader.parseLine(LINE_ONE, 1, Constants.DEFAULT_SEPARATOR));
        assertEquals(Constants.UNKNOWN, CSVReader.parseLine("", 2, Constants.DEFAULT_SEPARATOR));
    }
        
    @Test
    public void testParseLine() {
        logger.info("\ntesting parseLine(String line, int[] columns, String columnSeparator)");
        assertEquals(0, CSVReader.parseLine(null, null, null).size());
        assertEquals(0, CSVReader.parseLine("", null, null).size());
        assertEquals(0, CSVReader.parseLine(LINE_ONE, null, null).size());
        assertEquals(0, CSVReader.parseLine(LINE_ONE, new int[0], null).size());
        
        int[] columns = new int[] { 2, 5, 13 };
        List<String> result = null;
        result = CSVReader.parseLine(LINE_ONE, columns, null);
        result = CSVReader.parseLine(LINE_ONE, columns, "");
        assertEquals(0, result.size());
        //Utilities.showList(result);
        
        //now the legit inputs
        result = CSVReader.parseLine(LINE_ONE, columns, ",");
        assertEquals(3, result.size());
        //Utilities.showList(result);
        assertEquals("3", result.get(0));
        assertEquals("male", result.get(1));
        assertEquals("FALSE", result.get(2));
    }

    @Test
    public void testVerifyParameters() throws Exception {
    }
    
    @Test
    public void testGetSingleColumn() {
        logger.info("\ntesting getSingleColumn()");
        DataList<String> result = null;
        
        try {
            result = CSVReader.getSingleColumn("titanic.csv", 2, ",");
            assertEquals(891, result.size());
            for(int i = 0; i < 5; i++) {
                logger.debug(result.get(i));
            }
            assertEquals("3", result.get(0));
            assertEquals("1", result.get(1));
            assertEquals("3", result.get(2));
            assertEquals("1", result.get(3));
            assertEquals("3", result.get(4));
            assertEquals("3", result.get(5));
            
            assertEquals("2", result.get(886));
            assertEquals("1", result.get(887));
        } catch(IOException e) {
            logger.error(e.getClass() + " in testGetSingleColumn():  " + e.getMessage());
        }
        
        try {
            result = CSVReader.getSingleColumn("titanic.csv", 5, ",");
            assertEquals(891, result.size());
            for(int i = 0; i < 5; i++) {
                logger.debug(result.get(i));
            }
            assertEquals("male", result.get(0));
            assertEquals("female", result.get(1));
            assertEquals("female", result.get(2));
            assertEquals("female", result.get(3));
            assertEquals("male", result.get(4));
            assertEquals("male", result.get(5));
            
            assertEquals("male", result.get(886));
            assertEquals("female", result.get(887));
        } catch(IOException e) {
            logger.error(e.getClass() + " in testGetSingleColumn():  " + e.getMessage());
        }
    }
    
    @Test
    public void testGetSingleHistogram() {
        logger.info("\ntesting getSingleHistogram()");
        Histogram result = null;
        try {
            result = CSVReader.getSingleHistogram(null, 1, ",");
            assertEquals(0, result.getCounts().size());
            result = CSVReader.getSingleHistogram("titanic.csv", -1, ",");
            logger.debug(result.toString());
            assertEquals(0, result.getCounts().size());
            result = CSVReader.getSingleHistogram("titanic.csv", 2, null);
            logger.debug(result.toString());
            assertEquals(0, result.getCounts().size());
            
            result = CSVReader.getSingleHistogram("titanic.csv", 2, ",");
            logger.debug(result.toString());
            assertEquals(3, result.getCounts().size());
        } catch(IOException e) {
            logger.error(e.getClass() + " " + e.getMessage());
        }
    }

    @Test
    public void testGetSingleProbDist() {
        logger.info("\ntesting getSingleProbDist()");
        try {
            ProbDist result = null;
            result = CSVReader.getSingleDistribution("titanic.csv", 2, ",");
            logger.debug(result.toString());
        } catch(IOException e) {
            logger.error(e.getClass() + " " + e.getMessage());
        }
    }
    
    @Test
    public void testContains() {
        logger.info("\ncontains");
        ArrayList<String> list1 = new ArrayList<String>();
        list1.add("one");
        list1.add("two");
        assertEquals(true, list1.contains("one"));
        assertEquals(false, list1.contains("three"));
        
        ArrayList<ArrayList<String>> list2 = new ArrayList<ArrayList<String>>();
        ArrayList<String> l1 = new ArrayList<String>();
        l1.add("a");
        l1.add("b");
        ArrayList<String> l2 = new ArrayList<String>();
        l2.add("a");
        l2.add("c");
        ArrayList<String> l3 = new ArrayList<String>();
        l3.add("a");
        l3.add("b");
        ArrayList<String> l4 = new ArrayList<String>();
        l4.add("b");
        l4.add("a");
        
        list2.add(l1);
        //list2.add(l2);
        //list2.add(l3);
        logger.debug(list2.contains(l1));
        logger.debug(list2.contains(l2));
        logger.debug(list2.contains(l3));
        logger.debug(list2.contains(l4));
    }
    
    @Test
    public void testGetJointHistogram() {
        logger.info("\ntesting getJointHistogram()");
        try {
            Histogram result = null;
            result = CSVReader.getJointHistogram("titanic.csv", new int[] { 2, 5 }, ",");
            logger.debug(result.toString());
            logger.debug(result.getEntropy());
        } catch(IOException e) {
            logger.error(e.getClass() + " " + e.getMessage());
        }
    }
    
    @Test
    public void testGetJointDistribution() {
        logger.info("\ntesting getJointDistribution()");
        try {
            ProbDist result = null;
            result = CSVReader.getJointDistribution("titanic.csv", new int[] { 2, 5 }, ",");
            logger.debug(result.toString());
        } catch(IOException e) {
            logger.error(e.getClass() + " " + e.getMessage());
        }
    }
    
    @Test
    public void testGetMutualInformation() {
        logger.info("\ntesting getMutualInformation()");
        try {
            double result = 0.0;
            
            logger.debug("first with CSVReader.getMutualInforation():");
            //CLASS(2), SEX(5), SIBSP(7), PARCH(8), EMBARKED(12), ISCHILD(13);
            logger.debug("\nclass and sex:  " + CSVReader.getMutualInformation("titanic.csv", 2, 5, ","));
            logger.debug("class and sibsp:  " + CSVReader.getMutualInformation("titanic.csv", 2, 7, ","));
            logger.debug("class and parch:  " + CSVReader.getMutualInformation("titanic.csv", 2, 8, ","));
            logger.debug("class and port:  " + CSVReader.getMutualInformation("titanic.csv", 2, 12, ","));
            logger.debug("class and child:  " + CSVReader.getMutualInformation("titanic.csv", 2, 13, ","));
            
            logger.debug("sex and sibsp:  " + CSVReader.getMutualInformation("titanic.csv", 5, 7, ","));
            logger.debug("sex and parch:  " + CSVReader.getMutualInformation("titanic.csv", 5, 8, ","));
            logger.debug("sex and port:  " + CSVReader.getMutualInformation("titanic.csv", 5, 12, ","));
            logger.debug("sex and child:  " + CSVReader.getMutualInformation("titanic.csv", 5, 13, ","));
            
            logger.debug("sibsp and parch:  " + CSVReader.getMutualInformation("titanic.csv", 7, 8, ","));
            logger.debug("sibsp and port:  " + CSVReader.getMutualInformation("titanic.csv", 7, 12, ","));
            logger.debug("sibsp and child:  " + CSVReader.getMutualInformation("titanic.csv", 7, 13, ","));
            
            logger.debug("parch and port:  " + CSVReader.getMutualInformation("titanic.csv", 8, 12, ","));
            logger.debug("parch and child:  " + CSVReader.getMutualInformation("titanic.csv", 8, 13, ","));
            
            logger.debug("port and child:  " + CSVReader.getMutualInformation("titanic.csv", 12, 13, ","));

            logger.debug("\n--\nnow with Shannon.getMutualInformation():");
            DataList<String> pclass = CSVReader.getSingleColumn("titanic.csv", 2, ",");
            DataList<String> sex = CSVReader.getSingleColumn("titanic.csv", 5, ",");
            DataList<String> sibsp = CSVReader.getSingleColumn("titanic.csv", 7, ",");
            DataList<String> parch = CSVReader.getSingleColumn("titanic.csv", 8, ",");
            DataList<String> port = CSVReader.getSingleColumn("titanic.csv", 12, ",");
            DataList<String> child = CSVReader.getSingleColumn("titanic.csv", 13, ",");
            
            logger.debug("class and sex:  " + Shannon.getMutualInformation(pclass.getData(), sex.getData()));
            logger.debug("class and sibsp:  " + Shannon.getMutualInformation(pclass.getData(), sibsp.getData()));
            logger.debug("class and parch:  " + Shannon.getMutualInformation(pclass.getData(), parch.getData()));
            logger.debug("class and port:  " + Shannon.getMutualInformation(pclass.getData(), port.getData()));
            logger.debug("class and child:  " + Shannon.getMutualInformation(pclass.getData(), child.getData()));
            
            logger.debug("sex and sibsp:  " + Shannon.getMutualInformation(sex.getData(), sibsp.getData()));
            logger.debug("sex and parch:  " + Shannon.getMutualInformation(sex.getData(), parch.getData()));
            logger.debug("sex and port:  " + Shannon.getMutualInformation(sex.getData(), port.getData()));
            logger.debug("sex and child:  " + Shannon.getMutualInformation(sex.getData(), child.getData()));
            
            logger.debug("sibsp and parch:  " + Shannon.getMutualInformation(sibsp.getData(), parch.getData()));
            logger.debug("sibsp and port:  " + Shannon.getMutualInformation(sibsp.getData(), port.getData()));
            logger.debug("sibsp and child:  " + Shannon.getMutualInformation(sibsp.getData(), child.getData()));
            
            logger.debug("parch and port:  " + Shannon.getMutualInformation(parch.getData(), port.getData()));
            logger.debug("parch and child:  " + Shannon.getMutualInformation(parch.getData(), child.getData()));
            
            logger.debug("port and child:  " + Shannon.getMutualInformation(port.getData(), child.getData()));
            
        } catch(IOException e) {
            logger.error(e.getClass() + " in getGetMutualInformation(): " + e.getMessage());
        }
        logger.debug("----");
    }
    @Test
    public void testScrub() {
    }
}
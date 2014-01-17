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
import learning.naivebayes.Classifier;
import static learning.naivebayes.ClassifierTest.LINE_ONE;
import static learning.naivebayes.ClassifierTest.SURVIVED_COLUMN;
import learning.stats.*;
import learning.titanic.Constants;
import learning.util.Utilities;

/**
 *
 * @author paul
 */
public class CSVReaderTest {
    
    public CSVReaderTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
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

    //@Test
    public void testLoadFromFile() {
        System.out.println("\ntesting loadFromFile()");
        String filename = "naive4.csv";
        List<List> data = (List<List>)CSVReader.loadFromFile(filename, 10000);
        System.out.println(data.size());
        int count = 0;
        for(List features : data) {
            System.out.print(count++ + " ");
            int i = 0;
            for(Object feature : features) {
                System.out.print(i++ + " " + feature + " ");
            }
            System.out.println();
        }
    }

    //@Test
    public void testGetDistributions() {
        System.out.println("\ntesting getDistributions()");
        int[] featureColumns = { 3, 6, 14 };
        try {
            ProbDist<Classification> result = CSVReader.getDistributions("titanic.csv", SURVIVED_COLUMN, featureColumns, Constants.DEFAULT_SEPARATOR);
            //result.display();
            List<Classification> classifications = result.getValues();
            List<Double> probs = result.getProbabilities();
            for(Classification c : classifications) {
                System.out.println(c.getName());
                List<ProbDist> featureDists = c.getFeatureCPDs();
                for(ProbDist dist : featureDists) {
                    dist.display();
                }
                System.out.println();
            }
            
            learning.titanic.TitanicDistData tdd = new learning.titanic.TitanicDistData();
            ProbDist<Classification> titanicDists = tdd.getClassificationDist();
            classifications = titanicDists.getValues();
            for(Classification c : classifications) {
                System.out.println(c.getName());
                List<ProbDist> featureDists = c.getFeatureCPDs();
                for(ProbDist dist : featureDists) {
                    dist.display();
                }
                System.out.println();
            }
        } catch(Exception e) {
            System.err.println(e.getClass() + " in testGetDistributions():  " + e.getMessage());
        }
    }
    
    //@Test
    public void testGetClassificationDists() {
        System.out.println("\ntesting getClassificationDists()");
        
        //TODO:  Test bad inputs.
        try {
            ProbDist<String> result = CSVReader.getClassificationDists("titanic.csv", SURVIVED_COLUMN, Constants.DEFAULT_SEPARATOR);
            //result.display();
            assertEquals(2, result.getProbabilities().size());
            assertEquals(.6161, result.getProbabilities().get(result.getValues().indexOf("0")), .001);
            assertEquals(.3838, result.getProbabilities().get(result.getValues().indexOf("1")), .001);
        } catch(IOException e) {
            fail(e.getClass() + " " + e.getMessage());
        }
    }
    
    //@Test
    public void testGetFeatureDists() {
        System.out.println("\ntesting getFeatureDists()");
        int[] featureColumns = { 3, 6, 14 };
        try {
            List<ProbDist> dists = CSVReader.getFeatureDists("titanic.csv", SURVIVED_COLUMN, "0", featureColumns, Constants.DEFAULT_SEPARATOR);
            //System.out.println("dists.size() == " + dists.size());
            //System.out.println("died");
            //Utilities.showList(dists);
            //assertEquals(.1457, (Double)dists.get(0).getProbabilities().get(1), .0001);
            assertEquals(.1457, dists.get(0).probatilityOf("1"), .0001);
            assertEquals(.8525, dists.get(1).probatilityOf("male"), .001);
            //System.out.println("survived");
            dists = CSVReader.getFeatureDists("titanic.csv", SURVIVED_COLUMN, "1", featureColumns, Constants.DEFAULT_SEPARATOR);
            //Utilities.showList(dists);
        } catch(Exception e) {
            System.err.println(e.getClass() + " " + e.getMessage());
        }
    }
    
    //@Test
    public void testParseFile() {
        System.out.println("\ntesting parseFile()");
        int[] featureColumns = { 3, 6, 14 };
        //TODO:  fill this function out
        try {
            List<List<String>> data = (List<List<String>>)CSVReader.parseFile("titanic.csv", SURVIVED_COLUMN, "0", featureColumns, Constants.DEFAULT_SEPARATOR);
            for(List<String> curList : data) {
                //System.out.println(curList.size());
                //Utilities.showList(curList);
            }
        } catch(Exception e) {
            System.err.println(e.getClass() + " " + e.getMessage());
        }
    }
    
    //@Test
    public void testParseLineInt() {
        System.out.println("\ntesting parseLine(String line, int column, String columnSeparator)");
        //String result = "result";
        assertEquals(Constants.UNKNOWN, CSVReader.parseLine(null, -1, null));
        assertEquals(Constants.UNKNOWN, CSVReader.parseLine(LINE_ONE, -1, null));
        assertEquals(Constants.UNKNOWN, CSVReader.parseLine(LINE_ONE, 0, null));
        assertEquals(Constants.UNKNOWN, CSVReader.parseLine(LINE_ONE, -1, Constants.DEFAULT_SEPARATOR));
        assertEquals("", CSVReader.parseLine(LINE_ONE, 0, Constants.DEFAULT_SEPARATOR));
        assertEquals(Constants.UNKNOWN, CSVReader.parseLine(LINE_ONE, 1, null));
        
        assertEquals("0", CSVReader.parseLine(LINE_ONE, 2, Constants.DEFAULT_SEPARATOR));
        assertEquals(Constants.UNKNOWN, CSVReader.parseLine("", 2, Constants.DEFAULT_SEPARATOR));
    }
        
    //@Test
    public void testParseLine() {
        System.out.println("\ntesting parseLine(String line, int[] columns, String columnSeparator)");
        assertEquals(0, CSVReader.parseLine(null, null, null).size());
        assertEquals(0, CSVReader.parseLine("", null, null).size());
        assertEquals(0, CSVReader.parseLine(LINE_ONE, null, null).size());
        assertEquals(0, CSVReader.parseLine(LINE_ONE, new int[0], null).size());
        
        int[] columns = new int[] { 3, 6, 14 };
        List<String> result = null;
        result = CSVReader.parseLine(LINE_ONE, columns, null);
        result = CSVReader.parseLine(LINE_ONE, columns, "");
        assertEquals(0, result.size());
        Utilities.showList(result);
        
        //now the legit inputs
        result = CSVReader.parseLine(LINE_ONE, columns, ",");
        assertEquals(3, result.size());
        //showList(result);
        assertEquals("3", result.get(0));
        assertEquals("male", result.get(1));
        assertEquals("FALSE", result.get(2));
    }

    @Test
    public void testVerifyParameters() throws Exception {
    }
    
    @Test
    public void testGetSingleHistogram() {
        System.out.println("\ntesting getSingleHistogram()");
        Histogram result = null;
        try {
            result = CSVReader.getSingleHistogram(null, 1, ",");
            assertEquals(0, result.getCounts().size());
            result = CSVReader.getSingleHistogram("titanic.csv", -1, ",");
            System.out.println(result.toString());
            assertEquals(0, result.getCounts().size());
            result = CSVReader.getSingleHistogram("titanic.csv", 2, null);
            System.out.println(result.toString());
            assertEquals(0, result.getCounts().size());
            
            result = CSVReader.getSingleHistogram("titanic.csv", 2, ",");
            result.display();
            assertEquals(3, result.getCounts().size());
        } catch(IOException e) {
            System.err.println(e.getClass() + " " + e.getMessage());
        }
    }

    @Test
    public void testGetSingleProbDist() {
        System.out.println("\ntesting getSingleProbDist()");
        try {
            ProbDist result = null;
            result = CSVReader.getSingleDistribution("titanic.csv", 2, ",");
            result.display();
        } catch(IOException e) {
            System.err.println(e.getClass() + " " + e.getMessage());
        }
    }
    
    @Test
    public void testContains() {
        System.out.println("\ncontains");
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
        System.out.println(list2.contains(l1));
        System.out.println(list2.contains(l2));
        System.out.println(list2.contains(l3));
        System.out.println(list2.contains(l4));
    }
    
    @Test
    public void testGetJointHistogram() {
        System.out.println("\ntesting getJointHistogram()");
        try {
            Histogram result = null;
            result = CSVReader.getJointHistogram("titanic.csv", new int[] { 2, 5 }, ",");
            result.display();
            System.out.println(result.getEntropy());
        } catch(IOException e) {
            System.err.println(e.getClass() + " " + e.getMessage());
        }
    }
    
    @Test
    public void testGetJointDistribution() {
        System.out.println("\ntesting getJointDistribution()");
        try {
            ProbDist result = null;
            result = CSVReader.getJointDistribution("titanic.csv", new int[] { 2, 5 }, ",");
            result.display();
        } catch(IOException e) {
            System.err.println(e.getClass() + " " + e.getMessage());
        }
    }
    
    @Test
    public void testGetMutualInformation() {
        System.out.println("\ntesting getMutualInformation()");
        try {
            double result = 0.0;
            //result = CSVReader.getMutualInformation("titanic.csv", 2, 5, ",");
            //System.out.println(result);
            
            //result = CSVReader.getMutualInformation("titanic.csv", 0, 1, ",");
            //System.out.println(CSVReader.getJointHistogram("titanic.csv", new int[] { 0, 1 }, ","));
            //System.out.println(result);
            
            System.out.println("\nclass and sex:  " + CSVReader.getMutualInformation("titanic.csv", 2, 5, ","));
            System.out.println("class and child:  " + CSVReader.getMutualInformation("titanic.csv", 2, 13, ","));
            System.out.println("sex and child:  " + CSVReader.getMutualInformation("titanic.csv", 5, 13, ","));
            System.out.println("class and port:  " + CSVReader.getMutualInformation("titanic.csv", 2, 12, ","));
            //System.out.println(CSVReader.getMutualInformation("titanic.csv", 2, 14, ","));
        } catch(IOException e) {
            System.err.println(e.getClass() + " " + e.getMessage());
        }
    }
    @Test
    public void testScrub() {
    }
}
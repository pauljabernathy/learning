/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package learning.naivebayes;

import learning.titanic.Constants;
import learning.stats.ProbDist;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import learning.naivebayes.io.CSVReader;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import learning.titanic.*;
import learning.util.Utilities;

/**
 *
 * @author paul
 */
public class ClassifierTest {
    
    public static final String LINE_ONE = ",1,0,3,\"Braund, Mr. Owen Harris\",male,22,1,0,A/5 21171,7.25,,S,FALSE";
    public static final int SURVIVED_COLUMN = 2;
    private Classifier instance;
    public ClassifierTest() {
        instance = new Classifier();
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

    /**
     * Test of getDist method, of class Classifier.
     */
    //@Test
    public void testGetDist() {
        System.out.println("getDist");
        Classifier instance = new Classifier();
        ProbDist expResult = null;
        ProbDist result = instance.getDist();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDist method, of class Classifier.
     */
    //@Test
    public void testSetDist() {
        System.out.println("setDist");
        ProbDist<Classification> dist = null;
        Classifier instance = new Classifier();
        instance.setDist(dist);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of classify method, of class Classifier.
     */
    //@Test
    public void testClassify() {
        System.out.println("\ntesting classify()");
        String filename = "naive4.csv";
        Classifier instance = new Classifier();
        //instance.classify(filename);
        
        List t = new ArrayList<String>();
        //t.add("0");
        t.add("3");
        t.add("male");
        t.add("FALSE");
        //result = instance.classifyOne(t);
        //System.out.println(result);
        List<List> list = new ArrayList<List>();
        list.add(t);
        instance.setDist(new TitanicDistData().getClassificationDist());
        instance.classify(list);
    }
    
    /**
     * Test of classifyOne method, of class Classifier.
     */
    //@Test
    public void testClassifyOneArray() {
        System.out.println("\ntesting classifyOne(String[] features)");
        List features = null;
        //Classifier instance = new Classifier();
        Classification expResult = null;
        Classification result = null;//instance.classifyOne(features);
        //assertEquals(expResult, result);
        
        features = new ArrayList();
        features.add("Lora");
        features.add("blue");
        features.add(2);
        features.add(false);
        
        result = instance.classifyOne(features);
        System.out.println(result.getName());
        
        String[] s = { "Olivia", "blue", "2", "true"};
        System.out.println(instance.classifyOne(s));
        s = new String[] { "Sean", "yellow", "3", "false" };
        System.out.println(instance.classifyOne(s));
    }

    //@Test
    public void testClassifyOneList() {
        System.out.println("\ntesting ClassifyOne(List features)");
        
        ArrayList<String> features = new ArrayList<String>();
        features.add("Olivia");
        features.add("blue");
        features.add("2");
        features.add("true");
        Classification result = instance.classifyOne(features);
        System.out.println(result);
        //System.out.println(result.getName());
        
        String[] s = { "Olivia", "blue", "2", "true"};
        System.out.println(instance.classifyOne(s));
        
        List f = new ArrayList<String>();
        f.add("Olivia");
        f.add("blue");
        f.add(2);
        f.add(true);
        result = instance.classifyOne(features);
        System.out.println(result);
        result = instance.classifyOne(instance.getAsList(s));
        System.out.println(result);
        
        instance.setDist(new TitanicDistData().getClassificationDist());
        List t = new ArrayList<String>();
        //t.add("0");
        t.add("3");
        t.add("male");
        //t.add("FALSE");
        t.add("NA");
        result = instance.classifyOne(t);
        System.out.println(result);
    }
    /**
     * Test of findProbOfFeatures method, of class Classifier.
     */
    //@Test
    public void testFindProbOfFeatures() {
        System.out.println("findProbOfFeatures");
        List features = null;
        Classifier instance = new Classifier();
        double expResult = 0.0;
        double result = instance.findProbOfFeatures(features);
        //assertEquals(expResult, result, 0.0);
        
        List attrs;
        attrs = new ArrayList();
        attrs.add("Lora");
        attrs.add("blue");
        attrs.add(2);
        attrs.add(false);
        
        result = instance.findProbOfFeatures(attrs);
        System.out.println(result);
        assertEquals(0.0094875, result, 0.0);
    }
    
    //@Test
    public void testParseOneValue() {
        System.out.println("\ntesting parseOneValue()");
        Object result = null;
        assertEquals("", Classifier.parseOneValue(null));
        assertEquals(true, Classifier.parseOneValue("true"));
        assertEquals(false, Classifier.parseOneValue("false"));
        assertEquals(true, Classifier.parseOneValue("TRUE"));
        assertEquals(false, Classifier.parseOneValue("FALSE"));
        assertEquals("tru", Classifier.parseOneValue("tru"));
        assertEquals("fale", Classifier.parseOneValue("fale"));
        
        assertEquals(1, Classifier.parseOneValue("1"));
        assertEquals(200, Classifier.parseOneValue("200"));
        
        assertEquals(1.0, Classifier.parseOneValue("1.0"));
        assertEquals(200.0, Classifier.parseOneValue("200.0"));
        assertEquals(3.141593, Classifier.parseOneValue("3.141593"));
        
        assertEquals("string", Classifier.parseOneValue("string"));
        assertEquals("", Classifier.parseOneValue(""));
    }
}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package learning.naivebayes;

//import learning.titanic.Constants;
import toolbox.stats.ProbDist;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import org.apache.logging.log4j.*;
import toolbox.util.ListArrayUtil;

/**
 *
 * @author paul
 */
public class ClassifierTest {
    
    //public static final String LINE_ONE = ",1,0,3,\"Braund, Mr. Owen Harris\",male,22,1,0,A/5 21171,7.25,,S,FALSE";
    private Classifier instance;
    private static Logger logger;
    
    public ClassifierTest() {
        instance = new Classifier();
        instance.setDist(DataGenerator.getSampleProbDist());
    }
    
    @BeforeClass
    public static void setUpClass() {
        logger = ListArrayUtil.getLogger(ClassifierTest.class, Level.INFO);
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
        logger.info("getDist");
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
        logger.info("setDist");
        ProbDist<Classification> dist = null;
        instance.setDist(dist);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of classify method, of class Classifier.
     */
    @Test
    public void testClassify() {
        logger.info("\ntesting classify()");
        String filename = "naive4.csv";
        //instance.classify(filename);
        
        List t = new ArrayList<String>();
        //t.add("0");
        t.add("3");
        t.add("male");
        t.add("FALSE");
        //result = instance.classifyOne(t);
        //logger.debug(result);
        List<List> list = new ArrayList<List>();
        list.add(t);
        //instance.setDist(new TitanicDistData().getClassificationDist());
        //instance.classify(list);
        //TODO:  distributions
    }
    
    /**
     * Test of classifyOne method, of class Classifier.
     */
    @Test
    public void testClassifyOneArray() {
        logger.info("\ntesting classifyOne(String[] features)");
        List features = null;
        //Classifier instance = new Classifier();
        Classification expResult = null;
        Classification result = null;//instance.classifyOne(features);
        //assertEquals(expResult, result);
        
        features = new ArrayList();
        features.add("Duke");
        features.add("blue");
        features.add(2);
        features.add(false);
        
        result = instance.classifyOne(features);
        logger.debug(result.getName());
        assertEquals("c1", result.getName());
        
        String[] s = { "Duke", "blue", "2", "true"};
        logger.debug(instance.classifyOne(s));
        assertEquals("c1", instance.classifyOne(s).getName());
        s = new String[] { "Wake Forest", "yellow", "3", "false" };
        logger.debug(instance.classifyOne(s));
    }

    @Test
    public void testClassifyOneList() {
        logger.info("\ntesting ClassifyOne(List features)");
        
        ArrayList<String> features = new ArrayList<String>();
        features.add("Duke");
        features.add("blue");
        features.add("2");
        features.add("true");
        Classification result = instance.classifyOne(features);
        logger.debug(result);
        //logger.debug(result.getName());
        assertEquals("unknown", result.getName());
        
        ArrayList features2 = new ArrayList();
        features2.add("Duke");
        features2.add("blue");
        features2.add(2);
        features2.add(true);
        result = instance.classifyOne(features2);
        logger.debug(result);
        assertEquals("c1", result.getName());
        
        String[] s = { "Duke", "blue", "2", "true"};
        logger.debug(instance.classifyOne(s));
        assertEquals("c1", result.getName());
        
        List f = new ArrayList<String>();
        f.add("Duke");
        f.add("blue");
        f.add(2);
        f.add(true);
        result = instance.classifyOne(f);
        logger.debug(result);
        assertEquals("c1", result.getName());
        result = instance.classifyOne(instance.getAsList(s));
        logger.debug(result);
        assertEquals("c1", result.getName());
        
        //TODO:  supply a distribution
        /*instance.setDist(new TitanicDistData().getClassificationDist());
        List t = new ArrayList<String>();
        //t.add("0");
        t.add("3");
        t.add("male");
        //t.add("FALSE");
        t.add("NA");
        result = instance.classifyOne(t);
        logger.debug(result);*/
    }
    /**
     * Test of findProbOfFeatures method, of class Classifier.
     */
    @Test
    public void testFindProbOfFeatures() {
        logger.info("\ntesting findProbOfFeatures()");
        List features = null;
        double expResult = 0.0;
        double result = instance.findProbOfFeatures(features);
        //assertEquals(expResult, result, 0.0);
        
        List attrs;
        attrs = new ArrayList();
        attrs.add("Duke");
        attrs.add("blue");
        attrs.add(2);
        attrs.add(false);
        
        result = instance.findProbOfFeatures(attrs);
        logger.debug(result);
        assertEquals(.0044, instance.getDist().getValues().get(0).probabilityOf(attrs), 0.00001);
        assertEquals(.002475, instance.getDist().getValues().get(1).probabilityOf(attrs), 0.00001);
        assertEquals(.003375, instance.getDist().getValues().get(2).probabilityOf(attrs), 0.00001);
        assertEquals(0.00341625, result, 0.0);
    }
    
    @Test
    public void testParseOneValue() {
        logger.info("\ntesting parseOneValue()");
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
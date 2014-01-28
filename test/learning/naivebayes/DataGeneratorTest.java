/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package learning.naivebayes;

import learning.stats.ProbDist;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author paul
 */
public class DataGeneratorTest {
    
    private static Logger logger;
    
    public DataGeneratorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        logger = Logger.getLogger("HandRankerTest");
        logger.addAppender(new ConsoleAppender(new PatternLayout("%m%n")));
        logger.setLevel(Level.INFO);
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
     * Test of main method, of class DataGenerator.
     */
    //@Test
    public void testMain() {
        logger.info("main");
        String[] args = null;
        DataGenerator.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generateDate method, of class DataGenerator.
     */
    @Test
    public void testGenerateData() {
        logger.info("generateDate");
        DataGenerator instance = new DataGenerator();
        try {
            instance.generateData(100000, "naive3.csv");
        } catch(IOException e) {
            logger.debug(e.getClass() + " " + e.getMessage());
        }
    }

    /**
     * Test of getRandomFeatures method, of class DataGenerator.
     */
    @Test
    public void testGetRandomFeatures() {
        logger.info("getRandomFeatures");
        DataGenerator instance = new DataGenerator();
        List result = null;//instance.getRandomFeatures(null);
        //assertEquals(0, result.size());
        
        ProbDist<String> names = new ProbDist<String>();
        names.add("Duke", 0.2);
        names.add("UNC", 0.3);
        names.add("Wake Forest", 0.2);
        names.add("NC State", 0.15);
        names.add("GA Tech", 0.15);
        logger.debug(names.toString());
        
        ProbDist<String> colors = new ProbDist<String>();
        colors.add("red", .3);
        colors.add("blue", .4);
        colors.add("yellow", .3);
        logger.debug(colors.toString());
        
        List<ProbDist<String>> dists = new ArrayList<ProbDist<String>>();
        dists.add(names);
        dists.add(colors);
        result = instance.getRandomFeatures1(dists);
        showArrayList(result);
        
        int numRands = 1000;
        for(int i = 0; i < numRands; i++) {
            showArrayList(instance.getRandomFeatures1(dists));
        }
    }
    
    public <T> void showArrayList(List<T> input) {
        logger.debug("showArrayList()");
        if(input == null) {
            return;
        }
        for(int i = 0; i < input.size(); i++) {
            logger.debug(i + " " + input.get(i));
        }
        logger.debug("");
    }
}
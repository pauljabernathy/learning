/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package learning.genetic;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import toolbox.util.ListArrayUtil;
import toolbox.stats.*;
import org.apache.logging.log4j.*;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author paul
 */
public class GenomeTest {
    
    private static Logger logger;
    
    public GenomeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        logger = ListArrayUtil.getLogger(GenomeTest.class, Level.DEBUG);
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
    public void testGetInstance() {
        logger.info("\ntesting getInstance()");
        Genome instance1 = Genome.getInstance(10, Double.class);
        Genome instance2 = Genome.getInstance(10, String.class);
        Genome instance3 = Genome.getInstance(10, int.class);
        
        logger.debug(instance1.getSize());
        instance1.set(0, 5.0);
        instance1.set(1, "should not work");
        
    }
    
    @Test
    public void testGetSize() {
        logger.info("\ntesting getSize()");
        Genome instance = Genome.getInstance(10, Double.class);// new Genome<Double>(27, String.class);
        //instance.add(2.0);
    }
    
    @Test
    public void testSetAndGet_no_params() {
        logger.info("\ntesting get()");
        Genome instanceDouble = Genome.getInstance(10, Double.class);// new Genome<Double>(10);
        logger.debug(instanceDouble.getSize());
        instanceDouble.set(0, 5.0);
        //assertEquals(5.0, instanceDouble.get(0), 0.0);
        logger.debug(instanceDouble.get(1));
    }
    
    @Test
    public void testGet_range() {
        logger.info("\ntesting get(int start, int last)");
    }
    
    @Test
    public void testGetRawData() {
        logger.info("\ntesting getRawData()");
    }
    
    @Test
    public void testGetRandomMutationType() {
        logger.info("\ntesting getMutationType()");
        ProbDist<MutationType> mutationProbs = new ProbDist<MutationType>();
        mutationProbs.add(MutationType.POINT_VALUE_CHANGE, .25);
        mutationProbs.add(MutationType.MULTIPLE_POINT_VALUE_CHANGE, .35);
        mutationProbs.add(MutationType.POINT_DELETION, 0.0);   //let's not deal with changing the length just yet...
        mutationProbs.add(MutationType.SWAP, .2);
        mutationProbs.add(MutationType.GROUP_REVERSAL, .2);
        Genome instance = new DoubleGenome(0, mutationProbs);
        List<MutationType> results = new ArrayList<MutationType>();
        for(int i = 0; i < 10000; i++) {
            results.add(instance.getRandomMutationType());
        }
        Histogram h = new Histogram(results);
        logger.debug(h.toString());
        //TODO;  chi squared test
    }
}
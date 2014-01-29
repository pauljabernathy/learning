/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package learning.naivebayes;

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
public class ClassificationTest {
    
    private static Logger logger;
    
    public ClassificationTest() {
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
     * Test of getName method, of class Classification.
     */
    //@Test
    public void testGetName() {
        System.out.println("getName");
        Classification instance = null;
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setName method, of class Classification.
     */
    //@Test
    public void testSetName() {
        System.out.println("setName");
        String name = "";
        Classification instance = null;
        instance.setName(name);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFeatureCPDs method, of class Classification.
     */
    //@Test
    public void testGetFeatureCPDs() {
        System.out.println("getFeatureCPDs");
        Classification instance = null;
        List expResult = null;
        List result = instance.getFeatureCPDs();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setFeatureDists method, of class Classification.
     */
    //@Test
    public void testSetFeatureDists() {
        System.out.println("setFeatureDists");
        Classification instance = null;
        instance.setFeatureDists(null);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of probabilityOf method, of class Classification.
     */
    @Test
    public void testProbabilityOf() {
        System.out.println("\ntesting probabilityOf()");
        Classification instance = null;
        List<Classification> classes = DataGenerator.getSamples();
        instance = classes.get(0);
        List attrs = null;
        //double expResult = 0.0;
        double result = -1.0;//instance.probabilityOf(attrs);
        //assertEquals(expResult, result, 0.0);
        assertEquals(0.0, instance.probabilityOf(attrs), 0.0);
        attrs = new ArrayList();
        assertEquals(0.0, instance.probabilityOf(attrs), 0.0);
        
        System.out.println("\nDuke red 1 true");
        attrs.add("Duke");
        attrs.add("red");
        attrs.add(1);
        attrs.add(true);
        result = instance.probabilityOf(attrs);
        System.out.println(instance.getName() + ":  " + result);
        assertEquals(0.01335, result, 0.0001);
        System.out.println(classes.get(1).getName() + ":  " + result);
        
        System.out.println("\nTiger false 1 true");
        attrs = new ArrayList();
        attrs.add("Tiger");
        attrs.add(false);
        attrs.add(1);
        attrs.add(true);
        result = instance.probabilityOf(attrs);
        System.out.println(result);
        assertEquals(0.0, result, 0.0);
        
        System.out.println("\nUNC blue 2 true");
        attrs = new ArrayList();
        attrs.add("UNC");
        attrs.add("blue");
        attrs.add(2);
        attrs.add(false);
        result = instance.probabilityOf(attrs);
        System.out.println(result);
        assertEquals(0.0066, result, 0.00001);
        
        System.out.println(classes.get(0).getName() + ":  " + classes.get(0).probabilityOf(attrs));
        System.out.println(classes.get(0).getName() + ":  " + classes.get(1).probabilityOf(attrs));
        System.out.println("weighted sum:  " + classes.get(0).probabilityOf(attrs) + classes.get(1).probabilityOf(attrs));
        
    }

    /**
     * Test of toString method, of class Classification.
     */
    //@Test
    public void testToString() {
        System.out.println("toString");
        Classification instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
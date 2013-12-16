/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package learning.stats;

import learning.stats.ProbDist;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
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
public class ProbDistTest {
    
    public ProbDistTest() {
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
     * Test of reset method, of class CPD.
     */
    //@Test
    public void testReset() {
        System.out.println("reset");
        ProbDist instance = new ProbDist();
        instance.reset();
    }

    /**
     * Test of getValues method, of class CPD.
     */
    //@Test
    public void testGetValues() {
        System.out.println("getValues");
        ProbDist instance = new ProbDist();
        ArrayList expResult = null;
        List result = instance.getValues();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getValue method, of class CPD.
     */
    //@Test
    public void testGetValue() {
        System.out.println("getValue");
        int index = 0;
        ProbDist<String> instance = new ProbDist<String>();
        assertEquals(null, instance.getValue(0));
        //instance.add("Paul", 0.5);
        Object expResult = null;
        Object result = instance.getValue(index);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setValues method, of class CPD.
     */
    //@Test
    public void testSetValues() {
        System.out.println("setValues");
        ProbDist instance = new ProbDist();
        instance.setValues(null);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    //@Test
    public void testAdd() {
        System.out.println("\ntesting add()");
        ProbDist<String> instance = new ProbDist<String>();
        boolean result = false;
        System.out.println(instance.contains("Paul"));
        result = instance.add("Paul", 0.5);
        assertEquals(true, result);
        instance.display();
        result = instance.add("Lora", 0.501);
        assertEquals(false, result);
        instance.display();
        
        result = instance.add("Lora", 0.50);
        assertEquals(true, result);
        instance.display();
        
        instance.reset();
        System.out.println("\n\nresetting");
        instance.add("Paul", 0.2);
        instance.add("Lora", 0.3);
        instance.add("Olivia", 0.2);
        instance.add("Sean", 0.15);
        result = instance.add("Shiloh", 0.15);
        System.out.println("added Shiloh? " + result);
        instance.display();
        System.out.println("probs;");
        showArrayList(instance.getProbabilities());
        System.out.println("values");
        showArrayList(instance.getValues());
    }
    
    @Test
    public void testValidateNormalized() {
        System.out.println("\ntesting validateNormalized()");
        ArrayList<Double> probs = null;
        assertEquals(false, ProbDist.validateNormalized(probs));
        
        probs = new ArrayList<Double>();
        assertEquals(false, ProbDist.validateNormalized(probs));
        
        probs.add(.99);
        assertEquals(false, ProbDist.validateNormalized(probs));
        
        probs.add(.01);
        assertEquals(true, ProbDist.validateNormalized(probs));
        
        probs.add(.01);
        assertEquals(false, ProbDist.validateNormalized(probs));
        
        probs.remove(.01);
        assertEquals(true, ProbDist.validateNormalized(probs));
        
        probs.add(5.0);
        assertEquals(false, ProbDist.validateNormalized(probs));
    }
    
    //@Test
    public void testGetRandomValue() {
        System.out.println("\ntesting getRandomFeature()");
        ProbDist<String> instance = new ProbDist<String>();
        boolean added = false;
        instance.add("Paul", 0.2);
        instance.add("Lora", 0.3);
        instance.add("Olivia", 0.2);
        instance.add("Sean", 0.15);
        added = instance.add("Shiloh", 0.15);
        System.out.println("added Shiloh? " + added);
        assertEquals(true, ProbDist.validateNormalized(instance.getProbabilities()));
        /*System.out.println("values");
        showArrayList(instance.getValues());
        System.out.println("probabilities");
        showArrayList(instance.getProbabilities());*/
        instance.display();
        int[] counts = new int[5];
        int numRands = 100000;
        String rand = "";
        for(int i = 0; i < numRands; i++) {
            rand = instance.getRandomValue();
            //System.out.println(i + " " + rand);
            if(rand == null) {
                fail("was null");
            }
            if(rand.equals("Paul")) {
                counts[0]++;
            } else if(rand.equals("Lora")) {
                counts[1]++;
            } else if(rand.equals("Olivia")) {
                counts[2]++;
            } else if(rand.equals("Sean")) {
                counts[3]++;
            } else if(rand.equals(("Shiloh"))) {
                counts[4]++;
            }
        }
        showArray(counts);
    }

    /**
     * Test of getProbabilities method, of class CPD.
     */
    //@Test
    public void testGetProbabilities() {
        System.out.println("getProbabilities");
        ProbDist instance = new ProbDist();
        ArrayList expResult = null;
        List result = instance.getProbabilities();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setProbabilities method, of class CPD.
     */
    //@Test
    public void testSetProbabilities() {
        System.out.println("setProbabilities");
        ArrayList<Double> probabilities = null;
        ProbDist instance = new ProbDist();
        instance.setProbabilities(probabilities);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of contains method, of class CPD.
     */
    //@Test
    public void testContains() {
        System.out.println("contains");
        Object value = null;
        ProbDist instance = new ProbDist();
        boolean expResult = false;
        boolean result = instance.contains(value);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    private void showArray(int[] input) {
        if(input == null) {
            return;
        }
        for(int i = 0; i < input.length; i++) {
            System.out.println(input[i]);
        }
    }
    
    public <T> void showArrayList(List<T> input) {
        if(input == null) {
            return;
        }
        System.out.println("\n");
        for(int i = 0; i < input.size(); i++) {
            System.out.println(i + " " + input.get(i));
        }
    }
    
    @Test
    public void testProbabilityOf() {
        System.out.println("\ntesting probabilityOf()");
        ProbDist<String> instance = new ProbDist<String>();
        instance.add("Paul", 0.2);
        instance.add("Lora", 0.3);
        instance.add("Olivia", 0.2);
        instance.add("Sean", 0.15);
        instance.add("Shiloh", 0.15);
        //instance.display();
        assertEquals(.2, instance.probatilityOf("Paul"), 0.0);
        assertEquals(.3, instance.probatilityOf("Lora"), 0.0);
        assertEquals(.2, instance.probatilityOf("Olivia"), 0.0);
        assertEquals(.15, instance.probatilityOf("Sean"), 0.0);
        assertEquals(.15, instance.probatilityOf("Shiloh"), 0.0);
        
        assertEquals(0.0, instance.probatilityOf("Pau"), 0.0);
        assertEquals(0.0, instance.probatilityOf("false"), 0.0);
        assertEquals(0.0, instance.probatilityOf(null), 0.0);
    }
    
    @Test
    public void testCreateInstanceFromCounts() {
        System.out.println("\ntesting createInstanceFromCounts()");
        ProbDist<String> result = null;
        ProbDist.createInstanceFromCounts(null, null);
        
        ArrayList<String> strValues = new ArrayList<String>();
        strValues.add("A");
        strValues.add("B");
        strValues.add("C");
        
        ArrayList<Integer> counts = new ArrayList<Integer>();
        counts.add(10);
        counts.add(20);
        counts.add(10);
        
        //TODO:  use assertEquals instead of display()
        result = ProbDist.createInstanceFromCounts(strValues, counts);
        result.display();
        
        counts.add(60);
        /*result = ProbDist.createInstanceFromCounts(strValues, counts);  //blows up as inteded because of the assert line
        result.display();*/
        strValues.add("C");
        result = ProbDist.createInstanceFromCounts(strValues, counts);  //blows up as inteded because of the assert line
        result.display();
        
        ArrayList<Boolean> boolValues = new ArrayList<Boolean>();
        boolValues.add(true);
        boolValues.add(false);
        ArrayList<Integer> boolCounts = new ArrayList<Integer>();
        boolCounts.add(100);
        boolCounts.add(150);
        
        ProbDist<Boolean> resultBool = ProbDist.createInstanceFromCounts(boolValues, boolCounts);  //blows up as inteded because of the assert line
        resultBool.display();
    }
    
    @Test
    public void testGetJointDistribution() {
        System.out.println("\ntesting getJointDistribution()");
        ProbDist<String> fruit = new ProbDist<String>();
        fruit.add("blueberries", .7);
        fruit.add("grapes", .3);
        
        ProbDist<String> mains = new ProbDist<String>();
        mains.add("steak", .5);
        mains.add("mutton", .1);
        mains.add("Maine Lobster", .4);
        
        ProbDist<List> joint = ProbDist.getJointDistribution(fruit, mains);
        assertEquals(6, joint.getValues().size());
        for(List<String> values : joint.getValues()) {
            assertEquals(2, values.size());
        }
        joint.display();
        assertEquals("blueberries", joint.getValues().get(0).get(0));
        assertEquals("steak", joint.getValues().get(0).get(1));
        assertEquals("blueberries", joint.getValues().get(1).get(0));
        assertEquals("mutton", joint.getValues().get(1).get(1));
        assertEquals("blueberries", joint.getValues().get(2).get(0));
        assertEquals("Maine Lobster", joint.getValues().get(2).get(1));
        
        assertEquals("grapes", joint.getValues().get(3).get(0));
        assertEquals("steak", joint.getValues().get(3).get(1));
        assertEquals("grapes", joint.getValues().get(4).get(0));
        assertEquals("mutton", joint.getValues().get(4).get(1));
        assertEquals("grapes", joint.getValues().get(5).get(0));
        assertEquals("Maine Lobster", joint.getValues().get(5).get(1));
        
        assertEquals(.35, joint.getProbabilities().get(0), .00001);
        assertEquals(.07, joint.getProbabilities().get(1), .00001);
        assertEquals(.28, joint.getProbabilities().get(2), .00001);
        assertEquals(.15, joint.getProbabilities().get(3), .00001);
        assertEquals(.03, joint.getProbabilities().get(4), .00001);
        assertEquals(.12, joint.getProbabilities().get(5), .00001);
    }
    
    @Test
    public void testGetEntropy() {
        System.out.println("\ntesting getEntropy()");
        ProbDist<String> instance = new ProbDist<String>();
        //try {
            assertEquals(0.0, instance.getEntropy(), .0000001);
        /*} catch(ProbabilityException e) {
            System.err.println(e.getMessage());
        }*/
        
        //try {
            instance.add("first", 0.2424242);
            System.out.println(instance.getEntropy());
            System.out.println(ProbDist.validateNormalized(instance.getProbabilities()));
            learning.util.Utilities.showList(instance.getProbabilities());
            //fail("did not throw the exception");
        /*} catch(ProbabilityException e) {
            System.err.println(e.getMessage());
        }*/
        
        //try {
            instance.add("second", 0.2065095);
            instance.add("third", 0.5510662);

            assertEquals(1.439321, instance.getEntropy(), .001);
        /*} catch(ProbabilityException e) {
            System.err.println(e.getMessage());
        }*/
    }
    
    @Test
    public void testGetMutualInformation() {
        System.out.println("\ntesting getMutualInformation");
        ProbDist<String> fruit = new ProbDist<String>();
        fruit.add("blueberries", .7);
        fruit.add("grapes", .3);
        
        ProbDist<String> mains = new ProbDist<String>();
        mains.add("steak", .5);
        mains.add("mutton", .1);
        mains.add("Maine Lobster", .4);
        
        System.out.println(ProbDist.getMutualInformation(fruit, mains));
    }
}
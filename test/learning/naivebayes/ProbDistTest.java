/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package learning.naivebayes;

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
     * Test of getDistribution method, of class CPD.
     */
    //@Test
    public void testGetDistribution() {
        System.out.println("getDistribution");
        ProbDist instance = new ProbDist();
        HashMap expResult = null;
        HashMap result = instance.getDistribution();
        //assertEquals(expResult, result);
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
    
    //@Test
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
     * Test of setDistribution method, of class CPD.
     */
    //@Test
    public void testSetDistribution() {
        System.out.println("setDistribution");
        ProbDist instance = new ProbDist();
        instance.setDistribution(null);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getProbabilities method, of class CPD.
     */
    //@Test
    public void testGetProbabilities() {
        System.out.println("getProbabilities");
        ProbDist instance = new ProbDist();
        ArrayList expResult = null;
        ArrayList result = instance.getProbabilities();
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
}
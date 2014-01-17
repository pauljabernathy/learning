/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package learning.naivebayes;

import learning.stats.ProbDist;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
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
    
    public DataGeneratorTest() {
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
     * Test of main method, of class DataGenerator.
     */
    //@Test
    public void testMain() {
        System.out.println("main");
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
        System.out.println("generateDate");
        DataGenerator instance = new DataGenerator();
        try {
            instance.generateData(100000, "naive3.csv");
        } catch(IOException e) {
            System.out.println(e.getClass() + " " + e.getMessage());
        }
    }

    /**
     * Test of getRandomFeatures method, of class DataGenerator.
     */
    //@Test
    public void testGetRandomFeatures() {
        System.out.println("getRandomFeatures");
        DataGenerator instance = new DataGenerator();
        List result = null;//instance.getRandomFeatures(null);
        //assertEquals(0, result.size());
        
        ProbDist<String> names = new ProbDist<String>();
        names.add("Paul", 0.2);
        names.add("Lora", 0.3);
        names.add("Olivia", 0.2);
        names.add("Sean", 0.15);
        names.add("Shiloh", 0.15);
        names.display();
        
        ProbDist<String> colors = new ProbDist<String>();
        colors.add("red", .3);
        colors.add("blue", .4);
        colors.add("yellow", .3);
        colors.display();
        
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
        if(input == null) {
            return;
        }
        //System.out.println("\n");
        for(int i = 0; i < input.size() - 1; i++) {
            //System.out.println(i + " " + input.get(i));
            System.out.print(input.get(i) + " ");
        }
        System.out.println(input.get(input.size() - 1));
    }
}
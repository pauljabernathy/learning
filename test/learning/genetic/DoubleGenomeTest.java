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

import org.apache.log4j.*;
import toolbox.util.ListArrayUtil;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author paul
 */
public class DoubleGenomeTest {
    
    private static Logger logger;
    
    public DoubleGenomeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        logger = ListArrayUtil.getLogger(DoubleGenome.class, Level.DEBUG);
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
    public void testConstructor() {
        logger.info("\ntesting constructor");
        DoubleGenome instance = new DoubleGenome(0);
        assertEquals(0, instance.getSize());
        instance = new DoubleGenome(10);
        assertEquals(10, instance.getSize());
    }
    @Test
    public void testSetAndGet_int() {
        logger.info("\ntesting set(int index) and get(int element)");
        int size = 10;
        DoubleGenome genome = new DoubleGenome(size);
        for(int i = 0; i < size; i++) {
            assertEquals(0.0, genome.get(i), 0.0);
        }
        genome.set(9, 9.0);
        assertEquals(9.0, genome.get(9), 0.0);
        genome.set(3, 3.0);
        assertEquals(3.0, genome.get(3), 0.0);
        try {
            genome.set(-1, -1.0);
            fail("did not throw IndexOutOfBoundsException");
        } catch(IndexOutOfBoundsException e) {
            logger.debug("correclty threw IndexOutOfBoundsException for genome.set(10, -1.0);");
        }
        try {
            genome.set(10, -1.0);
            fail("did not throw IndexOutOfBoundsException");
        } catch(IndexOutOfBoundsException e) {
            logger.debug("correclty threw IndexOutOfBoundsException for genome.set(10, -1.0);");
        }
        
        try {
            genome.get(45);
            fail("did nt catch IndexOutOfBoundsException");
        } catch(IndexOutOfBoundsException e) {
            logger.debug("correclty threw IndexOutOfBoundsException for genome.set(10, -1.0);");
        }
    }

    @Test
    public void testGet_int_int() {
        logger.info("\ntesting get(int start, int end)");
        int size = 10;
        DoubleGenome genome = new DoubleGenome(size);
        for(int i = 0; i < size; i++) {
            genome.set(i, new Double(i));
            logger.debug(genome.get(i));
            assertEquals(i, genome.get(i), 0.0);
        }
        
        int start = 3;
        int end = 7;
        List<Double> result = genome.get(start, end);
        assertEquals(5, result.size());
        for(int i = 0; i < result.size(); i++) {
            assertEquals(i + start, result.get(i), 0.0);
        }
        
        start = 0;
        end = 9;
        result = genome.get(start, end);
        assertEquals(10, result.size());
        for(int i = 0; i < result.size(); i++) {
            assertEquals(i + start, result.get(i), 0.0);
        }
        
        try {
            start = -1;
            end = 9;
            result = genome.get(start, end);
            fail("did not throw IndexOutOfBoundsException");
        } catch(IndexOutOfBoundsException e) {
            logger.debug("correclty threw IndexOutOfBoundsException for genome.get(-1, 9);");
        }
        
        try {
            start = 0;
            end = 10;
            result = genome.get(start, end);
            fail("did not throw IndexOutOfBoundsException");
        } catch(IndexOutOfBoundsException e) {
            logger.debug("correclty threw IndexOutOfBoundsException for genome.get(0, 10);");
        }
        
        start = 7;
        end = 3;
        result = genome.get(start, end);
        for(int i = 0; i < result.size(); i++) {
            assertEquals(i + end, result.get(i), 0.0);
            logger.debug(result.get(i));
        }
    }

    @Test
    public void setRawData_array() {
        logger.info("\ntesting setRawData(double[] data)");
        DoubleGenome instance = new DoubleGenome(10);
        assertEquals(10, instance.getSize());
        
        double[] data = null;
        instance.setRawData(data);
        assertEquals(10, instance.getSize());
        
        data = new double[] { };
        instance.setRawData(data);
        assertEquals(0, instance.getSize());
        
        data = new double[] { 0.0, 1.0, 2.0, 3.0 };
        instance.setRawData(data);
        assertEquals(4, instance.getSize());
    }
    
    @Test
    public void setRawData_List() {
        logger.info("\ntesting setRawData(List<Double> data)");
        DoubleGenome instance = new DoubleGenome(10);
        assertEquals(10, instance.getSize());
        
        //should not accept null input -> size should remain initial size
        List<Double> d = null;
        instance.setRawData(d);
        assertEquals(10, instance.getSize());
        
        //empty array is currently acceptable
        d = new ArrayList<Double>();
        instance.setRawData(d);
        assertEquals(0, instance.getSize());
        
        d.add(0.0);
        d.add(1.0);
        d.add(2.0);
        d.add(3.0);
        instance.setRawData(d);
        assertEquals(4, instance.getSize());
        
        //now with new data bigger than the initial size - should not be a problem; size should automatically expand to fit it
        instance = new DoubleGenome(0);
        assertEquals(0, instance.getSize());
        instance.setRawData(d);
        assertEquals(4, instance.getSize());
    }
    
    @Test
    public void testMutate() {
        logger.info("\ntesting mutate()");
        int size = 40;
        DoubleGenome instance = new DoubleGenome(size);
        //double[] nums = toolbox.random.Random.getUniformDoubles(size, 0.0, 1.0);
        double[] nums = new double[size];
        List<Double> input = new ArrayList<Double>();
        for(int i = 0; i < size; i++) {
            nums[i] = (double)i;
            input.add((double)i);
        }
        //instance.setRawData(input);
        List<Double> previousData = instance.getRawData();  //for if we comment out the previous line
        assertEquals(size, instance.getRawData().size());
        assertEquals(size, instance.getSize());
        //logger.debug(ListArrayUtil.listToString(instance.getRawData()));
        List<Double> mutated = instance.mutate();
        logger.debug(ListArrayUtil.listToString(instance.getRawData()));
        logger.debug(ListArrayUtil.listToString(mutated));
        logger.debug(ListArrayUtil.haveSameElements(input, instance.getRawData()));
        //assertEquals(true, ListArrayUtil.haveSameElements(input, instance.getRawData())); //genome in instance should not change
        assertEquals(true, ListArrayUtil.haveSameElements(previousData, instance.getRawData()));
        assertEquals(false, ListArrayUtil.haveSameElements(input, mutated));              //mutated should be different
        instance.setRawData(mutated);
        assertEquals(false, ListArrayUtil.haveSameElements(previousData, instance.getRawData()));
        //TODO:  more rigorous test for how different they should be?
    }
    
    @Test
    public void testDoPointChangeMutation() {
        logger.info("\ntesting doPointChangeMutation()");
        int size = 40;
        DoubleGenome instance = new DoubleGenome(size);
        //double[] nums = null;
        
        //nums = new double[size];
        List<Double> input = null;
        List<Double> mutated = null;
        instance.setRawData(input);
        mutated = instance.doPointChangeMutation();
        if(mutated == null) {
            fail("mutated was null");
        }
        assertEquals(size, mutated.size());
        assertEquals(false, ListArrayUtil.haveSameElements(input, mutated));
        
        input = new ArrayList<Double>();
        mutated = instance.doPointChangeMutation();
        if(mutated == null) {
            fail("mutated was null");
        }
        assertEquals(size, mutated.size());
        assertEquals(false, ListArrayUtil.haveSameElements(input, mutated));
        
        //now with actual data in the genome
        for(int i = 0; i < size; i++) {
            //nums[i] = (double)i;
            input.add((double)i);
        }
        instance.setRawData(input);
        mutated = instance.doPointChangeMutation();
        logger.debug(ListArrayUtil.listToString(input));
        logger.debug(ListArrayUtil.listToString(mutated));
        assertEquals(size, mutated.size());
        assertEquals(false, ListArrayUtil.haveSameElements(input, mutated));
        int numDiffs = 0;
        for(int i = 0; i < mutated.size(); i++) {
            if(!mutated.get(i).equals(input.get(i))) {
                numDiffs++;
            }
        }
        assertEquals(1, numDiffs);
    }
    
    @Test
    public void testDoSwapMutation() {
        logger.info("\ntesting doSwapMutation()");
        int size = 40;
        DoubleGenome instance = new DoubleGenome(size);
        List<Double> input = null;
        List<Double> mutated = null;
        instance.setRawData(input);
        mutated = instance.doSwapMutation();
        if(mutated == null) {
            fail("mutated was null");
        }
        assertEquals(size, mutated.size());
        assertEquals(false, ListArrayUtil.haveSameElements(input, mutated));
        
        input = new ArrayList<Double>();
        mutated = instance.doSwapMutation();
        if(mutated == null) {
            fail("mutated was null");
        }
        assertEquals(size, mutated.size());
        assertEquals(false, ListArrayUtil.haveSameElements(input, mutated));
        
        //now with actual data in the genome
        for(int i = 0; i < size; i++) {
            //nums[i] = (double)i;
            input.add((double)i);
        }
        instance.setRawData(input);
        mutated = instance.doSwapMutation();
        logger.debug(ListArrayUtil.listToString(input));
        logger.debug(ListArrayUtil.listToString(mutated));
        assertEquals(size, mutated.size());
        assertEquals(false, ListArrayUtil.haveSameElements(input, mutated));
        int numDiffs = 0;
        for(int i = 0; i < mutated.size(); i++) {
            if(!mutated.get(i).equals(input.get(i))) {
                numDiffs++;
            }
        }
        assertEquals(2, numDiffs);
    }
    //TODO:  test swap and reversal on sizes of 2 or less
    //TODO:  test with odd genome length
    @Test
    public void testDoGroupReversalMutation() {
        logger.info("\ntesting doGroupReveralMutation()");
        int size = 40;
        DoubleGenome instance = new DoubleGenome(size);
        List<Double> input = null;
        List<Double> mutated = null;
        instance.setRawData(input);
        mutated = instance.doGroupReversalMutation();
        if(mutated == null) {
            fail("mutated was null");
        }
        assertEquals(size, mutated.size());
        assertEquals(false, ListArrayUtil.haveSameElements(input, mutated));
        
        input = new ArrayList<Double>();
        mutated = instance.doGroupReversalMutation();
        if(mutated == null) {
            fail("mutated was null");
        }
        assertEquals(size, mutated.size());
        assertEquals(false, ListArrayUtil.haveSameElements(input, mutated));
        
        //now with actual data in the genome
        for(int i = 0; i < size; i++) {
            //nums[i] = (double)i;
            input.add((double)i);
        }
        instance.setRawData(input);
        mutated = instance.doGroupReversalMutation();
        logger.debug(ListArrayUtil.listToString(input));
        logger.debug(ListArrayUtil.listToString(mutated));
        assertEquals(size, mutated.size());
        assertEquals(false, ListArrayUtil.haveSameElements(input, mutated));
        int numDiffs = 0;
        for(int i = 0; i < mutated.size(); i++) {
            if(!mutated.get(i).equals(input.get(i))) {
                numDiffs++;
            }
        }
        if(numDiffs < 1) {
            fail("there was no difference");
        }
    }
    
    @Test
    public void testClone() {
        logger.info("\ntesting clone()");
        int size = 40;
        DoubleGenome instance = new DoubleGenome(size);
        //double[] nums = toolbox.random.Random.getUniformDoubles(size, 0.0, 1.0);
        double[] nums = new double[size];
        List<Double> input = new ArrayList<Double>();
        for(int i = 0; i < size; i++) {
            nums[i] = (double)i;
            input.add((double)i);
        }
        instance.setRawData(input);
        List<Double> original = instance.getRawData();
        logger.debug(original);
        DoubleGenome copy = instance.clone();
        List<Double> copyOriginal = copy.getRawData();
        assertEquals(true, ListArrayUtil.haveSameElements(instance.getRawData(), copy.getRawData()));
        List<Double> mutated = instance.mutate();
        instance.setRawData(mutated);
        logger.debug(mutated);
        assertEquals(false, ListArrayUtil.haveSameElements(original, mutated));
        assertEquals(false, ListArrayUtil.haveSameElements(original, instance.getRawData()));
        
        assertEquals(false, ListArrayUtil.haveSameElements(copyOriginal, instance.getRawData()));
        logger.debug(copyOriginal);
        logger.debug(copy.getRawData());
        assertEquals(false, ListArrayUtil.haveSameElements(copy.getRawData(), instance.getRawData()));
    }
    
    //TODO:  move to toolbox
    private boolean haveSameElements(double[] left, double[] right) {
        //if one is null but the other is not, they are different
        if((left == null && right != null) || (left != null && right == null)) {
            return false;
        }
        //if they are different sizes, they are different
        if(left.length != right.length) {
            return false;
        }
        for(int i = 0; i < left.length; i++) {
            if(left[i] != right[i]) {
                return false;
            }
        }
        return true;
    }
    
}
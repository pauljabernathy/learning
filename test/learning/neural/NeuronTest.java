/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package learning.neural;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import toolbox.util.ListArrayUtil;
import org.apache.log4j.*;
import java.util.ArrayList;

/**
 *
 * @author paul
 */
public class NeuronTest {
    
    private static Logger logger;
    private static final double ERROR = 0.0000001;
    
    public NeuronTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        logger = ListArrayUtil.getLogger(NeuronTest.class, Level.DEBUG);
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
    public void testGetOutput() {
    }

    @Test
    public void testCalculateOutputAndGetCachedOutput() {
        logger.info("\ntesting calculateOutput() and getCachedOutput()");
        Neuron instance = new Neuron();
        Neuron input1 = new Neuron(new ArrayList<Neuron>(), new ArrayList<Double>(), .2);
        Neuron input2 = new Neuron(new ArrayList<Neuron>(), new ArrayList<Double>(), .2);
        Neuron input3 = new Neuron(new ArrayList<Neuron>(), new ArrayList<Double>(), .1);
        Neuron input4 = new Neuron(new ArrayList<Neuron>(), new ArrayList<Double>(), .6);
        
        instance.addInput(input1, Neuron.ACTIVE);
        assertEquals(Neuron.INACTIVE, instance.getCachedOutput(), ERROR);
        assertEquals(Neuron.INACTIVE, instance.calculateOutput(), ERROR);
        assertEquals(Neuron.INACTIVE, instance.getCachedOutput(), ERROR);
        
        instance.addInput(input2, Neuron.INACTIVE);
        assertEquals(Neuron.INACTIVE, instance.getCachedOutput(), ERROR);
        assertEquals(Neuron.INACTIVE, instance.calculateOutput(), ERROR);
        assertEquals(Neuron.INACTIVE, instance.getCachedOutput(), ERROR);
        
        instance.addInput(input3, Neuron.ACTIVE);
        assertEquals(Neuron.INACTIVE, instance.getCachedOutput(), ERROR);
        assertEquals(Neuron.INACTIVE, instance.calculateOutput(), ERROR);
        assertEquals(Neuron.INACTIVE, instance.getCachedOutput(), ERROR);
        
        instance.addInput(input4, Neuron.ACTIVE);
        assertEquals(Neuron.INACTIVE, instance.getCachedOutput(), ERROR);
        assertEquals(Neuron.ACTIVE, instance.calculateOutput(), ERROR);
        assertEquals(Neuron.ACTIVE, instance.getCachedOutput(), ERROR);
    }

    @Test
    public void testCalculateRawOutput() {
        logger.info("\ntesting calculateRawOutput()");
        Neuron instance = new Neuron();
        Neuron input1 = new Neuron(new ArrayList<Neuron>(), new ArrayList<Double>(), .2);
        Neuron input2 = new Neuron(new ArrayList<Neuron>(), new ArrayList<Double>(), .2);
        Neuron input3 = new Neuron(new ArrayList<Neuron>(), new ArrayList<Double>(), .1);
        
        instance.addInput(input1, Neuron.ACTIVE);
        assertEquals(.2, instance.calculateRawOutput(), ERROR);
        
        instance.addInput(input2, Neuron.INACTIVE);
        assertEquals(.2, instance.calculateRawOutput(), ERROR);
        
        instance.addInput(input3, Neuron.ACTIVE);
        assertEquals(.3, instance.calculateRawOutput(), ERROR);
    }
    
    @Test
    public void testAddInput() {
        logger.info("\ntesting addInput()");
        Neuron instance = new Neuron();
        assertEquals(0, instance.getNumInputs());
        instance.addInput(null, 1.0);
        assertEquals(0, instance.getNumInputs());
        Neuron input1 = new Neuron();
        Neuron input2 = new Neuron();
        Neuron input3 = new Neuron();
        instance.addInput(input1, -1.0);
        assertEquals(0, instance.getNumInputs());
        instance.addInput(instance, 1.0);
        assertEquals(0, instance.getNumInputs());
        
        instance.addInput(input1, 1.0);
        assertEquals(1, instance.getNumInputs());
        instance.addInput(input2, 2.0);
        assertEquals(2, instance.getNumInputs());
        instance.addInput(input3, 3.0);
        assertEquals(3, instance.getNumInputs());
    }

    @Test
    public void testSetWeights() {
    }
    
    @Test
    public void testGetNumInputs() {
        logger.info("\ntesting getNumInputs()");
        Neuron instance = new Neuron();
        Neuron input1 = new Neuron();
        Neuron input2 = new Neuron();
        Neuron input3 = new Neuron();
        instance.addInput(input1, 1.0);
        assertEquals(1, instance.getNumInputs());
        instance.addInput(input2, 2.0);
        assertEquals(2, instance.getNumInputs());
        instance.addInput(input3, 3.0);
        assertEquals(3, instance.getNumInputs());
    }
    
    @Test
    public void testSimpleNetwork() {
        logger.info("\ntesting a simple network");
        Neuron input1 = new Neuron(Neuron.ACTIVE);
        Neuron input2 = new Neuron(Neuron.ACTIVE);
        Neuron input3 = new Neuron(Neuron.INACTIVE);
        Neuron input4 = new Neuron(Neuron.ACTIVE);
        
        Neuron hidden1 = new Neuron();
        Neuron hidden2 = new Neuron();
        Neuron hidden3 = new Neuron();
        Neuron hidden4 = new Neuron();
        Neuron hidden5 = new Neuron();
        
        Neuron output1 = new Neuron();
        Neuron output2 = new Neuron();
        Neuron output3 = new Neuron();
        Neuron output4 = new Neuron();
        
        //how add the connections
        hidden1.addInput(input1, .5);
        hidden1.addInput(input4, .4);
        hidden2.addInput(input1, .5);
        hidden2.addInput(input2, .4);
        hidden3.addInput(input3, .9);
        hidden4.addInput(input2, .9);
        hidden5.addInput(input1, .01);
        hidden5.addInput(input4, .9);
        
        assertEquals(Neuron.ACTIVE, hidden1.calculateOutput(), ERROR);
        assertEquals(Neuron.ACTIVE, hidden2.calculateOutput(), ERROR);
        assertEquals(Neuron.INACTIVE, hidden3.calculateOutput(), ERROR);
        assertEquals(Neuron.ACTIVE, hidden4.calculateOutput(), ERROR);
        assertEquals(Neuron.ACTIVE, hidden5.calculateOutput(), ERROR);
        
        output1.addInput(hidden2, .9);
        output2.addInput(hidden2, .3);
        output2.addInput(hidden3, .9);
        output3.addInput(hidden1, .9);
        output3.addInput(hidden3, .4);
        output4.addInput(hidden1, .1);
        output4.addInput(hidden4, .2);
        output4.addInput(hidden5, .2);
        
        assertEquals(Neuron.ACTIVE, output1.calculateOutput(), ERROR);
        assertEquals(Neuron.INACTIVE, output2.calculateOutput(), ERROR);
        assertEquals(Neuron.ACTIVE, output3.calculateOutput(), ERROR);
        assertEquals(Neuron.INACTIVE, output4.calculateOutput(), ERROR);
    }
}
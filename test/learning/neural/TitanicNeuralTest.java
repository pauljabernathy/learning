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

import org.apache.log4j.*;
import toolbox.util.ListArrayUtil;
import learning.genetic.DoubleGenome;

/**
 *
 * @author paul
 */
public class TitanicNeuralTest {
    
    private static Logger logger;
    
    public TitanicNeuralTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        logger = ListArrayUtil.getLogger(TitanicNeuralTest.class, Level.DEBUG);
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
    public void testGetTwoLayerNetwork() {
        logger.info("\ntesting getTwoLayerNetwork");
        TitanicNeural instance = new TitanicNeural();
        Neuron[][] network = instance.getTwoLayerNetwork(new DoubleGenome(14));
        assertEquals(3, network.length);
        assertEquals(14, network[0].length);
        for(Neuron n : network[0]) {
            assertEquals(0, n.getNumInputs());
        }
        assertEquals(14, network[1].length);
        for(Neuron n : network[1]) {
            assertEquals(1, n.getNumInputs());
        }
        assertEquals(1, network[2].length);
        assertEquals(14, network[2][0].getNumInputs());
    }
    
    @Test
    public void testGetSensorsAndFirstLayer() {
        logger.info("\ntesting getSensorsAndFirstLayer()");
        TitanicNeural instance = new TitanicNeural();
        Neuron[][] network = instance.getSensorsAndFirstLayer();
        assertEquals(2, network.length);
        assertEquals(14, network[0].length);
        for(Neuron n : network[0]) {
            assertEquals(0, n.getNumInputs());
        }
        assertEquals(14, network[1].length);
        for(Neuron n : network[1]) {
            assertEquals(1, n.getNumInputs());
        }
    }
}
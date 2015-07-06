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
import org.apache.logging.log4j.*;
import java.util.ArrayList;
import java.util.List;

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
        
        
        
        //testing problem discovered in TitanicNeural
        
        input1 = new Neuron(new ArrayList<Neuron>(), new ArrayList<Double>(), 0);
        input2 = new Neuron(new ArrayList<Neuron>(), new ArrayList<Double>(), 0);
        input3 = new Neuron(new ArrayList<Neuron>(), new ArrayList<Double>(), 1.0);
        Neuron input4 = new Neuron(new ArrayList<Neuron>(), new ArrayList<Double>(), 0);
        Neuron input5 = new Neuron(new ArrayList<Neuron>(), new ArrayList<Double>(), 1.0);
        Neuron input6 = new Neuron(new ArrayList<Neuron>(), new ArrayList<Double>(), 0);
        Neuron input7 = new Neuron(new ArrayList<Neuron>(), new ArrayList<Double>(), 0);
        Neuron input8 = new Neuron(new ArrayList<Neuron>(), new ArrayList<Double>(), 1);
        Neuron input9 = new Neuron(new ArrayList<Neuron>(), new ArrayList<Double>(), 0);
        Neuron input10 = new Neuron(new ArrayList<Neuron>(), new ArrayList<Double>(), 0);
        Neuron input11 = new Neuron(new ArrayList<Neuron>(), new ArrayList<Double>(), 0);
        Neuron input12 = new Neuron(new ArrayList<Neuron>(), new ArrayList<Double>(), 0);
        Neuron input13 = new Neuron(new ArrayList<Neuron>(), new ArrayList<Double>(), 1);
        Neuron input14 = new Neuron(new ArrayList<Neuron>(), new ArrayList<Double>(), 0);
        
        Neuron hidden1 = new Neuron();
        hidden1.addInput(input1, 1.0);
        hidden1.addInput(input2, 1.0);
        hidden1.addInput(input3, 1.0);
        hidden1.addInput(input4, 1.0);
        hidden1.addInput(input5, 1.0);
        hidden1.addInput(input6, 1.0);
        hidden1.addInput(input7, 1.0);
        hidden1.addInput(input8, 1.0);
        hidden1.addInput(input9, 1.0);
        hidden1.addInput(input10, 1.0);
        hidden1.addInput(input11, 1.0);
        hidden1.addInput(input12, 1.0);
        hidden1.addInput(input13, 1.0);
        hidden1.addInput(input14, 1.0);
        
        assertEquals(4.0, hidden1.calculateRawOutput(), 0.0);
        assertEquals(Neuron.ACTIVE, hidden1.calculateOutput(), 0.0);
        
        Neuron hidden2 = new Neuron();
        Neuron hidden3 = new Neuron();
        Neuron hidden4 = new Neuron();
        Neuron hidden5 = new Neuron();
        
        Neuron output = new Neuron();
        assertEquals(Neuron.INACTIVE, output.calculateOutput(), 0.0);

        output.addInput(hidden1, 1.0);
        output.addInput(hidden2, 1.0);
        output.addInput(hidden3, 1.0);
        output.addInput(hidden4, 1.0);
        output.addInput(hidden5, 1.0);
        
        Neuron[][] network = new Neuron[4][];
        //0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0
        network[0] = new Neuron[] { new Neuron(0.0), new Neuron(0.0), new Neuron(1.0), new Neuron(0.0), new Neuron(1.0), new Neuron(0.0), new Neuron(0.0), new Neuron(1.0), new Neuron(0.0), new Neuron(0.0), new Neuron(0.0), new Neuron(0.0), new Neuron(1.0), new Neuron(0.0) };
        network[1] = new Neuron[] { input1, input2, input3, input4, input5, input6, input7, input8, input9, input10, input11, input12, input13, input14 };
        
        network = this.getThreeLayerNetwork3(5);
        for(int i = 0; i < 14; i++) {
            network[1][i].addInput(network[0][i], 1.0);
            network[1][i].setOutput(Neuron.INACTIVE);
        }
        network[2] = new Neuron[] { hidden1, hidden2, hidden3, hidden4, hidden5 };
        network[3] = new Neuron[] { output };
        for(int i = 0; i < 5; i++) {
            network[2][i].setOutput(Neuron.INACTIVE);
        }
        //assertEquals(Neuron.ACTIVE, output.calculateOutput(), 0.0);
        
        this.recalculateNetworkOutput(network);
        
    }
    
    public Neuron[][] getThreeLayerNetwork3(int hiddenLayerSize) {
        
        Neuron[][] network = new Neuron[4][];
        Neuron[][] inputs = this.getSensorsAndFirstLayer();
        network[0] = inputs[0];
        network[1] = inputs[1];
        network[2] = new Neuron[hiddenLayerSize];
        network[3] = new Neuron[1];
        
        int numConnections = network[1].length * hiddenLayerSize + hiddenLayerSize;
        
        for(int j = 0; j < hiddenLayerSize; j++) {
            network[2][j] = new Neuron();
        }
        for(int i = 0; i < inputs[1].length; i++) {
            for(int j = 0; j < hiddenLayerSize; j++) {
                //weights.get(i * (hiddenLayerSize) + j);
                network[2][j].addInput(network[1][i], 1.0);
            }
        }
        
        Neuron output = new Neuron();
        int start = inputs[1].length * hiddenLayerSize;
        for(int j = 0; j < hiddenLayerSize; j++) {
            output.addInput(network[2][j], 1.0);
        }
        network[3][0] = output;
        return network;
    }
    
    public Neuron[][] getSensorsAndFirstLayer() {
        Neuron[][] network = new Neuron[2][];
        Neuron input1 = new Neuron(new ArrayList<Neuron>(), new ArrayList<Double>(), 0);
        Neuron input2 = new Neuron(new ArrayList<Neuron>(), new ArrayList<Double>(), 0);
        Neuron input3 = new Neuron(new ArrayList<Neuron>(), new ArrayList<Double>(), 1.0);
        Neuron input4 = new Neuron(new ArrayList<Neuron>(), new ArrayList<Double>(), 0);
        Neuron input5 = new Neuron(new ArrayList<Neuron>(), new ArrayList<Double>(), 1.0);
        Neuron input6 = new Neuron(new ArrayList<Neuron>(), new ArrayList<Double>(), 0);
        Neuron input7 = new Neuron(new ArrayList<Neuron>(), new ArrayList<Double>(), 0);
        Neuron input8 = new Neuron(new ArrayList<Neuron>(), new ArrayList<Double>(), 1);
        Neuron input9 = new Neuron(new ArrayList<Neuron>(), new ArrayList<Double>(), 0);
        Neuron input10 = new Neuron(new ArrayList<Neuron>(), new ArrayList<Double>(), 0);
        Neuron input11 = new Neuron(new ArrayList<Neuron>(), new ArrayList<Double>(), 0);
        Neuron input12 = new Neuron(new ArrayList<Neuron>(), new ArrayList<Double>(), 0);
        Neuron input13 = new Neuron(new ArrayList<Neuron>(), new ArrayList<Double>(), 1);
        Neuron input14 = new Neuron(new ArrayList<Neuron>(), new ArrayList<Double>(), 0);
        
        network[0] = new Neuron[] { new Neuron(0.0), new Neuron(0.0), new Neuron(1.0), new Neuron(0.0), new Neuron(1.0), new Neuron(0.0), new Neuron(0.0), new Neuron(1.0), new Neuron(0.0), new Neuron(0.0), new Neuron(0.0), new Neuron(0.0), new Neuron(1.0), new Neuron(0.0) };
        network[1] = new Neuron[] { input1, input2, input3, input4, input5, input6, input7, input8, input9, input10, input11, input12, input13, input14 };
        
        return network;
    }
    
    public Neuron[][] recalculateNetworkOutput(Neuron[][] network) {
        if(network == null) {
            return new Neuron[4][];
        }
        for(int i = 1; i < network.length; i++) {
            if(network[i] == null) {
                continue;
            }
            for(int j = 0; j < network[i].length; j++) {
                //network[i][j].setOutput(network[i][j].calculateOutput());       //the problem seems to be with this line
                System.out.println("before network[" + i + "][" + j + "].calculateOutput():  " + network[i][j].getCachedOutput());System.out.flush();
                network[i][j].calculateOutput();
                System.out.println("after network[" + i + "][" + j + "].calculateOutput():  " + network[i][j].getCachedOutput());System.out.flush();
            }
        }
        return network;
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
    public void testGetInputsAndGetWeights() {
        logger.info("\ntesting getInputs() and getWeights()");
        Neuron output = new Neuron(null, null, Neuron.INACTIVE);
        checkSizes(output.getInputs(), output.getWeights(), 0); 
        
        output = new Neuron();
        checkSizes(output.getInputs(), output.getWeights(), 0);
        
        output.addInput(new Neuron(), 1.0);
        checkSizes(output, 1);
        assertEquals(1.0, output.getWeights().get(0), 0.0);
    }
    
    private void checkSizes(Neuron n, int size) {
        checkSizes(n.getInputs(), n.getWeights(), size);
    }
    
    private void checkSizes(List a, List b, int size) {
        //first, check if they are null or empty
        if((a == null && (b == null || b.isEmpty())) || b == null && (a == null || a.isEmpty())) {
            assertEquals(size, 0);
            return;
        } else if(a == null && b != null && !b.isEmpty()) {
            fail("a is null but b is not null or empty");
        } else if(b == null && a != null && !a.isEmpty()) {
            fail("b is null but a is not null or empty");
        }
        assertEquals(size, a.size());
        assertEquals(size, b.size());
    }

    @Test
    public void testSetInuts() {
        logger.info("\ntesting setInputs()");
        Neuron neuron1 = new Neuron();
        
        //first, add a couple and make sure it works as intended
        Neuron neuron2 = new Neuron();
        Neuron neuron3 = new Neuron();
        neuron2.setOutput(Neuron.ACTIVE);
        neuron3.setOutput(Neuron.ACTIVE);
        neuron1.addInput(neuron2, .3);
        neuron1.addInput(neuron3, .2);
        assertEquals(2, neuron1.getNumInputs());
        assertEquals(.5, neuron1.calculateRawOutput(), 0.0);
        
        //now switch it up and make sure the number of inputs and the output are what they should be for the new inputs
        //first, check null, empty
        ArrayList<Neuron> neurons = null;
        ArrayList<Double> weights = null;
        neuron1.setInputs(neurons, weights);
        assertEquals(0, neuron1.getNumInputs());
        assertEquals(0.0, neuron1.calculateRawOutput(), 0.0);
        
        neurons = new ArrayList<Neuron>();
        weights = new ArrayList<Double>();
        neuron1.setInputs(neurons, weights);
        assertEquals(0, neuron1.getNumInputs());
        assertEquals(0, neuron1.calculateRawOutput(), 0.0);
        
        //now with real values
        Neuron neuron4 = new Neuron(Neuron.ACTIVE);
        Neuron neuron5 = new Neuron(Neuron.ACTIVE);
        Neuron neuron6 = new Neuron(Neuron.ACTIVE);
        neurons.add(neuron4);
        neurons.add(neuron5);
        neurons.add(neuron6);
        weights.add(.1);
        weights.add(.1);
        weights.add(.1);
        neuron1.setInputs(neurons, weights);
        assertEquals(3, neuron1.getNumInputs());
        assertEquals(.3, neuron1.calculateRawOutput(), 0.0000001);
        
        //now with mismatched lengths
        //using a new ArrayList because using the same one doesn't work because it is actually a pointer and doing weights.add(.4) changes the Neuron's weights stealthily.
        ArrayList<Double> weights2 = new ArrayList<Double>();
        weights2.add(.1);
        weights2.add(.1);
        weights2.add(.1);
        weights2.add(.4);
        neuron1.setInputs(neurons, weights2);
        assertEquals(3, neuron1.getNumInputs());
        assertEquals(.3, neuron1.calculateRawOutput(), 0.0000001);
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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package learning.neural;

import java.util.ArrayList;

//TODO:  a factory for this; pass in the inputs and weights in the constructor; this should help set up small a test network

/**
 *
 * @author paul
 */
public class Neuron {
    
    private ArrayList<Neuron> inputs;
    private ArrayList<Double> weights;
    private double output;
    public static final double THRESHOLD = .8;
    public static final double INACTIVE = 0.0;
    public static final double ACTIVE = 1.0;
    
    public Neuron() {
        /*this.inputs = new ArrayList<Neuron>();
        this.weights = new ArrayList<Double>();
        this.output = 0.0;*/
        this(new ArrayList<Neuron>(), new ArrayList<Double>(), INACTIVE);
    }
    
    public Neuron(double output) {
        this(new ArrayList<Neuron>(), new ArrayList<Double>(), output);
    }
    
    public Neuron(ArrayList<Neuron> inputs, ArrayList<Double> weights, double output) {
        this.inputs = inputs;
        this.weights = weights;
        this.output = output;
    }
    /**
     * returns the last calculated value of the output; Generally, calculateOutput() should be used.  This is only here for cases where an update is not expected and where performance is critical.
     * @return 
     */
    public double getCachedOutput() {
        return this.output;
    }
    
    /**
     * calculates the output of the neuron
     * @return 
     */
    public synchronized double calculateOutput() {
        double result = 0.0;
        /*for(int i = 0; i < this.weights.size(); i++) {
            result += this.weights.get(i) * this.inputs.get(i).getCachedOutput();
            if(result >= THRESHOLD) {
                result = ACTIVE;
                //break;
                this.output = result;
                return this.output;
            }
        }
        this.output = INACTIVE;
        return this.output;*/
        result = this.calculateRawOutput();
        if(result >= THRESHOLD) {
            this.output = ACTIVE;
        } else {
            this.output = INACTIVE;
        }
        return this.output;
    }
    
    protected double calculateRawOutput() {
        double result = 0.0;
        for(int i = 0; i < this.weights.size(); i++) {
            result += this.weights.get(i) * this.inputs.get(i).getCachedOutput();
        }
        return result;
    }
    
    //TODO:  create sensor class and move this there
    public void setOutput(double output) {
        this.output = output;
    }
    
    /**
     * adds the given neuron as an input with the specified weight; If the input is null or the weight is less than 0, or if the input is this same object, nothing is added.
     * @param neuron
     * @param weight 
     */
    public void addInput(Neuron neuron, double weight) {
        if(neuron == null || weight < 0.0 || neuron == this) {
            return;
        }
        this.inputs.add(neuron);
        this.weights.add(weight);
    }
    
    public void setWeights() {
        
    }
    
    public int getNumInputs() {
        return this.inputs.size();
    }
}

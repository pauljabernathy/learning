/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package learning.genetic;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import toolbox.util.MathUtil;
import toolbox.stats.ProbDist;
import toolbox.random.Random;

/**
 *
 * @author paul
 */
public class DoubleGenome extends Genome {
    
    private double minRandomValue;
    private double maxRandomValue;
    public DoubleGenome(int size, ProbDist<MutationType> mutationProbs) {
        super(mutationProbs);
        super.genome = new ArrayList<Double>();
        for(int i = 0; i < size; i++) {
            super.genome.add(0.0);
        }
        this.maxRandomValue = .8;
    }
    
    public void generateRandom() {
        super.genome = Random.getUniformDoublesList(super.getSize());
    }
    
    public void generateRandom(double low, double high) {
        super.genome = Random.getUniformDoublesList(super.getSize(), low, high);
    }
    
    public Double get(int index) {
        return (Double)super.genome.get(index);
    }
    
    public List<Double> get(int start, int last) {
        if(start > last) {
            int temp = start;
            start = last;
            last = temp;
        }
        return super.genome.subList(start, last + 1);
        //int[] endPoints = toolbox.util.MathUtil.checkEndPoints(0, super.genome.size(), start, last);
        //return super.genome.subList(endPoints[0], endPoints[1]);
    }
    
    public <T> void set(int index, T element) {
        super.genome.set(index, element);
    }
    
    /**
     * sets the data inside the genome to the information in the given array; if the input is null, no change will be made
     * @param data the input array
     */
    public void setRawData(double[] data) {
        if(data == null) {
            return;
        }
        //super.genome = new ArrayList<Double>();
        List<Double> input = new ArrayList<Double>();
        for(int i = 0; i < data.length; i++) {
            input.add(data[i]);
        }
        setRawData(input);
    }
    
    /**
     * sets the data inside the genome to the data in the given List; if the input is null, no change will be made
     * @param data the input array
     */
    public synchronized void setRawData(List<Double> data) {
        if(data == null) {
            return;
        }
        super.genome = new ArrayList<Double>();
        if(data.isEmpty()) {
            return;
        }
        //element by element copy, so later changes to the input list do not affect this
        
        for(Double d : data) {
            super.genome.add(d);
        }
        
        this.minRandomValue = Collections.min(data);
        this.maxRandomValue = Collections.max(data);
    }
    
    //TODO:  have mutate return a new genome but not update this's genome yet - let the calling code update it when 
    public List<Double> mutate() {
        MutationType m = super.getRandomMutationType();
        List<Double> mutated = new ArrayList<Double>();
        for(int i = 0; i < super.genome.size(); i++) {
            mutated.add((Double)super.genome.get(i));
        }
        if(m == MutationType.POINT_VALUE_CHANGE) {
            //int index = (int)(Math.random() * super.getSize());
            //mutated.set(index, toolbox.random.Random.getUniformDoubles(1, this.minRandomValue, this.maxRandomValue)[0]);
            mutated = this.doPointChangeMutation();
        } else if(m == MutationType.MULTIPLE_POINT_VALUE_CHANGE) {
            mutated = this.doMultiplePointValueChangeMutation();
        } else if(m == MutationType.SWAP) {
            mutated = this.doSwapMutation();
        } else if(m == MutationType.GROUP_REVERSAL) {
            mutated = this.doGroupReversalMutation();
        }
        return mutated;
    }
    
    protected List<Double> doPointChangeMutation() {
        List<Double> mutated = new ArrayList<Double>();
        for(int i = 0; i < super.genome.size(); i++) {
            mutated.add((Double)super.genome.get(i));
        }
        int index = (int)(Math.random() * super.getSize());
        mutated.set(index, toolbox.random.Random.getUniformDoubles(1, this.minRandomValue, this.maxRandomValue)[0]);
        return mutated;
    }
    
    protected List<Double> doMultiplePointValueChangeMutation() {
        List<Double> mutated = new ArrayList<Double>();
        for(int i = 0; i < super.genome.size(); i++) {
            mutated.add((Double)super.genome.get(i));
        }
        List<Integer> indecesChanged = new ArrayList<Integer>();
        for(int i = 0; i < super.genome.size() / 5; i++) {
            int index = (int)(Math.random() * super.getSize());
            while(indecesChanged.contains(index)) {
                index = (int)(Math.random() * super.getSize());
            }
            //TODO:  maybe a loop counter here...
            double newValue = toolbox.random.Random.getUniformDoubles(1, this.minRandomValue, this.maxRandomValue)[0];
            while(newValue == mutated.get(index)) {
                newValue = toolbox.random.Random.getUniformDoubles(1, this.minRandomValue, this.maxRandomValue)[0];
            }
            mutated.set(index, newValue);
            indecesChanged.add(index);
        }
        return mutated;
    }
    
    protected List<Double> doSwapMutation() {
        List<Double> mutated = new ArrayList<Double>();
        for(int i = 0; i < super.genome.size(); i++) {
            mutated.add((Double)super.genome.get(i));
        }
        try {
            List<Integer> indeces = toolbox.random.Random.sample(MathUtil.seqInteger(0, super.genome.size() - 1), 2, false);
            double temp = mutated.get(indeces.get(0));
            mutated.set(indeces.get(0), mutated.get(indeces.get(1)));
            mutated.set(indeces.get(1), temp);
        } catch(Exception e) {
            //should only get here if the size of the genome is less than 2...
            //TODO:  handle exception
            e.printStackTrace();
        }
        return mutated;
    }
    //TODO:  debug defect of occasionally not performing a swap
    protected List<Double> doGroupReversalMutation() {
        List<Double> mutated = new ArrayList<Double>();
        if(super.genome == null || super.genome.size() < 2) {
            return mutated;
        }
        
        try {
            List<Integer> indeces = toolbox.random.Random.sample(MathUtil.seqInteger(0, super.genome.size() - 1), 2, false);
            mutated = DoubleGenome.doGroupReversalMutation(genome, indeces.get(0), indeces.get(1));
        } catch(Exception e) {
            //should only get here if the size of the genome is less than 2...
            //TODO:  handle exception
            e.printStackTrace();
        }
        return mutated;
    }
    
    protected static List<Double> doGroupReversalMutation(List<Double> genome, int first, int last) {
        //System.out.println("doGroupReversal(genome, " + first + ", " + last + ")");
        if(genome == null || genome.size() == 0) {
            return new ArrayList<Double>();
        }
        if(genome.size() == 1) {
            return genome;
        }
        List<Double> mutated = new ArrayList<Double>();
        for(int i = 0; i < genome.size(); i++) {
            mutated.add(genome.get(i));
        }
        
        int start = 0;
        int stop = 0;
        if(first < last) {
            start = first;
            stop = last;
        } else {
            stop = first;
            start = last;
        }
        Double temp;
        int swap1 = start;
        int swap2 = stop;
        //System.out.println("swap1 = " + swap1 + ";  swap2 = " + swap2);
        while(swap1 < swap2) {
            temp = mutated.get(swap1);
            mutated.set(swap1, mutated.get(swap2));
            mutated.set(swap2, temp);
            swap1++;
            swap2--;
        }
        return mutated;
    }
    
    //@Override
    /**
     * clones this object
     */
    public DoubleGenome clone() {
        DoubleGenome clone = new DoubleGenome(this.getSize(), super.mutationProbs);
        List<Double> cloneData = new ArrayList<Double>();
        for(int i = 0; i < super.getSize(); i++) {
            cloneData.add((Double)(super.genome.get(i)));
        }
        clone.setRawData(cloneData);
        return clone;
    }
}

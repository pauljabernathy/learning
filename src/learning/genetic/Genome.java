/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package learning.genetic;

import java.util.List;
import java.util.ArrayList;
import toolbox.stats.ProbDist;

/**
 *
 * @author paul
 */
public abstract class Genome {
    
    //private double[] genome;
    protected List genome;
    protected ProbDist<MutationType> mutationProbs;
    
    /**
     * This is not working as well as I hoped because objects' signatures are Genome, not the subclass, so the Lists returned are List<Object> not List<Double> etc.
     * @param size
     * @param clazz
     * @return 
     */
    public static Genome getInstance(int size, Class clazz) {
        //this.genome = new double[size];
        //this.genome = new ArrayList<T>(size);
        //T tt;
        if(clazz.equals(String.class)) {
            System.out.println("String");
        } else if(clazz.equals(Double.class)) {
            System.out.println("Double");
            return new DoubleGenome(size);
        } else if(clazz.equals(Integer.class)) {
            System.out.println("Integer");
        } else if(clazz.equals(int.class)) {
            System.out.println("int");
        }
        return null;
    }
    
    protected Genome() {
        this.genome = new ArrayList();
        this.mutationProbs = new ProbDist<MutationType>();
        this.mutationProbs.add(MutationType.POINT_VALUE_CHANGE, .25);
        this.mutationProbs.add(MutationType.MULTIPLE_POINT_VALUE_CHANGE, .35);
        this.mutationProbs.add(MutationType.POINT_DELETION, 0.0);   //let's not deal with changing the length just yet...
        this.mutationProbs.add(MutationType.SWAP, .2);
        this.mutationProbs.add(MutationType.GROUP_REVERSAL, .2);
    }
    public int getSize() {
        return genome.size();
    }
    
    /*public T get(int i) {
        return genome.get(i);
    }*/
    
    public abstract Object get(int index);
    
    /**
     * 
     * @param first
     * @param last
     * @return the data in the genome starting with element start and ending with element last, inclusive
     */
    /*public List<T> get(int first, int last) {
        List<T> result = new ArrayList<T>();
        for(int i = first; i < last; i++) {
            result.add(this.genome.get(i));
        }
        return result;
    }*/
    public abstract List get(int first, int last);
    
    /**
     * @return the raw data as a list
     */
    public List getRawData() {
        return this.genome;
    }
    
    public abstract <T> void set(int index, T element); /*{
        this.genome.set(index, element);
    }*/
    
    public abstract List mutate();
    
    protected MutationType getRandomMutationType() {
        return this.mutationProbs.getRandomValue();
    }
}

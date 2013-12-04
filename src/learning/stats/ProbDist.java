/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package learning.naivebayes;
import java.util.*;
import java.math.BigDecimal;
import java.math.MathContext;

/**
 *
 * @author paul
 */
public class ProbDist<T> {
    //private HashMap<T, Double> distribution;
    private ArrayList<T> values;
    ArrayList<Double> probabilities;
    private final T UNKNOWN = null;
    private static final double MIN_UNKNOWN = 0.0001;
    
    public ProbDist() {
        this.reset();
    }

    public void reset() {
        //this.setDistribution(new HashMap<T, Double>());
        this.setValues(new ArrayList<T>());
        this.setProbabilities(new ArrayList<Double>());
        this.values.add(UNKNOWN);
        this.probabilities.add(1.0);
    }
    /*public HashMap<T, Double> getDistribution() {
        return distribution;
    }*/

    public List<T> getValues() {
        return values;
    }

    public T getValue(int index) {
        if(this.getValues() == null || index < 0 || this.getValues().size() <= index) {
            return null;
        } else if(this.contains(UNKNOWN) && this.getValues().size() == 1) {
            return null;
        } else if(this.contains(UNKNOWN) && this.getValues().size() > 1) {
            int newIndex = index + 1;
            if(newIndex >= this.getValues().size()) {
                return null;
            } else {
                return this.getValues().get(newIndex);
            }
        } else {
            return this.getValues().get(index);
        }
    }
    
    public void setValues(List<T> values) {
        this.values = (ArrayList<T>)values;
    }

    public boolean add(T value, double probability) {
        //TODO:  value.equals("") does not work with generics
        if(value == null || value.equals("") || probability <= 0.0 || probability >= 1.0) {
            return false;
        }
        //if(T.getClass() == "java.lang.String") {
            
        //}
        if(this.contains(value)) {
            return false;
        }
        
        //see if there is any unknown part left
        int unknownIndex = this.getValues().indexOf(UNKNOWN);
        double unknownProb = 0.0;
        if(unknownIndex > -1 && unknownIndex < this.getProbabilities().size()) {
            unknownProb = this.getProbabilities().get(unknownIndex);
        }
        //System.out.println("unknownProb = " + unknownProb);
        //see if there is still space
        if(probability > unknownProb + MIN_UNKNOWN) {
            System.out.println(probability + " > " + unknownProb + " + " + MIN_UNKNOWN + "; returning false");
            return false;
        }
        
        //if any unkown, subtract from unknown's prob
        if(-1 < unknownIndex && unknownIndex < this.getProbabilities().size()) {
            unknownProb -= probability;
            if(unknownProb < MIN_UNKNOWN) {
                this.getProbabilities().remove(unknownIndex);
                this.getValues().remove(unknownIndex);
            } else {
                this.getProbabilities().set(unknownIndex, unknownProb);
            }
        }
        
        //now it is safe to add it
        //TODO:  some kind of synchronization?
        this.getValues().add(value);
        this.getProbabilities().add(probability);
        return true;
    }
    
    public static boolean validateNormalized(List<Double> probs) {
        if(probs == null) {
            return false;
        }
        double total = 0.0;
        for(int i = 0; i < probs.size(); i++) {
            BigDecimal current = new BigDecimal(probs.get(i), new MathContext(2));
            total += current.doubleValue();
        }
        if(total == 1.0) {
            return true;
        } else {
            return false;
        }
    }
    
    //TODO:  check for normalized before getting a random value?  what to do if not normalized?
    public T getRandomValue() {
        double rand = Math.random();
        double sum = 0.0;
        int index = 0;
        if(this.getProbabilities() == null || this.getProbabilities().size() == 0 || this.getValues() == null || this.getValues().size() == 0) {
            return null;
        }
        while(sum < rand) {
            sum += this.getProbabilities().get(index);
            if(sum < rand) {
                index++;
            }
        }
        return this.getValues().get(index);
    }
    
    /*public void setDistribution(HashMap<T, Double> distribution) {
        this.distribution = distribution;
    }*/

    public ArrayList<Double> getProbabilities() {
        return probabilities;
    }

    public void setProbabilities(ArrayList<Double> probabilities) {
        this.probabilities = probabilities;
    }
    
    public boolean contains(T value) {
        return this.getValues().contains(value);
    }
    //TODO:  class cast exception?
    public double probatilityOf(Object obj) {
        if(!contains((T)obj)) {
            return 0.0;
        } else {
            return this.getProbabilities().get(this.getValues().indexOf(obj));
        }
    }
    
    public void display() {
        System.out.println("\n\n");
        double total = 0.0;
        for(int i = 0; i < this.getProbabilities().size(); i++) {
            System.out.println(this.getValues().get(i) + " " + this.getProbabilities().get(i));
            total += this.getProbabilities().get(i);
        }
        System.out.println("total = " + total);
    }
}

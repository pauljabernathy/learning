/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package learning.naivebayes;
import learning.stats.ProbDist;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author paul
 */
public class Classification<T> {
    private String name;
    private ArrayList<ProbDist<T>> featureDists;
    
    public Classification(String name) {
        this.setName(name);
        this.setFeatureDists(new ArrayList<ProbDist<T>>());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProbDist<T>> getFeatureCPDs() {
        return featureDists;
    }

    public void setFeatureDists(List<ProbDist<T>> featureDists) {
        this.featureDists = (ArrayList<ProbDist<T>>)featureDists;
    }
    
    //TODO:  Is 0 the best result for empty list, or should it be 1?
    /**
     * Gives the probability of a list of attributes appearing in the Classification.  If the list is null or empty, returns 0.
     * It matches each object in the list with each object in the Classification's internal list in the same order, 
     * so the size and types of the input list must match the Classification's internal list.
     * @param attrs
     * @return 
     */
    public double probabilityOf(List attrs) {
        if(attrs == null || attrs.size() == 0) {
            return 0.0;
        }
        double prob = 1.0;
        //iterate through dists, getting probability of each thing in attrs
        for(int i = 0; i < this.getFeatureCPDs().size(); i++) {
            //prob *= this.getFeatureCPDs().get(i).probatilityOf(attrs.get(i));
            ProbDist attrDist =  this.getFeatureCPDs().get(i);
            prob *= attrDist.probatilityOf(attrs.get(i));
        }
        return prob;
    }
    
    public String toString() {
        return this.getName();
    }
    
}

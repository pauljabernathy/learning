/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package learning.naivebayes;

import toolbox.stats.*;
import toolbox.io.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.io.*;

import org.apache.logging.log4j.*;
import toolbox.Constants;
import toolbox.util.ListArrayUtil;

/**
 *
 * @author paul
 */
public class Classifier {
    
    private ProbDist<Classification> dist;
    //private static final String SEPARATOR = ",";
    private static final int MAX_LINES = 2000000;
    private Logger logger;
    
    /**
     * @deprecated
     */
    public Classifier() {
        this(new ProbDist(), learning.Constants.DEFAULT_LOG_LEVEL);
    }
    
    public Classifier(ProbDist dist) {
        this(dist, learning.Constants.DEFAULT_LOG_LEVEL);
        
    }
    public Classifier(ProbDist dist, Level level) {
        this.dist = dist;
        logger = ListArrayUtil.getLogger(this.getClass(), learning.Constants.DEFAULT_LOG_LEVEL);
    }

    public ProbDist<Classification> getDist() {
        return dist;
    }

    public void setDist(ProbDist<Classification> dist) {
        this.dist = dist;
    }
    
    public void classify(String filename) {
        List<List> data = (List<List>)CSVReader.getRowsAsLists(filename, MAX_LINES);  //must cast to List<List> to satifsfy compiler, even though that's what it already is
        classify(data);
    }
    
    public List<String> classify(List<List> data) {
        int numCorrect = 0;
        boolean wasCorrect = false;
        List<String> results = new ArrayList<String>();
        int count = 0;
        for(List features : data) {
            //System.out.print(count++ + " ");
            /**/
            int i = 0;
            logger.debug("features.size() = " + features.size());
            for(Object feature : features) {
                //System.out.print(i++ + " " + feature + " ");System.out.flush();
            }/**/
            Classification classification = this.classifyOne(features);
            logger.debug(classification.getName());
            results.add(classification.getName());
        }
        return results;
    }
    
    public Classification classifyOne(String[] features) {
        return classifyOne(getAsList(features));
    }
    /*
     * Classifying can by done by either listing all probabilities at the start and plugging those into Bayes Equ., or by calculating each 
     * part of the equation for each classification
     */
    public Classification classifyOne(List features) {
        //prob of classification x given features = (prob of features given class x times prob of class x) / total prob of features
        
        double pF = findProbOfFeatures(features);
        logger.debug(pF);
        //TODO:  error checking on pf being 0! -> return null or an empty classification since this would mean we have a set of features that cannot happen in our set of classifications
        
        double pCgivenF = 0.0;
        double pC = 0.0;
        double pFgivenC = 0.0;
        int highestProbIndex = 0;
        double highestProb = 0.0;
        Classification mostLikely = new Classification("unknown");
        for(int i = 0; i < this.dist.getValues().size(); i++) {
            Classification currentClass = this.dist.getValues().get(i);
            pFgivenC = currentClass.probabilityOf(features);
            logger.debug(pFgivenC);
            pC = this.dist.getProbabilities().get(i);
            pCgivenF = pFgivenC * pC / pF;
            if(pCgivenF > highestProb) {
                highestProb = pCgivenF;
                highestProbIndex = i;
                mostLikely = currentClass;
            }
        }
        
        return mostLikely;
    }
    
    /**
     * Gives the total probability of a set of features; Ei P(f | Ci) * P(Ci)
     * @param features
     * @return 
     */
    public double findProbOfFeatures(List features) {
        List<Classification> classes = this.getDist().getValues();
        List<Double> probs = this.getDist().getProbabilities();
        
        double totalProb = 0.0;
        //TODO:  error checking on null, matching sizes
        for(int i = 0; i < classes.size(); i++) {
            totalProb += classes.get(i).probabilityOf(features) * probs.get(i);
        }
        return totalProb;
    }
    
    public List getAsList(String[] input) {
        ArrayList result = new ArrayList<String>();
        /**/result.add(input[0]);
        result.add(input[1]);
        int number = -1;
        try {
            number = Integer.parseInt(input[2]);
        } catch(NumberFormatException e) {
            System.out.println(e.getClass() + " trying to parse " + input[2] + ":  " + e.getMessage());
        }
        result.add(number);
        boolean bool;
        //try {
            bool = Boolean.parseBoolean(input[3]);
        //} //catch()
        result.add(bool);
        /**/
        /**if(input == null) {
            return result;
        }
        for(int i = 0; i < input.length; i++) {
            result.add(parseOneValue(input[i]));
        }
        /**/
        return result;
    }
    
    /**
     * looks at the input and converts it into a boolean, int, or double, if possible; returns the value if not; returns empty string "" for null input
     * @param value
     * @return 
     */
    public static Object parseOneValue(String value) {
        if(value == null) {
            return "";
        }
        if(value.equalsIgnoreCase("true")) {
            return true;
        } else if(value.equalsIgnoreCase("false")) {
            return false;
        }
        
        //int must come before double
        try {
            return Integer.parseInt(value);
        } catch(NumberFormatException e) {
            //do nothing - just try something else
        }
        
        try {
            return Double.parseDouble(value);
        } catch(NumberFormatException e) {
            //do nothing - just try something else
        }
        return value;
    }
}

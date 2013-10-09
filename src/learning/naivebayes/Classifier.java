/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package learning.naivebayes;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.io.*;

/**
 *
 * @author paul
 */
public class Classifier {
    
    private ProbDist<Classification> dist;
    private static final String SEPARATOR = ",";
    
    public Classifier() {
        this.setDist(DataGenerator.getSampleProbDist());
        Classification c = dist.getValues().get(0);
        List<ProbDist> dists = c.getFeatureCPDs();
        for(ProbDist currentDist : dists) {
            System.out.println(currentDist.getValues().get(0).getClass());
        }
    }

    public ProbDist<Classification> getDist() {
        return dist;
    }

    public void setDist(ProbDist<Classification> dist) {
        this.dist = dist;
    }
    
    public void classify(String filename) {
        System.out.println("entering classify() at " + new Date());
        int lineNum = -1;
        String line = "";
        int maxLines = 200000;
        int numCorrect = 0;
        boolean wasCorrect = false;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            if(reader.ready()) {
                reader.readLine();  //skip the header line
            }
            while(reader.ready() && lineNum < maxLines) {
                lineNum++;
                line = reader.readLine();
                //System.out.println(lineNum + "  " + line);
                String[] array = line.split(SEPARATOR);
                if(array == null) {
                    System.out.println("null array at line " + lineNum + ":  " + line);
                } else if(array.length == 0) {
                    System.out.println("empty array at line " + lineNum + ":  " + line);
                } else {
                    /*ArrayList<String> features = new ArrayList<String>();
                    for(int i = 0; i < array.length - 1; i++) { //length - 1 because lase should be the class
                        features.add(array[i]);     //TODO:  check for empty null String ?
                    }*/
                    List features = getAsList(array);
                    Classification classification = this.classifyOne(features);
                    String actualClass = array[array.length - 1];
                    /**/if(actualClass.equals(classification.getName())) {
                        wasCorrect = true;
                        numCorrect++;
                    }/**/
                    //System.out.println(lineNum + ":  said " + classification.getName() + ";  was " + actualClass + ":  " + wasCorrect);
                }
            }
            System.out.println("\nnumber correct:  " + numCorrect);
            if(lineNum != 0) {
                System.out.println("percentage correct:  " + (double)numCorrect * 100.0 / (double)lineNum);
            }
        } catch(IOException e) {
            System.out.println(e.getClass() + " in classify(" + filename + ") at line " + lineNum + ":  " + e.getMessage());
        }
        System.out.println("leaving classify() at " + new Date());
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
        //TODO:  error checking on pf being 0! -> return null or an empty classification since this would mean we have a set of features that cannot happen in our set of classifications
        
        double pCgivenF = 0.0;
        double pC = 0.0;
        double pFgivenC = 0.0;
        int highestProbIndex = 0;
        double highestProb = 0.0;
        Classification mostLikely = null;
        for(int i = 0; i < dist.getValues().size(); i++) {
            Classification currentClass = dist.getValues().get(i);
            pFgivenC = currentClass.probabilityOf(features);
            pC = dist.getProbabilities().get(i);
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
    
    private List getAsList(String[] input) {
        ArrayList result = new ArrayList<String>();
        /**/result.add(input[0]);
        result.add(input[1]);
        int number = -1;
        try {
            number = Integer.parseInt(input[2]);
        } catch(NumberFormatException e) {
            System.out.println(e.getClass() + " trying to parse " + input[3] + ":  " + e.getMessage());
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

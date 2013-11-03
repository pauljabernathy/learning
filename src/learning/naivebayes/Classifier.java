/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package learning.naivebayes;

import learning.util.Constants;
import learning.stats.*;
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
    //private static final String SEPARATOR = ",";
    private static final int MAX_LINES = 2000000;
    
    public Classifier() {
        this.setDist(DataGenerator.getSampleProbDist());
        Classification c = dist.getValues().get(0);
        List<ProbDist> dists = c.getFeatureCPDs();
        for(ProbDist currentDist : dists) {
            //System.out.println(currentDist.getValues().get(0).getClass());
        }
    }

    public ProbDist<Classification> getDist() {
        return dist;
    }

    public void setDist(ProbDist<Classification> dist) {
        this.dist = dist;
    }
    
    public void classifyOld(String filename) {
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
                String[] array = line.split(Constants.DEFAULT_SEPARATOR);
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
    
    public void classify(String filename) {
        List<List> data = (List<List>)loadFromFile(filename);   //must cast to List<List> to satifsfy compiler, even though that's what it already is
        classify(data);
    }
    
    public List<? extends List> loadFromFile(String filename) {
        List<ArrayList> result = new ArrayList<ArrayList>();
        int lineNum = -1;
        String line = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            if(reader.ready()) {
                reader.readLine(); //skip first line
                //TODO:  check to see if first line is a header or not
            }
            while(reader.ready() & lineNum < MAX_LINES) {
                lineNum++;
                line = reader.readLine();
                //System.out.println(lineNum + "  " + line);
                String[] array = line.split(Constants.DEFAULT_SEPARATOR);
                if(array == null) {
                    System.out.println("null array at line " + lineNum + ":  " + line);
                } else if(array.length == 0) {
                    System.out.println("empty array at line " + lineNum + ":  " + line);
                } else {
                    ArrayList<String> current = new ArrayList<String>();
                    for(String s : array) {
                        current.add(s);
                    }
                    result.add(current);
                }
            }
        } catch(IOException e) {
            System.out.println(e.getClass() + " in classify(" + filename + ") at line " + lineNum + ":  " + e.getMessage());
        }
        System.out.println("leaving loadFile(" + filename + ") at " + new Date());
        return result;
    }
    
    public List<String> classify(List<List> data) {
        int numCorrect = 0;
        boolean wasCorrect = false;
        List<String> results = new ArrayList<String>();
        /**int count = 0;
        for(List features : data) {
            System.out.print(count++ + " ");
            int i = 0;
            for(Object feature : features) {
                System.out.print(i++ + " " + feature + " ");
            }
            System.out.println();
        }
        
        count = 0; // /**/ int count = 0;
        for(List features : data) {
            System.out.print(count++ + " ");
            /**/
            int i = 0;
            for(Object feature : features) {
                //System.out.print(i++ + " " + feature + " ");System.out.flush();
            }/**/
            //System.out.println();
            Classification classification = this.classifyOne(features);
            //System.out.println(classification.getName());
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
        //System.out.println(pF);
        //TODO:  error checking on pf being 0! -> return null or an empty classification since this would mean we have a set of features that cannot happen in our set of classifications
        
        double pCgivenF = 0.0;
        double pC = 0.0;
        double pFgivenC = 0.0;
        int highestProbIndex = 0;
        double highestProb = 0.0;
        Classification mostLikely = new Classification("unknown");
        for(int i = 0; i < dist.getValues().size(); i++) {
            Classification currentClass = dist.getValues().get(i);
            pFgivenC = currentClass.probabilityOf(features);
            //System.out.println(pFgivenC);
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
    
    public ProbDist<Classification> getDistributions(String filename, int classColumn, int[] featureColumns, String columnSeparator) throws IOException {
        //determine probs of classifications
        ProbDist<String> classDist = getClassificationDists(filename, classColumn, columnSeparator);
        
        //for each classification, determine prob of each dimension
        List<String> classNames = classDist.getValues();
        //learning.util.Utilities.showList(classNames);
        List<ProbDist> dists = null;
        ProbDist<Classification> result = new ProbDist<Classification>();
        for(String currentName : classNames) {
            try {
                dists = getFeatureDists("titanic.csv", Constants.SURVIVED_COLUMN, currentName, featureColumns, Constants.DEFAULT_SEPARATOR);
                //learning.util.Utilities.showList(dists);
                Classification c = new Classification(currentName);
                c.setFeatureDists(dists);
                result.add(c, classDist.probatilityOf(currentName));
                //System.out.println("added " + c.getName() + " with probability " + classDist.probatilityOf(currentName) + "\n");
            } catch(IOException e) {
                throw e;
            } catch(Exception e) {
                System.err.println(e.getClass() + " " + e.getMessage());
            }
        }
        return result;
    }
    
    public ProbDist<String> getClassificationDists(String filename, int classColumn, String columnSeparator) throws IOException {
        if(filename == null || filename.equals("") || classColumn < 0 || columnSeparator == null || columnSeparator.equals("")) {
            return new ProbDist<String>();
        } 
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        //skip the header line
        if(reader.ready()) {
            reader.readLine();
        }
        String line = "";
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<Integer> counts = new ArrayList<Integer>();
        int total = 0;
        String currentName = "";
        while(reader.ready()) {
            line = reader.readLine();
            currentName = parseLine(line, classColumn, columnSeparator);
            if(!names.contains(currentName)) {
                names.add(currentName);
                counts.add(1);
            } else {
                int index = names.indexOf(currentName);
                counts.set(index, counts.get(index) + 1);
            }
            total++;
        }
        ProbDist<String> dist = ProbDist.createInstanceFromCounts(names, counts);
        //ArrayList<Double> percents = new ArrayList<Double>();
        
        return dist;
    }
    
    public List<Histogram> getFeatureHistograms(String filename, int classColumn, String classification, int[] featureColumns, String columnSeparator) throws Exception {
        verifyParameters(filename, classColumn, classification, featureColumns, columnSeparator);
        
        List<List<String>> data = (List<List<String>>)parseFile(filename, classColumn, classification, featureColumns, columnSeparator); //to hold the data about the features
        ArrayList<Histogram> result = new ArrayList<Histogram>();
        for(int i = 0; i < featureColumns.length; i++) {
            Histogram hist = new Histogram(data.get(i));
            result.add(hist);
        }
        
        return result;
    }
    /**
     * 
     * @param filename
     * @param classColumn
     * @param classification
     * @return a list of the distributions for the given classification name
     */
    public List<ProbDist> getFeatureDists(String filename, int classColumn, String classification, int[] featureColumns, String columnSeparator) throws Exception {
        verifyParameters(filename, classColumn, classification, featureColumns, columnSeparator);
        
        List<Histogram> histList = getFeatureHistograms(filename, classColumn, classification, featureColumns, columnSeparator);
        ArrayList<ProbDist> result = new ArrayList<ProbDist>();
        for(Histogram hist : histList) {
            ProbDist dist = hist.getProbDist();
            result.add(dist);
        }
        return result;
    }
    
    /**
     * 
     * @param filename
     * @param classColumn
     * @param classification
     * @param featureColumns
     * @param columnSeparator
     * @return a two demensional list containing the data from the specified columns when the classification name matched the specified classification name
     * @throws Exception 
     */
    public List<? extends List<String>> parseFile(String filename, int classColumn, String classification, int[] featureColumns, String columnSeparator) throws Exception {
        verifyParameters(filename, classColumn, classification, featureColumns, columnSeparator);
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        if(reader.ready()) {
            reader.readLine();  //read header
            //TODO:  check for header
        }
        ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>(); //to hold the data about the features
        //inner list is for each observation in a given feature
        for(int i = 0; i < featureColumns.length; i++) {
            data.add(new ArrayList<String>());
        }
        //should now have one "inner lists" for each feature
        
        String line = "";
        List<String> curFeatureValues = null;
        String className = "";
        while(reader.ready()) {
            line = reader.readLine();
            curFeatureValues = parseLine(line, featureColumns, columnSeparator);
            //learning.util.Utilities.showList(curFeatureValues);
            className = parseLine(line, classColumn, columnSeparator);
            //System.out.println(className);
            for(int i = 0; i < featureColumns.length; i++) {
                if(classification.equals(className)) {
                //add the ith value of the feature from the current line to the ith data List
                data.get(i).add(curFeatureValues.get(i));
                }
            }
        }
        return data;
    }
    
    private boolean verifyParameters(String filename, int classColumn, String classification, int[] featureColumns, String columnSeparator) throws Exception {
        if(filename == null || filename.equals("") || featureColumns == null || featureColumns.length == 0) {
            //return new ArrayList<ProbDist>();
            //TODO:  throw an exception
            throw new Exception("bad input");
        }
        return true;
    }
    
    public static String parseLine(String line, int column, String columnSeparator) {
        if(column == -1) {
            return Constants.UNKNOWN;
        }
        int[] dummy = new int[1];
        dummy[0] = column;
        //dummy = new int [] { 1 };
        List<String> value = parseLine(line, dummy, columnSeparator);
        if(value != null && value.size() > 0) {
            return value.get(0);
        } else {
            return Constants.UNKNOWN;
        }
    }
    
    /**
     * 
     * @param line
     * @param columns
     * @param columnSeparator must be an non empty String; will return an empty List if this is null or empty
     * @return 
     */
    public static List<String> parseLine(String line, int[] columns, String columnSeparator) {
        ArrayList<String> row = new ArrayList<String>();
        if(line == null /*|| line.equals("") */|| columns == null || columns.length < 1 || columnSeparator == null || columnSeparator.equals("")) {
            return row;
        }
        String[] vars = line.split(columnSeparator);
        if(vars == null ||  vars.length < columns[columns.length - 1]) {
            //since columns is sorted, it's last element should be it's largest, which can be no larger than the last column index
            return row;
        }
        for(int column : columns) {
            row.add(scrub(vars[column]));
            //row.add(vars[column]);
        }
        return row;
    }
    
    private static String scrub(String input) {
        return input.replace("\"", "");
    }
}

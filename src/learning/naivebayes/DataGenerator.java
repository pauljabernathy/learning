/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package learning.naivebayes;
import learning.stats.ProbDist;
import java.util.List;
import java.util.ArrayList;

import java.io.*;

/**
 *
 * @author paul
 */
public class DataGenerator {
    
    public static void main(String[] args) {
        DataGenerator dg = new DataGenerator();
        try {
            dg.generateData(100000, "naive.csv");
        } catch(IOException e) {
            System.out.println(e.getClass() + " " + e.getMessage());
        }
    }
    
    public void generateData(int numObservations, String outputFile) throws IOException {
        List result = null;//this.getRandomFeatures(null);
        
        //Classification c1 = new Classification("c1");
        //Classification c2 = new Classification("c2");
        
        ProbDist dists = getSampleProbDist();
        List<Classification> classes = dists.getValues();
        //c1 = classes.get(0);
        //c2 = classes.get(1);
        
        PrintWriter writer = new PrintWriter(new FileWriter(outputFile));
        writer.println("name,color,number,bool,class");
        String csv = "";
        Classification currentClass = null;
        for(int i = 0; i < numObservations; i++) {
            /*//showList(this.getRandomFeatures(dists));
            csv = listToCSV(this.getRandomFeatures(c1), c1.getName());
            writer.println(csv);
            csv = listToCSV(this.getRandomFeatures(c2), c2.getName());
            //System.out.println(i + " " + csv);
            writer.println(csv);*/
            for(int j = 0; j < classes.size(); j++) {
                currentClass = classes.get(j);
                csv = listToCSV(this.getRandomFeatures(currentClass), currentClass.getName());
                writer.println(csv);
            }
        }
        writer.flush();
        writer.close();
    }
    
    public List getRandomFeatures(Classification classification) {
        if(classification != null) {
            return getRandomFeatures(classification.getFeatureCPDs());
        } else {
            return null;
        }
    }
    public List getRandomFeatures(List<ProbDist> dists) {
        if(dists == null) {
            return null;
        }
        ArrayList features = new ArrayList();
        Object value = null;
        for(int i = 0; i < dists.size(); i++) {
            value = dists.get(i).getRandomValue();
            features.add(value);
        }
        return features;
    }
    
    public <T> List<T> getRandomFeatures1(List<ProbDist<T>> dists) {
        if(dists == null) {
            return new ArrayList<T>();
        }
        ArrayList<T> features = new ArrayList<T>();
        T value = null;
        for(int i = 0; i < dists.size(); i++) {
            value = dists.get(i).getRandomValue();
            features.add(value);
        }
        return features;
    }
    
    private <T> void showList(List<T> input) {
        if(input == null) {
            return;
        }
        //System.out.println("\n");
        for(int i = 0; i < input.size() - 1; i++) {
            //System.out.println(i + " " + input.get(i));
            System.out.print(input.get(i) + " ");
        }
        System.out.println(input.get(input.size() - 1));
    }
    
    private <T> String listToCSV(List<T> input, String classification) {
        if(input == null) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < input.size() - 1; i++) {
            //System.out.println(i + " " + input.get(i));
           result.append(input.get(i)).append(",");
        }
        result.append(input.get(input.size() - 1));
        result.append(",").append(classification);
        return result.toString();
    }
    
    public static List<Classification> getSamples() {
        List<Classification> result = new ArrayList<Classification>(0);
        
        Classification c1 = new Classification("c1");
        Classification c2 = new Classification("c2");
        ProbDist<String> names = new ProbDist<String>();
        names.add("Paul", 0.2);
        names.add("Lora", 0.3);
        names.add("Olivia", 0.2);
        names.add("Sean", 0.15);
        names.add("Shiloh", 0.15);
        //names.display();
        
        ProbDist<String> colors = new ProbDist<String>();
        colors.add("red", .3);
        colors.add("blue", .4);
        colors.add("yellow", .3);
        //colors.display();
        
        ProbDist<Integer> numbers = new ProbDist<Integer>();
        numbers.add(1, 0.25);
        numbers.add(2, 0.5);
        numbers.add(3, 0.25);
        
        ProbDist<Boolean> bools = new ProbDist<Boolean>();
        bools.add(Boolean.TRUE, .89);
        bools.add(false, .11);
        
        //List<ProbDist<String>> dists = new ArrayList<ProbDist<String>>();
        List<ProbDist> dists = new ArrayList<ProbDist>();
        dists.add(names);
        dists.add(colors);
        dists.add(numbers);
        dists.add(bools);
        //result = this.getRandomFeatures(dists);
        //showList(result);
        c1.setFeatureDists(dists);
        
        ProbDist<String> names2 = new ProbDist<String>();
        names2.add("Paul", 0.05);
        names2.add("Lora", 0.25);
        names2.add("Olivia", 0.25);
        names2.add("Sean", 0.25);
        names2.add("Shiloh", 0.20);
        //names2.display();
        
        ProbDist<String> colors2 = new ProbDist<String>();
        colors2.add("red", .2);
        colors2.add("blue", .3);
        colors2.add("yellow", .5);
        //colors2.display();
        
        ProbDist<Integer> numbers2 = new ProbDist<Integer>();
        numbers2.add(1, 0.33);
        numbers2.add(2, 0.33);
        numbers2.add(3, 0.34);
        
        ProbDist<Boolean> bools2 = new ProbDist<Boolean>();
        bools2.add(Boolean.TRUE, .5);
        bools2.add(false, .5);
        
        List<ProbDist> dists2 = new ArrayList<ProbDist>();
        dists2.add(names2);
        dists2.add(colors2);
        dists2.add(numbers2);
        dists2.add(bools2);
        c2.setFeatureDists(dists2);
        
        result.add(c1);
        result.add(c2);
        
        Classification c3 = new Classification("c3");
        ProbDist<String> names3 = new ProbDist<String>();
        names3.add("Paul", 0.15);
        names3.add("Lora", 0.15);
        names3.add("Olivia", 0.2);
        names3.add("Sean", 0.2);
        names3.add("Shiloh", 0.30);
        //names3.display();
        
        ProbDist<String> colors3 = new ProbDist<String>();
        colors3.add("red", .7);
        colors3.add("blue", .15);
        colors3.add("yellow", .15);
        //colors3.display();
        
        ProbDist<Integer> numbers3 = new ProbDist<Integer>();
        numbers3.add(1, 0.15);
        numbers3.add(2, 0.20);
        numbers3.add(3, 0.65);
        
        ProbDist<Boolean> bools3 = new ProbDist<Boolean>();
        bools3.add(Boolean.TRUE, .25);
        bools3.add(false, .75);
        
        List<ProbDist> dists3 = new ArrayList<ProbDist>();
        dists3.add(names3);
        dists3.add(colors3);
        dists3.add(numbers3);
        dists3.add(bools3);
        c3.setFeatureDists(dists2);
        result.add(c3);
        return result;
    }
    
    public static ProbDist<Classification> getSampleProbDist() {
        ProbDist<Classification> dist = new ProbDist<Classification>();
        List<Classification> classes = getSamples();
        /*for(Classification c : classes) {
            
        }*/
        dist.add(classes.get(0), 0.33);
        dist.add(classes.get(1), 0.33);
        dist.add(classes.get(2), 0.34);
        return dist;
    }
}

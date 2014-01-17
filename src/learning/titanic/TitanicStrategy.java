/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package learning.titanic;

/**
 *
 * @author paul
 */
public interface TitanicStrategy {
    
     public void runTrain(String inputFile, String outputFile);
     
     public void runTest(String inputFile, String outputFile);
}

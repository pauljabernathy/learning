/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package learning.stats;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

/**
 *
 * @author paul
 */
public class HistogramTest {
    
    public HistogramTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSetDataList() {
        System.out.println("\ntesting setDataList()");
        Histogram instance = new Histogram();
        DataList<Double> d = new DataList<Double>();
        d.add(5.0).add(2.0).add(5.0);
        instance.setDataList(d);
        List<Double> values = instance.getValues();
        assertEquals(2, values.size());
        if(!values.contains(5.0)) {
            fail("does not contain 5.0");
        }
        if(!values.contains(2.0)) {
            fail("does not contain 2.0");
        }
        int i = values.indexOf(5.0);
        List<Integer> counts = instance.getCounts();
        assertEquals(2, (int)counts.get(i));
    }

    @Test
    public void testGetProbDist() {
        System.out.println("\ntesting getProbDist()");
        Histogram instance = new Histogram();
        DataList<String> list = new DataList<String>();
        list.add("a").add("b").add("b").add("a").add("b");
        instance.setDataList(list);
        showList(instance.getValues());
        showList(instance.getCounts());
        showList(instance.getProbabilities());
        //instance.
        ProbDist pd = instance.getProbDist();
        List<Double> probs = pd.getProbabilities();
        List<String> values = pd.getValues();
        int aIndex = values.indexOf("a");
        //assertEquals(probs.get(aIndex), .4, .0001);
        int bIndex = values.indexOf("b");
        //assertEquals(probs.get(bIndex), .6, .0001);
        pd.display();
    }
    
    @Test
    public void testSize() {
        System.out.println("\ntesting size()");
    }
    
    public void showList(List list) {
        for(int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i) + " ");
        }
        System.out.println();
    }
}
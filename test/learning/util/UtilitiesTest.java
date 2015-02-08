/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package learning.util;

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
public class UtilitiesTest {
    
    public UtilitiesTest() {
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
    public void testShowList() {
    }

    @Test
    public void testShowArray() {
    }
    
    @Test
    public void testGetCondensedPermutations() {
        System.out.println("\ntesting getCondensedPermutations()");
        int[] input = null;
        input = new int[] { 5, 4, 3, 2, 2 };
        input = new int[]{ 2, 5, 7, 8, 12, 13 };//{  3, 6  };
        List<int[]> result = Utilities.getCondensedPermutations(input);
        if(result == null || result.isEmpty()) {
            fail("returned an empty list");
        }
        for(int[] current : result) {
            Utilities.showArray(current);
        }
    }
    
    @Test
    public void testGetPermutations_array() {
        System.out.println("\ntesting getPermutations");
        int[] input = null;
        input = new int[] { 5, 4, 3, 2, 2 };
        Utilities.showArray(input);
        Utilities.getPermutations(input);
    }
        
    @Test
    public void testToBinaryArray() {
        System.out.println("\ntesting toBinaryArray()");
        //Utilities.showArray(Utilities.toBinaryArray(-1));
        Utilities.showArray(Utilities.toBinaryArray(0));
        //Utilities.showArray(Utilities.toBinaryArray(1));
        //Utilities.showArray(Utilities.toBinaryArray(2));
        //Utilities.showArray(Utilities.toBinaryArray(3));
        Utilities.showArray(Utilities.toBinaryArray(4));
        //Utilities.showArray(Utilities.toBinaryArray(5));
        Utilities.showArray(Utilities.toBinaryArray(6));
        Utilities.showArray(Utilities.toBinaryArray(7));
        Utilities.showArray(Utilities.toBinaryArray(8));
        Utilities.showArray(Utilities.toBinaryArray(9));
        Utilities.showArray(Utilities.toBinaryArray(10));
        Utilities.showArray(Utilities.toBinaryArray(11));
        Utilities.showArray(Utilities.toBinaryArray(12));
        Utilities.showArray(Utilities.toBinaryArray(13));
        assertArrayEquals(new int[] { 0 }, Utilities.toBinaryArray(0));
        assertArrayEquals(new int[] { 1 }, Utilities.toBinaryArray(1));
        assertArrayEquals(new int[] { 1, 0 }, Utilities.toBinaryArray(2));
        assertArrayEquals(new int[] { 1, 1 }, Utilities.toBinaryArray(3));
        assertArrayEquals(new int[] { 1, 0, 0 }, Utilities.toBinaryArray(4));
        assertArrayEquals(new int[] { 1, 0, 1 }, Utilities.toBinaryArray(5));
        assertArrayEquals(new int[] { 1, 1, 0 }, Utilities.toBinaryArray(6));
        assertArrayEquals(new int[] { 1, 1, 1 }, Utilities.toBinaryArray(7));
        assertArrayEquals(new int[] { 1, 0, 0, 0 }, Utilities.toBinaryArray(8));
        assertArrayEquals(new int[] { 1, 0, 0, 1 }, Utilities.toBinaryArray(9));
        assertArrayEquals(new int[] { 1, 0, 1, 0 }, Utilities.toBinaryArray(10));
        assertArrayEquals(new int[] { 1, 0, 1, 1 }, Utilities.toBinaryArray(11));
        assertArrayEquals(new int[] { 1, 1, 0, 0 }, Utilities.toBinaryArray(12));
        assertArrayEquals(new int[] { 1, 1, 0, 1 }, Utilities.toBinaryArray(13));
    }
    
    @Test
    public void testLogBase2() {
        System.out.println("\ntesting logBase2()");
        System.out.println(Utilities.logBase2(1));
        System.out.println(Utilities.logBase2(2));
        System.out.println(Utilities.logBase2(3));
        System.out.println(Utilities.logBase2(4));
        System.out.println(Utilities.logBase2(5));
        System.out.println(Utilities.logBase2(6));
        System.out.println(Utilities.logBase2(7));
        System.out.println(Utilities.logBase2(8));
        System.out.println(Utilities.logBase2(9));
    }
    
    
    @Test
    public void testAndAndCondense() {
        System.out.println("\ntesting andAndCondense()");
        int[] source = new int[] { 1, 2, 3, 4, 5};
        int[] bits = new int[] { 0, 1, 0, 1, 1 };
        Utilities.showArray(Utilities.andAndCondense(source, bits));
        bits = new int[] { 0, 0, 0, 0, 0 };
        Utilities.showArray(Utilities.andAndCondense(source, bits));
        bits = new int[] { 1, 1, 1, 1, 1 };
        Utilities.showArray(Utilities.andAndCondense(source, bits));
    }
    
    @Test
    public void testAnd() {
        System.out.println("\ntesting and()");
        int[] source = new int[] { 1, 2, 3, 4, 5};
        int[] bits = new int[] { 0, 1, 0, 1, 1 };
        int[] result = null;
        assertEquals(0, Utilities.and(new int[] { 1, 2, 3, 4, 5}, new int[] { 0, 2, 0, 2 }).length);
        assertEquals(0, Utilities.and(new int[] { 1, 2, 3, 4}, new int[] { 0, 2, 0, 2, 5 }).length);
        
        assertEquals(0, Utilities.and(null, new int[] { 0, 2, 0, 2 }).length);
        assertEquals(0, Utilities.and(new int[] { }, new int[] { 0, 2, 0, 2 }).length);
        
        assertEquals(0, Utilities.and(new int[] { 1, 2, 3, 4, 5}, null).length);
        assertEquals(0, Utilities.and(new int[] { 1, 2, 3, 4, 5}, new int[] { }).length);
        
        //TODO:  automated test - test by value, not by visual
        result = Utilities.and(source, bits);
        assertEquals(0, result[0]);
        assertEquals(2, result[1]);
        assertEquals(0, result[2]);
        assertArrayEquals(new int[] { 0, 2, 0, 4, 5}, result);
        assertArrayEquals(new int[] { 0, 2, 0, 4, 5 }, Utilities.and(source, new int[] { 0, 2, 0, 2, 1 }));
        assertArrayEquals(new int[] { 0, 0, 0, 4, 5}, Utilities.and(new int[] { 1, 0, 3, 4, 5}, new int[] { 0, 2, 0, 2, 1 }));
        assertArrayEquals(new int[] { 0, 2, 0, 2, 1}, Utilities.and(new int[] { 0, 2, 0, 2, 1 }, new int[] { 1, 2, 3, 4, 5}));
    }
    
    @Test
    public void testOr() {
        System.out.println("\ntesting or()");
        System.out.println(Utilities.arrayToString(Utilities.or(new int[] { 1, 0, 0 }, new int[] { 0, 0, 0, 1})));
        System.out.println(Utilities.arrayToString(Utilities.or(new int[] { 1, 0, 0, 1, 0, 0 }, new int[] { 0, 0, 0, 1})));
    }
}
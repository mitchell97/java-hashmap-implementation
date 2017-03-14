package ca.uwo.eng.se2205b.lab5;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Includes testing for the Banking Model
 */
public class ModelTests {

    MyHashMap<Integer,Character> test = new MyHashMap<Integer,Character>();

    @Test
    public void put(){
        test.put(5,'a');
        test.put(6,'r');
        test.put(7,'k');
        test.put(3,'m');

        assertTrue(test.containsKey(5));
        assertTrue(test.containsKey(6));
        assertTrue(test.containsKey(7));
        assertTrue(test.containsKey(3));
    }

    @Test
    public void remove(){

    }

    @Test
    public void contains(){

    }
}

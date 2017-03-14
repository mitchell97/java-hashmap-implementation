package ca.uwo.eng.se2205b.lab5;

import org.junit.Test;

/**
 * Includes testing for the Banking Model
 */
public class ModelTests {

    MyHashMap<Integer,Character> test = new MyHashMap<Integer,Character>();

    @Test
    public void put(){
        test.put(5,'a');
        test.put(6,'r');
    }

    @Test
    public void remove(){

    }

    @Test
    public void contains(){

    }
}

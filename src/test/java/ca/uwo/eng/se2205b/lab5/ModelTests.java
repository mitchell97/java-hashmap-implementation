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

        test.put(10, null);
        assertEquals(null, test.get(10));


        assertTrue(test.containsKey(5));
        assertTrue(test.containsKey(6));
        assertTrue(test.containsKey(7));
        assertTrue(test.containsKey(3));

        assertEquals((Character) 'a', test.put(5,'b'));
        assertEquals((Character) 'r', test.put(6,'r'));
        assertEquals((Character) 'k', test.put(7,'o'));
        assertEquals(null, test.put(8,'d'));
    }

    @Test
    public void remove(){

        assertNull(test.remove(5));

        test.put(5,'a');
        test.put(6,'r');
        test.put(7,'k');
        test.put(3,'m');
        test.put(8,'d');

        assertEquals(null, test.remove(9));
        assertEquals((Character) 'a', test.remove(5));
        assertEquals((Character) 'r', test.remove(6));
        assertEquals((Character) 'k', test.remove(7));
        assertEquals((Character) 'm', test.remove(3));

        assertEquals(1, test.size());

    }

    @Test
    public void contains(){
        test.put(5,'a');
        test.put(6,'r');
        test.put(7,'k');
        test.put(3,'m');
        test.put(8,'d');

        assertFalse(test.containsKey(9));
        assertTrue(test.containsKey(6));

        assertTrue(test.containsValue('r'));
        assertFalse(test.containsValue('l'));
    }

    @Test
    public void rehash(){
        test.put(1,'a');
        test.put(2,'b');
        test.put(3,'c');
        test.put(4,'d');
        test.put(5,'e');
        test.put(6,'f');
        test.put(7,'g');
        assertEquals(37,test.capacity());
        test.put(8,'h');
        test.put(9,'i');
        test.put(10,'j');
        test.put(11,'k');
        test.put(12,'l');
        test.put(13,'m');
        assertEquals(79, test.capacity());
        test.put(14,'n');
        test.put(15,'o');
    }

}

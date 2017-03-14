package ca.uwo.eng.se2205b.lab5;

import java.util.*;

/**
 * Created by Mitchell on 2017-03-13.
 */
public class MyHashMap<K,V> extends AbstractMap<K,V> implements IHashMap<K,V> {

    private Entry<K,V> [] entries;
    private double loadFactor;
    private int size;
    private int capacity;
    private final double DEFAULT_LOAD_FACTOR = 0.3;
    private final int DEFAULT_CAPACITY = 17;
    private final Entry<K,V> DEFUNCT = new SimpleEntry<K, V>(null, null);

    public MyHashMap(){
        loadFactor = DEFAULT_LOAD_FACTOR;
        capacity = DEFAULT_CAPACITY;
        size = 0;
        entries = new SimpleEntry[capacity];
    }

    public MyHashMap(double loadFactor){
        this.loadFactor = loadFactor;
        capacity = DEFAULT_CAPACITY;
        size = 0;
        entries = new SimpleEntry[capacity];
    }

    private int location(int hash){
        return hash % capacity;
    }

    private void rehash(){
        System.out.println("called");
        int oldCap = capacity;
        capacity = nextPrime(capacity * 2);

        Entry<K,V> [] newEntries = new SimpleEntry[capacity];

        for (int i = 0; i < oldCap; i++) {
            if (entries[i] != null) {
                if (!entries[i].equals(DEFUNCT)) {
                    int index = location(entries[i].getKey().hashCode());

                    while (newEntries[index] != null) {
                        if (index == capacity - 1)
                            index = 0;
                        else
                            index++;
                    }

                    newEntries[index] = new SimpleEntry(entries[i].getKey(), entries[i].getValue());
                }
            }
        }

        entries = new SimpleEntry[capacity];
        System.arraycopy(newEntries,0, entries,0, capacity);
    }

    private int nextPrime(int num){
        int m = DEFAULT_CAPACITY;

        while (true) {
            m++;
            if (m <= 3)
                return m;

            boolean divisible = false;

            for (int t = 2; !divisible && t <= Math.sqrt(m); t++) {
                if (m % t == 0)
                    divisible = true;
            }

            if (!divisible){
                if(num < m)
                   return m;

            }
        }
    }

    @Override
    public double loadFactorThreshold() {
        return loadFactor;
    }

    @Override
    public double loadFactor() {
        return ((double)size)/capacity;
    }

    @Override
    public int capacity() {
        return capacity;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        int index = location(key.hashCode());

        while (entries[index] != null){
            if (entries[index].getKey().equals(key))
                return true;

            if (index == capacity-1)
                index = 0;
            else
                index++;
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        for (int i = 0; i < capacity ; i++) {
            if (entries[i] != null)
                if (entries[i].getValue().equals(value))
                    return true;
        }
        return false;
    }

    @Override
    public V get(Object key) {
        int index = location(key.hashCode());

        while (entries[index] != null){
            if (entries[index].getKey().equals(key))
                return entries[index].getValue();

            if (index == capacity-1)
                index = 0;
            else
                index++;
        }
        return null;
    }

    private int getKeyIndex(Object key){
        int index = location(key.hashCode());

        while (entries[index] != null){
            if (entries[index].getKey().equals(key))
                break;

            if (index == capacity - 1)
                index = 0;
            else
                index++;
        }

        return index;
    }

    @Override
    public V put(K key, V value) {
        if (containsKey(key))
            return entries[getKeyIndex(key)].setValue(value);

        if (size >= loadFactor * capacity)
            rehash();

        System.out.println("Capacity: " + capacity + " Size: " + size);

        int index = location(key.hashCode());

        while (entries[index] != null){
            if (entries[index].equals(DEFUNCT))
                break;

            if (index == capacity-1)
                index = 0;
            else
                index++;
        }

        entries[index] = new SimpleEntry<K, V>(key, value);
        size++;
        return null;
    }

    @Override
    public V remove(Object key) {
        int index = getKeyIndex(key);

        if (entries[index] == null)
            return null;

        V rtn = entries[index].getValue();
        entries[index] = DEFUNCT;
        size--;
        return rtn;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for(Entry<? extends K, ? extends V> entry: m.entrySet()){
            put(entry.getKey(),entry.getValue());
        }
    }

    @Override
    public void clear() {
        for (int i = 0; i < capacity ; i++) {
            entries[i] = null;
        }
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> rtn = new TreeSet<K>();

        for (int i = 0; i < capacity; i++) {
            if (entries[i] != null || !entries[i].equals(DEFUNCT))
                rtn.add(entries[i].getKey());
        }

        return rtn;
    }

    @Override
    public Collection<V> values() {
        Set<V> rtn = new TreeSet<V>();

        for (int i = 0; i < capacity; i++) {
            if (entries[i] != null || !entries[i].equals(DEFUNCT))
                rtn.add(entries[i].getValue());
        }

        return rtn;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Map)) return false;

        Map<K,V> that = (Map<K,V>) o;

        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    class EntrySet{

    }
}

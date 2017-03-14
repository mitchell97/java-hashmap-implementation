package ca.uwo.eng.se2205b.lab5;

import java.util.*;

/**
 * Created by Mitchell on 2017-03-13.
 */
public class MyHashMap<K,V> extends AbstractMap<K,V> implements IHashMap<K,V> {

    Entry<K,V> [] entries;
    double loadFactor;
    int size;
    int capacity;
    final double DEFAULT_LOAD_FACTOR = 0.3;
    final int DEFAULT_CAPACITY = 7;
    final Entry<K,V> DEFUNCT = new SimpleEntry<K, V>(null, null);

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
        entries = new SimpleEntry[7];
    }

    private int location(int hash){
        return hash % capacity;
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

    @Override
    public V put(K key, V value) {
        int index = location(key.hashCode());

        V rtn = null;

        while (entries[index] != null || entries[index].equals(DEFUNCT) || !entries[index].getKey().equals(key)){
            if (index == capacity-1)
                index = 0;
            else
                index++;
        }

        if (entries[index].getKey().equals(key))
            rtn = entries[index].setValue(value);
        else {
            entries[index] = new SimpleEntry<K, V>(key, value);
            size++;
        }
        return rtn;
    }

    @Override
    public V remove(Object key) {
        int index = location(key.hashCode());

        V rtn = null;

        while (entries[index] != null || !entries[index].getKey().equals(key)){
            if (index == capacity-1)
                index = 0;
            else
                index++;
        }

        if (entries[index].getKey().equals(key)){
            rtn = entries[index].getValue();
            entries[index] = DEFUNCT;
            size--;
        }

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
}

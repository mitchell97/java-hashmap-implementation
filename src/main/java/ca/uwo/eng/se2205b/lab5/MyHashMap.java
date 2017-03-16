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
        int m = num;

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
        if (isEmpty()) return false;

        int index = location(key.hashCode());

        while (entries[index] != null){
            if (!entries[index].equals(DEFUNCT)) {
                if (entries[index].getKey() == null) {
                    if (key == null) return true;
                }
                else {
                    if (entries[index].getKey().equals(key))
                        return true;
                }
            }
            if (index == capacity - 1)
                index = 0;
            else
                index++;
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        if (isEmpty()) return false;
        for (int i = 0; i < capacity ; i++) {
            if (entries[i] != null) {
                if (entries[i].equals(DEFUNCT))
                    continue;

                if (entries[i].getValue() == null) {
                    if (value == null)
                        return true;
                }else
                    if (entries[i].getValue().equals(value))
                        return true;
            }
        }
        return false;
    }

    @Override
    public V get(Object key)    {
        if (isEmpty()) return null;

        int index = location(key.hashCode());

        while (entries[index] != null){
            if (!entries[index].equals(DEFUNCT)){
                if (entries[index].getKey() == null){
                    if (key == null)
                        return entries[index].getValue();
                }
                else{
                    if (entries[index].getKey().equals(key))
                        return entries[index].getValue();
                }
            }
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
        if (containsKey(key)){
            if (key == null && value == null) throw new UnsupportedOperationException("CREATING DEFUNCT");
            return entries[getKeyIndex(key)].setValue(value);
        }
        if (key == null && value == null) throw new IllegalArgumentException("PUTTING DEFUNCT");

        if (size >= loadFactor * capacity)
            rehash();

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

        while (entries[index] != null){
            if (entries[index].equals(DEFUNCT))
                continue;

            if (entries[index].getKey() == null){
                if (key == null) break;
            }

            if (entries[index].getKey().equals(key)) break;
        }

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
    public Set<Entry<K, V>> entrySet() {
        return new EntrySet();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Map)) return false;

        Map<K,V> that = (Map<K,V>) o;
        if (this.size != that.size()) return false;

        for (Entry<K,V> entry : this.entrySet()) {
            if (get(entry.getKey()) == null) {
                if (that.get(entry.getKey()) != null)
                    return false;
            }
            else
            if (!get(entry.getKey()).equals(that.get(entry.getKey())))
                return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        for(Entry<K,V> entry : entrySet())
            hash += entry.hashCode();
        return hash;
    }

    private class EntrySet extends AbstractSet<Entry<K,V>>{

        @Override
        public Iterator iterator() {
            return new SetIterator();
        }

        @Override
        public int size() {
            return MyHashMap.this.size;
        }

        @Override
        public void clear() {
            MyHashMap.this.clear();
        }

        private class SetIterator implements Iterator {

            Entry<K,V> current;
            Deque<Entry<K,V>> queue;

            SetIterator(){
                current = null;
                queue = new ArrayDeque<>(MyHashMap.this.size);

                for (int i = 0; i < MyHashMap.this.capacity; i++)
                    if (MyHashMap.this.entries[i] != null)
                        if (!MyHashMap.this.entries[i].equals(DEFUNCT))
                            queue.addLast(MyHashMap.this.entries[i]);

            }

            @Override
            public boolean hasNext() {
                return !queue.isEmpty();
            }

            @Override
            public Entry<K,V> next() {
                if (!hasNext()) throw new NoSuchElementException("No element to iterate");
                current = queue.removeFirst();
                return current;
            }

            @Override
            public void remove() {
                if (current == null) throw new IllegalStateException("Removing null");
                MyHashMap.this.remove(current.getKey());
                current = null;
            }
        }

    }

}

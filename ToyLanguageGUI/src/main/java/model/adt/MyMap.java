package model.adt;

import exceptions.ExpressionException;
import model.type.IType;
import model.value.IValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyMap<K, V> implements MyIMap<K, V> {
    Map<K, V> map;

    public MyMap() {
        map = new HashMap<K, V>();
    }

    public void insert(K key, V value) {
        this.map.put(key, value);
    }

    public void remove(K key) throws ExpressionException {
        if(this.map.containsKey(key))
            this.map.remove(key);
        else
            throw new ExpressionException("Key not found");
    }

    public Map<K, V> getContent() {
        return this.map;
    }

    public boolean contains(K key) {
        return this.map.containsKey(key);
    }

    public V lookup(K key) throws ExpressionException {
        if(this.map.containsKey(key))
            return this.map.get(key);
        else
            throw new ExpressionException("Key not found");
    }

    public String toString() {
        StringBuilder st = new StringBuilder();
        this.map.forEach((k,v)->{
            st.append(k).append(" -> ").append(v).append("\n");
        });
        return "Dictionary contains: " + st.toString();

    }

    public Set<K> getKeys(){return this.map.keySet();}

    @Override
    public void update(K key, V value) {
        if(this.map.containsKey(key))
            this.map.put(key,value);
        else
            throw new ExpressionException("Key not found");
    }

    @Override
    public MyIMap<K, V> clone() {
        MyIMap<K, V> newMap = new MyMap<K, V>();
        for (K key : map.keySet()) {
            IValue value = (IValue) map.get(key);
            newMap.insert(key, (V) value.deepcopy());
        }
        return newMap;

    }

}

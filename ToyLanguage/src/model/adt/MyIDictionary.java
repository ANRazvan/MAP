package model.adt;

import exceptions.ExpressionException;
import java.util.Set;

public interface MyIDictionary<K, V> {
    void insert(K key, V value);
    void remove(K key) throws ExpressionException;
    boolean contains(K key);
    public V getValue(K key) throws ExpressionException;

    Set<K> getKeys();
}

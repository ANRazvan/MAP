package model.adt;

import exceptions.ExpressionException;

public interface MyIDictionary<K, V> {
    void insert(K key, V value);
    void remove(K key) throws ExpressionException;
    boolean contains(K key);
    public V get(K key) throws ExpressionException;
}

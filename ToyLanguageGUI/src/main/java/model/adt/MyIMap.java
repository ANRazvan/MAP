package model.adt;

import exceptions.ExpressionException;
import javafx.util.Pair;
import model.statements.IStmt;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface MyIMap<K, V> {
    void insert(K key, V value);
    void remove(K key) throws ExpressionException;
    boolean contains(K key);
    public V lookup(K key) throws ExpressionException;
    public Map<K,V> getContent();
    Set<K> getKeys();
    public void update(K key, V value);

    public MyIMap<K, V> clone();

}

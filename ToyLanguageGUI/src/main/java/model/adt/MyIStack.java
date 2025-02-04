package model.adt;

import exceptions.EmptyStackException;

import java.util.List;

public interface MyIStack<T> {
    T pop() throws EmptyStackException;
    void push(T t);
    boolean isEmpty();
    int size();
    public List<T> getContentAsList();

    T peek();
}

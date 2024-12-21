package model.adt;

import model.value.IValue;

import java.util.Map;

public interface MyIHeap {
    public int allocate(IValue val);
    public IValue getValue(int key);
    public void insert(int key, IValue val);
    public void update(int key, IValue val);
    public Map<Integer,IValue> getMap();
    public boolean containsAddr(int key);
    public void remove(int key);

    Map<Integer, IValue> getContent();

    void setContent(Map<Integer, IValue> integerIValueMap);

    int getNextFreeAddr();
}
package model.adt;

import exceptions.InterpreterException;
import model.adt.MyILockTable;

import java.util.HashMap;
import java.util.Set;

public class MyLockTable implements MyILockTable {
    private HashMap<Integer, Integer> lockTable;
    private int freeLocation = 0;

    public MyLockTable() {
        this.lockTable = new HashMap<>();
    }
    @Override
    public int getFreeValue() {
            freeLocation++;
            return freeLocation;
    }

    @Override
    public void put(int key, int value) throws InterpreterException {
            if (!lockTable.containsKey(key)) {
                lockTable.put(key, value);
            } else {
                throw new InterpreterException(String.format("Lock table already contains the key %d!", key));
            }
    }

    @Override
    public HashMap<Integer, Integer> getContent() {
            return lockTable;
    }

    @Override
    public boolean containsKey(int position) {
            return lockTable.containsKey(position);
    }

    @Override
    public int get(int position) throws InterpreterException {
        synchronized (this) {
            if (!lockTable.containsKey(position))
                throw new InterpreterException(String.format("%d is not present in the table!", position));
            return lockTable.get(position);
        }
    }

    @Override
    public void update(int position, int value) throws InterpreterException {
        synchronized (this) {
            if (lockTable.containsKey(position)) {
                lockTable.replace(position, value);
            } else {
                throw new InterpreterException(String.format("%d is not present in the table!", position));
            }
        }
    }

    @Override
    public void setContent(HashMap<Integer, Integer> newMap) {
            this.lockTable = newMap;
    }

    @Override
    public Set<Integer> keySet() {
            return lockTable.keySet();
    }

    @Override
    public String toString() {
        return lockTable.toString();
    }
}
package model.statements;

import exceptions.InterpreterException;
import exceptions.StatementException;
import model.state.PrgState;
import model.type.IntType;
import model.type.IType;
import model.adt.MyIMap;
import model.adt.MyILockTable;
import model.value.IntValue;
import model.value.IValue;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockStatement implements IStmt {
    private final String var;
    private static final Lock lock = new ReentrantLock(); // java host language mechanisms for lock

    public LockStatement(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws InterpreterException {
        lock.lock();
        MyIMap<String, IValue> symTable = state.getSymTable();
        MyILockTable lockTable = state.getLockTable();
        if (symTable.contains(var)) {
            if (symTable.lookup(var).getType().equals(new IntType())) {
                IntValue fi = (IntValue) symTable.lookup(var); // getting the address of the lock
                int foundIndex = fi.getValue();
                if (lockTable.containsKey(foundIndex)) { // checking if the lock is stored
                    if (lockTable.get(foundIndex) == -1) { // check if the lock is not held by any other PrgState
                        lockTable.update(foundIndex, state.getId()); // mark it as locked by the current PrgState
                        state.setLockTable(lockTable); // updating the LockTable
                    } else {
                        state.getExecStack().push(this); // else if the lock is already locked:), just push it back on the stack
                                                            // and wait until it's not
                                                            // held by other PrgState anymore
                    }
                } else {
                    throw new InterpreterException("Index is not in the lock table!");
                }
            } else {
                throw new InterpreterException("Var is not of Type int!");
            }
        } else {
            throw new InterpreterException("Variable not defined!");
        }
        lock.unlock();
        return null;
    }

    @Override
    public MyIMap<String, IType> typecheck(MyIMap<String, IType> typeEnv) throws InterpreterException {
        if (typeEnv.lookup(var).equals(new IntType())) {
            return typeEnv;
        } else {
            throw new InterpreterException("Var is not of Type int!");
        }
    }

    @Override
    public IStmt deepcopy() {
        return new LockStatement(var);
    }

    @Override
    public String toString() {
        return String.format("lock(%s)", var);
    }
}
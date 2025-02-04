package model.statements;

import exceptions.InterpreterException;
import model.state.PrgState;
import model.type.IntType;
import model.type.IType;
import model.adt.MyIMap;
import model.adt.MyILockTable;
import model.value.IntValue;
import model.value.IValue;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UnlockStatement implements IStmt {
    private final String var;
    private static final Lock lock = new ReentrantLock();

    public UnlockStatement(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws InterpreterException {
        lock.lock();
        MyIMap<String, IValue> symTable = state.getSymTable();
        MyILockTable lockTable = state.getLockTable();
        if (symTable.contains(var)) {
            if (symTable.lookup(var).getType().equals(new IntType())) {
                IntValue fi = (IntValue) symTable.lookup(var);
                int foundIndex = fi.getValue();
                if (lockTable.containsKey(foundIndex)) {
                    if (lockTable.get(foundIndex) == state.getId())
                        lockTable.update(foundIndex, -1);
                } else {
                    throw new InterpreterException("Index not in the lock table!");
                }
            } else {
                throw new InterpreterException("Var is not of int Types!");
            }
        } else {
            throw new InterpreterException("Variable is not defined!");
        }
        lock.unlock();
        return null;
    }

    @Override
    public MyIMap<String, IType> typecheck(MyIMap<String, IType> typeEnv) throws InterpreterException {
        if (typeEnv.lookup(var).equals(new IntType()))
            return typeEnv;
        else
            throw new InterpreterException("Var is not of Types int!");
    }

    @Override
    public IStmt deepcopy() {
        return new UnlockStatement(var);
    }

    @Override
    public String toString() {
        return String.format("unlock(%s)", var);
    }
}
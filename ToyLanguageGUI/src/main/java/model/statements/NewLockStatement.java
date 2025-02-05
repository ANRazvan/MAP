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

public class NewLockStatement implements IStmt {
    private final String var;
    private static final Lock lock = new ReentrantLock();

    public NewLockStatement(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws InterpreterException {
        lock.lock(); // making sure we`re not interrupted by other threads
        MyILockTable lockTable = state.getLockTable();
        MyIMap<String, IValue> symTable = state.getSymTable();
        int freeAddress = lockTable.getFreeValue(); // get the next free address from thr locktable in order to create a new lock
        lockTable.put(freeAddress, -1); // put the new lock in the locktable with the value -1 which means it is unlocked
        if (symTable.contains(var) && symTable.lookup(var).getType().equals(new IntType())) {
            symTable.update(var, new IntValue(freeAddress));
        }
        else
            throw new InterpreterException("Variable not declared!");
        lock.unlock(); // release the lock
        return null;
    }

    @Override
    public MyIMap<String, IType> typecheck(MyIMap<String, IType> typeEnv) throws InterpreterException {
        if (typeEnv.lookup(var).equals(new IntType()))
            return typeEnv;
        else
            throw new InterpreterException("Var is not of Type int!");
    }

    @Override
    public IStmt deepcopy() {
        return new NewLockStatement(var);
    }

    @Override
    public String toString() {
        return String.format("newLock(%s)", var);
    }
}
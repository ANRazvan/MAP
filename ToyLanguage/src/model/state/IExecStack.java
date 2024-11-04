package model.state;

import exceptions.EmptyStackException;
import model.statements.IStmt;

public interface IExecStack {
    public void push(IStmt statement);
    public IStmt pop() throws EmptyStackException;
    int size();
    boolean isEmpty();
}

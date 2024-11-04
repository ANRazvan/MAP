package model.state;

import exceptions.EmptyStackException;
import model.adt.MyStack;
import model.statements.IStmt;

public class ExecState implements IExecStack {
    private MyStack<IStmt> stack;
    public ExecState() {
        stack = new MyStack<>();
    }

    @Override
    public void push(IStmt statement) {
        this.stack.push(statement);
    }

    public IStmt pop() throws EmptyStackException {
        return this.stack.pop();
    }

    public int size(){
        return this.stack.size();
    }

    @Override
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public MyStack<IStmt> getStack() {
        return this.stack;
    }
}

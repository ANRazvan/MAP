package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.adt.*;
import model.state.PrgState;
import model.value.IValue;

public class ForkStmt implements IStmt{

    private IStmt statement;

    public ForkStmt(IStmt stmt){
        this.statement = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException {
            MyIStack<IStmt> newStack = new MyStack<IStmt>();
            MyIMap<String, IValue> newSymTable = state.getSymTable().deepcopy();
            return new PrgState(statement,newStack, newSymTable, state.getOutputList(), state.getFileTable(), state.getHeap());
    }

    @Override
    public IStmt deepcopy() {
        return null;
    }

}

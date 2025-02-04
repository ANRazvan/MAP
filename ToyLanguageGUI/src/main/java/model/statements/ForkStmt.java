package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.adt.*;
import model.state.PrgState;
import model.type.IType;
import model.value.IValue;

public class ForkStmt implements IStmt{

    private IStmt statement;

    public ForkStmt(IStmt stmt){
        this.statement = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException {
            MyIStack<IStmt> newStack = new MyStack<IStmt>();
           // MyIMap<String, IValue> newSymTable = state.getSymTable().clone();
            MyIStack<MyIMap<String,IValue>> SymTableStack = state.getSymTableStack().deepcopy();
            return new PrgState(statement,newStack, SymTableStack, state.getOutputList(), state.getFileTable(), state.getHeap(),state.getLockTable(),state.getProcedureTable());
    }

    @Override
    public IStmt deepcopy() {
        return new ForkStmt(statement.deepcopy());
    }

    @Override
    public MyIMap<String, IType> typecheck(MyIMap<String, IType> typeEnv) throws StatementException {
        return statement.typecheck(typeEnv);
    }

    @Override
    public String toString() {
        return "fork(" + statement.toString() + ")";
    }

}

package model.statements;

import exceptions.ExpressionException;
import exceptions.InterpreterException;
import exceptions.StatementException;
import model.adt.MyIMap;
import model.state.PrgState;
import model.type.IType;

public class ReturnStmt implements IStmt{

    @Override
    public PrgState execute(PrgState state) throws InterpreterException {
        try {
            state.getAllSymTables().pop();
        } catch (InterpreterException e) {
            throw new InterpreterException(e.getMessage());
        }

        return null;
    }

    @Override
    public MyIMap<String, IType> typecheck(MyIMap<String, IType> typeEnv) throws InterpreterException {
        return typeEnv;
    }

    @Override
    public IStmt deepcopy() {
        return new ReturnStmt();
    }

    @Override
    public String toString() {
        return "return";
    }
}

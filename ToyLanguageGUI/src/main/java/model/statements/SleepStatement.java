package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.adt.MyIMap;
import model.state.PrgState;
import model.type.IType;

public class SleepStatement implements IStmt {
    private int number;

    public SleepStatement(int number) {
        this.number = number;
    }


    @Override
    public String toString() {
        return "Sleep(" + number + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException {
        if (number != 0) {
            state.getExecStack().push(new SleepStatement(number - 1));
        }
        return null;
    }

    @Override
    public IStmt deepcopy() {
        return new SleepStatement(number);
    }

    @Override
    public MyIMap<String, IType> typecheck(MyIMap<String, IType> typeEnv) throws StatementException {
        return typeEnv;
    }
}

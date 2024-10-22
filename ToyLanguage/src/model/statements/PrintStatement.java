package model.statements;

import model.expressions.IExpression;
import model.state.PrgState;
import model.value.IValue;

public class PrintStatement implements IStatement {
    private IExpression expression;

    public PrintStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) {
        IValue val = expression.evaluate(state.getSymTable());
        state.getOutputList().add(val.toString());
        return state;
    }

    @Override
    public String toString() {
        return "print(" +this.expression.toString()+")";
    }
}

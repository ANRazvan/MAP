package model.statements;

import exceptions.ExpressionException;
import model.expressions.IExpression;
import model.state.PrgState;
import model.value.IValue;

public class PrintStmt implements IStmt {
    private IExpression expression;

    public PrintStmt(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws ExpressionException {
        IValue val = expression.evaluate(state.getSymTable());
        state.getOutputList().add(val);
        return state;
    }

    public IStmt deepcopy(){
        return new PrintStmt(expression.deepcopy());
    }

    @Override
    public String toString() {
        return "print(" +this.expression.toString()+")";
    }
}

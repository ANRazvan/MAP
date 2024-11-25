package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.expressions.IExpression;
import model.state.PrgState;
import model.value.BoolValue;

public class WhileStmt implements IStmt{
    private IExpression cond;
    private IStmt stmt;

    public WhileStmt(IExpression cond, IStmt stmt) {
        this.cond = cond;
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException {
        if(cond.evaluate(state.getSymTable(), state.getHeap()).getType().toString().equals("bool")) {
            if(cond.evaluate(state.getSymTable(), state.getHeap()).equals(new BoolValue(true))) {
                state.getExecStack().push(this);
                state.getExecStack().push(stmt);
            }
        }
        else
            throw new StatementException("Condition is not a boolean");

        return state;
    }

    @Override
    public IStmt deepcopy() {
        return null;
    }

    public String toString() {
        return "while(" + cond.toString() + "){" + stmt.toString() + "}";
    }
}

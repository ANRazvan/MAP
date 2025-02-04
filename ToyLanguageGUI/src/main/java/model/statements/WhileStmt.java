package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.adt.MyIMap;
import model.expressions.IExpression;
import model.state.PrgState;
import model.type.BoolType;
import model.type.IType;
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

        return null;
    }

    @Override
    public IStmt deepcopy() {
        return new WhileStmt(cond.deepcopy(), stmt.deepcopy());
    }

    @Override
    public MyIMap<String, IType> typecheck(MyIMap<String, IType> typeEnv) throws StatementException {
        IType condType = cond.typecheck(typeEnv);
        if(condType.equals(new BoolType()))
            return typeEnv;
        else
            throw new StatementException("Condition is not a boolean");
    }

    public String toString() {
        return "while(" + cond.toString() + "){" + stmt.toString() + "}";
    }
}

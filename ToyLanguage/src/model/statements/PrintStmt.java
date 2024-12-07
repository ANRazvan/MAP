package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.adt.MyIMap;
import model.expressions.IExpression;
import model.state.PrgState;
import model.type.IType;
import model.value.IValue;

public class PrintStmt implements IStmt {
    private IExpression expression;

    public PrintStmt(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws ExpressionException {
        IValue val = expression.evaluate(state.getSymTable(), state.getHeap());
        state.getOutputList().add(val);
        return null;
    }

    public IStmt deepcopy(){
        return new PrintStmt(expression.deepcopy());
    }

    @Override
    public MyIMap<String, IType> typecheck(MyIMap<String, IType> typeEnv) throws StatementException {
        expression.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString() {
        return "print(" +this.expression.toString()+")";
    }
}

package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.expressions.IExpression;
import model.state.PrgState;
import model.type.BoolType;
import model.value.BoolValue;
import model.value.IValue;

public class IfStmt implements IStmt {
    private IExpression condition;
    private IStmt thenStatement;
    private IStmt elseStatement;
    public IfStmt(IExpression condition, IStmt thenStatement, IStmt elseStatement) {
        this.condition = condition;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }
    public PrgState execute(PrgState state) throws StatementException, ExpressionException {

        IValue val = condition.evaluate(state.getSymTable(),state.getHeap());
        if(!val.getType().equals(new BoolType())) {
            throw new StatementException("The condition is not a boolean");
        }

        if(((BoolValue)val).getValue())
        {
            state.getExecStack().push(thenStatement);
        }
        else
            state.getExecStack().push(elseStatement);
        return state;
    }

    public IStmt deepcopy(){
        return new IfStmt(condition.deepcopy(),thenStatement.deepcopy(),elseStatement.deepcopy());
    }

    public String toString() {
        return "IF("+ condition.toString()+") THEN{" +thenStatement.toString() +"}ELSE{"+elseStatement.toString()+"}";
    }
}

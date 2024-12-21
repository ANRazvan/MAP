package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.adt.MyIMap;
import model.state.PrgState;
import model.type.IType;
import model.value.IValue;
import model.expressions.IExpression;

public class AssignStmt implements IStmt {
    private String id;
    private IExpression expression;

    public AssignStmt(String id, IExpression expression) {
        this.id = id;
        this.expression = expression;
    }

    public IStmt deepcopy(){
        return new AssignStmt(new String(id),expression.deepcopy());
    }

    @Override
    public MyIMap<String, IType> typecheck(MyIMap<String, IType> typeEnv) throws StatementException {
        IType typevar = typeEnv.lookup(id);
        IType typexp = expression.typecheck(typeEnv);
        if(typevar.equals(typexp))
            return typeEnv;
        else
            throw new StatementException("Assignment: right hand side and left hand side have different types ");
    }

    public String toString(){
        return id + "=" + expression;
    }
    public PrgState execute(PrgState state) throws StatementException, ExpressionException {
        if(!state.getSymTable().contains(id))
            throw new StatementException("The variable wasnt declared previously!");
        IValue val = expression.evaluate(state.getSymTable(),state.getHeap());
        if(!val.getType().equals(state.getSymTable().lookup(id).getType()))
            throw new StatementException("The types did not match!");
        state.getSymTable().insert(id,val);
        return null;
    }

}

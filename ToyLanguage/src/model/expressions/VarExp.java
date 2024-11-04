package model.expressions;

import exceptions.ExpressionException;
import model.adt.MyIDictionary;
import model.value.IValue;

public class VarExp implements IExpression{
    private String variable;

    public VarExp(String variable)
    {
        this.variable = variable;

    }

    public IExpression deepcopy(){
        return new VarExp(new String(variable));
    }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> symTbl) throws ExpressionException {

        return symTbl.lookup(this.variable);
    }
    @Override
    public String toString()
    {
        return this.variable;
    }
}

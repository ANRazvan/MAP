package model.expressions;

import exceptions.ExpressionException;
import model.adt.MyIHeap;
import model.adt.MyIMap;
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
    public IValue evaluate(MyIMap<String, IValue> symTbl, MyIHeap heap) throws ExpressionException {

        return symTbl.getValue(this.variable);
    }
    @Override
    public String toString()
    {
        return this.variable;
    }
}

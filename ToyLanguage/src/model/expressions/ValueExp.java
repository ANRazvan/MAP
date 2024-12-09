package model.expressions;

import exceptions.ExpressionException;
import model.adt.MyIHeap;
import model.adt.MyIMap;
import model.type.IType;
import model.value.IValue;

public class ValueExp implements IExpression {
    private IValue value;
    public ValueExp(IValue value) {
        this.value = value;
    }

    public IExpression deepcopy(){
        return new ValueExp(value.deepcopy());
    }

    @Override
    public IType typecheck(MyIMap<String, IType> typeEnv) throws ExpressionException {
        return this.value.getType();
    }

    @Override
    public IValue evaluate(MyIMap<String, IValue> symTbl, MyIHeap heap) {
        return this.value;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}

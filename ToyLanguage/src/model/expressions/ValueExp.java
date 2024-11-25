package model.expressions;

import model.adt.MyIHeap;
import model.adt.MyIMap;
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
    public IValue evaluate(MyIMap<String, IValue> symTbl, MyIHeap heap) {
        return this.value;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}

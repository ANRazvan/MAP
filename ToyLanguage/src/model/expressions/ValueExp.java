package model.expressions;

import model.adt.MyIDictionary;
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
    public IValue evaluate(MyIDictionary<String, IValue> symTbl) {
        return this.value;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}

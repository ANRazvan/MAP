package model.expressions;

import model.adt.MyIDictionary;
import model.state.PrgState;
import model.value.IValue;

import java.beans.Expression;

public class ValueExpression implements IExpression {
    private IValue value;
    public ValueExpression(IValue value) {
        this.value = value;
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

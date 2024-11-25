package model.expressions;

import exceptions.ExpressionException;
import model.adt.MyIHeap;
import model.adt.MyIMap;
import model.value.IValue;

public interface IExpression {
    IValue evaluate(MyIMap<String, IValue> symTbl, MyIHeap heap) throws ExpressionException;
    IExpression deepcopy();
}

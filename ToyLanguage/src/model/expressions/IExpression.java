package model.expressions;

import exceptions.ExpressionException;
import model.adt.MyIHeap;
import model.adt.MyIMap;
import model.type.IType;
import model.value.IValue;

public interface IExpression {
    IType typecheck(MyIMap<String, IType> typeEnv) throws ExpressionException;
    IValue evaluate(MyIMap<String, IValue> symTbl, MyIHeap heap) throws ExpressionException;
    IExpression deepcopy();
}

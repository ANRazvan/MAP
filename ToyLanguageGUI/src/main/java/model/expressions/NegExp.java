package model.expressions;

import exceptions.ExpressionException;
import model.adt.MyIHeap;
import model.adt.MyIMap;
import model.type.IType;
import model.value.BoolValue;
import model.value.IValue;
import model.type.BoolType;

public class NegExp implements IExpression{
    private IExpression exp;

    public NegExp(IExpression exp) {
        this.exp = exp;
    }

    @Override
    public IType typecheck(MyIMap<String, IType> typeEnv) throws ExpressionException {
        IType type = exp.typecheck(typeEnv);
        if(type.equals(new BoolType())){
            return new BoolType();
        }
        throw new ExpressionException("Negation can only be applied to a boolean value");
    }

    @Override
    public IValue evaluate(MyIMap<String, IValue> symTbl, MyIHeap heap) throws ExpressionException {
        // for a boolean value we have to return the negation of the value
        IValue value = exp.evaluate(symTbl, heap);
        if(value.getType().equals(new BoolType())){
            return new BoolValue(!((BoolValue)value).getValue());
        }
        return null;
    }

    @Override
    public IExpression deepcopy() {
        return new NegExp(exp.deepcopy());
    }

    @Override
    public String toString() {
        return "!" + exp.toString();
    }
}

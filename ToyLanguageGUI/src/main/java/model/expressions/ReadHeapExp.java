package model.expressions;

import exceptions.ExpressionException;
import model.adt.MyIHeap;
import model.adt.MyIMap;
import model.type.IType;
import model.type.RefType;
import model.value.IValue;
import model.value.RefValue;

public class ReadHeapExp implements IExpression{
    IExpression expression;

    public ReadHeapExp(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public IType typecheck(MyIMap<String, IType> typeEnv) throws ExpressionException {
        IType type = expression.typecheck(typeEnv);
        if(type instanceof RefType){
            RefType refType = (RefType) type;
            return refType.getInner();
        }
        else{
            throw new ExpressionException("Expression is not a reference");
        }
    }

    @Override
    public IValue evaluate(MyIMap<String, IValue> symTbl, MyIHeap heap) throws ExpressionException {
        IValue res = expression.evaluate(symTbl, heap);
        if(res instanceof RefValue refValue){
            int address = refValue.getAddr();
            if(heap.containsAddr(address)){
                return heap.getValue(address);
            }
            else{
                throw new ExpressionException("Address not defined in heap");
            }
        }
        else{
            throw new ExpressionException("Expression is not a reference");
        }
    }

    @Override
    public IExpression deepcopy() {
        return null;
    }

    public String toString(){
        return "ReadHeap(" + expression.toString() + ")";
    }
}

package model.expressions;

import exceptions.ExpressionException;
import model.adt.MyIHeap;
import model.adt.MyIMap;
import model.type.BoolType;
import model.type.IType;
import model.type.IntType;
import model.value.BoolValue;
import model.value.IValue;
import model.value.IntValue;

public class RelationalExp implements IExpression{
    private IExpression left;
    private IExpression right;
    private String op;

    public RelationalExp(IExpression e1, String op, IExpression e2) {
        this.left = e1;
        this.right = e2;
        this.op = op;
    }

    @Override
    public IType typecheck(MyIMap<String, IType> typeEnv) throws ExpressionException {
        IType type1, type2;
        type1 = left.typecheck(typeEnv);
        type2 = right.typecheck(typeEnv);

        if(type1.equals(new IntType())) {
            if(type2.equals(new IntType())) {
                return new BoolType();
            }
            else {
                throw new ExpressionException("Second operand is not an integer!");
            }
        }
        else {
            throw new ExpressionException("First operand is not an integer!");
        }
    }

    @Override
    public IValue evaluate(MyIMap<String, IValue> symTable, MyIHeap heap) throws ExpressionException {
        IValue left = this.left.evaluate(symTable,heap);
        IValue right = this.right.evaluate(symTable,heap);

        if(!left.getType().equals(new IntType()))
            throw new ExpressionException("The left operand is not an integer!");
        if(!right.getType().equals(new IntType()))
            throw new ExpressionException("The right operand is not an integer!");

        int leftValue = ((IntValue)left).getValue();
        int rightValue = ((IntValue)right).getValue();

        switch(op) {
            case "=" -> {
                return new BoolValue(leftValue == rightValue);
            }
            case "!=" -> {
                return new BoolValue(leftValue != rightValue);
            }
            case "<" -> {
                return new BoolValue(leftValue < rightValue);
            }
            case "<=" -> {
                return new BoolValue(leftValue <= rightValue);
            }
            case ">" -> {
                return new BoolValue(leftValue > rightValue);
            }
            case ">=" -> {
                return new BoolValue(leftValue >= rightValue);
            }
            default-> {
                throw new ExpressionException("Invalid operator!");
            }
        }
    }

    public IExpression deepcopy() {
        return new RelationalExp(left.deepcopy(), op, right.deepcopy());
    }

    public String toString() {
        return left.toString() + op + right.toString();
    }

}

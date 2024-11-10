package model.expressions;

import exceptions.ExpressionException;
import model.adt.MyIDictionary;
import model.type.IntType;
import model.value.BoolValue;
import model.value.IValue;
import model.value.IntValue;

public class RelationalExp implements IExpression{
    private IExpression left;
    private IExpression right;
    private RelationalOp op;

    public RelationalExp(IExpression e1, RelationalOp op, IExpression e2) {
        this.left = e1;
        this.right = e2;
        this.op = op;
    }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> symTable) throws ExpressionException {
        IValue left = this.left.evaluate(symTable);
        IValue right = this.right.evaluate(symTable);

        if(!left.getType().equals(new IntType()))
            throw new ExpressionException("The left operand is not an integer!");
        if(!right.getType().equals(new IntType()))
            throw new ExpressionException("The right operand is not an integer!");

        int leftValue = ((IntValue)left).getValue();
        int rightValue = ((IntValue)right).getValue();

        switch(op) {
            case EQUAL -> {
                return new BoolValue(leftValue == rightValue);
            }
            case NOT_EQUAL -> {
                return new BoolValue(leftValue != rightValue);
            }
            case LESS -> {
                return new BoolValue(leftValue < rightValue);
            }
            case LESS_OR_EQUAL -> {
                return new BoolValue(leftValue <= rightValue);
            }
            case GREATER -> {
                return new BoolValue(leftValue > rightValue);
            }
            case GREATER_OR_EQUAL -> {
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

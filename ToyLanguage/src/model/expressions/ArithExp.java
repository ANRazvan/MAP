package model.expressions;

import exceptions.ExpressionException;
import model.adt.MyIHeap;
import model.adt.MyIMap;
import model.type.IntType;
import model.value.IValue;
import model.value.IntValue;

public class ArithExp implements IExpression {

    private IExpression left;
    private IExpression right;
    private ArithOp operation;

    public ArithExp(char operation, IExpression left, IExpression right){
        this.left = left;
        this.right = right;
        switch(operation){
            case '+' -> {
                this.operation = ArithOp.PLUS;
            }

            case '-' -> {
                this.operation = ArithOp.MINUS;
            }

            case '*' -> {
                this.operation = ArithOp.MULTIPLY;
            }

            case '/' -> {
                this.operation = ArithOp.DIVIDE;
            }

            default -> {
                this.operation = null;
            }
        }
    }

    public ArithExp(ArithOp operation,IExpression left, IExpression right){
        this.left = left;
        this.right = right;
        this.operation = operation;
    }

    public IExpression deepcopy(){
        return new ArithExp(operation,left.deepcopy(),right.deepcopy());
    }

    @Override
    public IValue evaluate(MyIMap<String, IValue> symTbl, MyIHeap heap) throws ExpressionException {

        IValue leftValue = left.evaluate(symTbl,heap);
        IValue rightValue = right.evaluate(symTbl,heap);

        if(!leftValue.getType().equals(new IntType())
                || !rightValue.getType().equals(new IntType())){
            throw new ExpressionException("Invalid expressions");
        }

        int intLeftValue = ((IntValue) leftValue).getValue();
        int intRightValue = ((IntValue) rightValue).getValue();

        switch(operation){
            case PLUS -> {
                return new IntValue(intLeftValue+intRightValue);
            }

            case MINUS -> {
                return new IntValue(intLeftValue-intRightValue);
            }

            case MULTIPLY -> {
                return new IntValue(intLeftValue*intRightValue);
            }

            case DIVIDE -> {

                if(intRightValue == 0){
                    throw new ExpressionException("Division by zero");
                }

                return new IntValue(intLeftValue/intRightValue);
            }

            default -> {
                throw new ExpressionException("Invalid operation");
            }
        }


    }

    public String enumToString(){
        switch(this.operation){
            case PLUS -> {
                return "+";
            }

            case MINUS -> {
                return "-";
            }

            case MULTIPLY -> {
                return "*";
            }

            case DIVIDE -> {
                return "/";
            }

            default -> {
                return "";
            }
        }
    }

    @Override
    public String toString() {
        return this.left.toString() + enumToString() + this.right.toString();
    }
}

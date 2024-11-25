package model.expressions;
import exceptions.ExpressionException;
import model.adt.MyIHeap;
import model.adt.MyIMap;
import model.value.IValue;
import model.type.BoolType;
import model.value.BoolValue;

public class LogicalExp implements IExpression{
    private IExpression left;
    private IExpression right;
    private LogicalOp operation;
    public LogicalExp(IExpression left, LogicalOp operation, IExpression right) {
        this.left = left;
        this.operation = operation;
        this.right = right;
    }
    @Override
    public String toString(){
        return left.toString() + " " + operation.toString().toLowerCase() + " " + right.toString();
    }

    public IExpression deepcopy(){
        return new LogicalExp(left.deepcopy(),operation,right.deepcopy());
    }

    public IValue evaluate(MyIMap<String, IValue> symTable, MyIHeap heap) throws ExpressionException {
        IValue left = this.left.evaluate(symTable,heap);
        IValue right = this.right.evaluate(symTable,heap);
        if(!(left.getType().equals(new BoolType()) && right.getType().equals(new BoolType()))){
            throw new ExpressionException("The values are not boolean!");
        }

        Boolean leftValue = ((BoolValue)(left)).getValue();
        Boolean rightValue = ((BoolValue)(right)).getValue();
        if(operation == LogicalOp.AND){
            return new BoolValue(leftValue && rightValue);
        }
        else {
            return new BoolValue(leftValue || rightValue);
        }

    }

}

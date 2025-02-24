package model.value;

import model.type.IType;
import model.type.IntType;

import java.lang.reflect.Type;

public class IntValue implements IValue{
    private int number;
    public IntValue(int number) {
        this.number = number;
    }
    public int getValue() {
        return number;
    }
    public IType getType(){
        return new IntType();
    }

    @Override
    public boolean equals(IValue val) {
        return val.getType() instanceof IntType &&((IntValue)val).getValue()==this.getValue();
    }

    public IValue deepcopy(){
        return new IntValue(number);
    }

    public String toString(){
        return Integer.toString(number);
    }

}

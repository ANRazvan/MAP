package model.value;

import model.type.StringType;
import model.type.IType;

public class StringValue implements IValue{
    private String value;
    public StringValue(String val){
        this.value = val;
    }
    public String getValue(){return value;}

    public IType getType(){return new StringType();}

    @Override
    public boolean equals(IValue obj){
        return obj.getType() instanceof StringType && ((StringValue)obj).getValue()==this.getValue();
    }
    public IValue deepcopy(){
        return new StringValue(value);
    }

    public String toString(){return value;}
}

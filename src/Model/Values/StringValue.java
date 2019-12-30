package Model.Values;

import Model.Types.IType;
import Model.Types.StringType;

public class StringValue implements IValue{
    private String value;

    public StringValue(String newValue){ this.value = newValue; }

    @Override
    public Object getValue(){ return value; }

    @Override
    public String toString(){ return value; }

    @Override
    public IType getType(){ return new StringType(); }

    @Override
    public boolean equals(IValue another){ return another.getValue().equals(value); }
}

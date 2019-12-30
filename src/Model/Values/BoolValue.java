package Model.Values;

import Model.Types.BoolType;
import Model.Types.IType;

public class BoolValue implements IValue {
    private boolean value;

    public BoolValue(boolean newValue){ this.value = newValue; }

    @Override
    public Object getValue(){ return value; }

    @Override
    public String toString(){ return "" + value; }

    @Override
    public IType getType(){ return new BoolType(); }

    @Override
    public boolean equals(IValue another){ return another.getValue().equals(value); }
}

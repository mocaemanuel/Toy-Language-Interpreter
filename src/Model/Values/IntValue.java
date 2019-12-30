package Model.Values;

import Model.Types.IntType;
import Model.Types.IType;

public class IntValue implements IValue {
    private int value;
    public IntValue(int v){ value = v; }

    @Override
    public Object getValue(){ return value; }

    @Override
    public String toString(){ return "" + value; }

    @Override
    public IType getType(){ return new IntType(); }

    @Override
    public boolean equals(IValue another){ return another.getValue().equals(value); }
}

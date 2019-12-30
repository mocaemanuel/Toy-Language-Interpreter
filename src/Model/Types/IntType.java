package Model.Types;

import Model.Values.IntValue;

public class IntType implements IType {
    public boolean equals(Object another){
        return another instanceof IntType;
    }

    @Override
    public String toString(){ return "int"; }

    @Override
    public IntValue defaultValue() { return new IntValue(1); }
}

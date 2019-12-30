package Model.Types;

import Model.Values.IValue;
import Model.Values.ReferenceValue;

public class ReferenceType implements IType {
    private IType inner;

    public ReferenceType (IType inner){
        this.inner = inner;
    }

    public ReferenceType (){ }

    public IType getInner(){
        return this.inner;
    }

    @Override
    public boolean equals(Object another){
        if (another instanceof  ReferenceType)
            return inner.equals(((ReferenceType) another).getInner());
        else
            return false;
    }

    @Override
    public String toString(){
        return "Ref( " + inner.toString() + " )";
    }

    @Override
    public IValue defaultValue(){
        return new ReferenceValue(0, inner);
    }
}

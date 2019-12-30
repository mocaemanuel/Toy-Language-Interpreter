package Model.Values;

import Model.Types.IType;
import Model.Types.ReferenceType;

public class ReferenceValue implements IValue {
    private int address;
    private IType locationType;

    public ReferenceValue(int address, IType locationType){
        this.address = address;
        this.locationType = locationType;
    }

    public ReferenceValue(){}

    public int getAddress(){
        return address;
    }

    public IType getType(){
        return new ReferenceType(locationType);
    }

    public void setAddress(int a) { address = a;}

    public Object getInnerType(){
        return this.locationType;
    }

    @Override
    public Object getValue(){
        return this.locationType;
    }

    @Override
    public boolean equals(Object another){
        return another instanceof ReferenceValue;
    }

    @Override
    public String toString(){
        return "("  + Integer.toString(address)  + " " + locationType.toString() + " )";
    }

    @Override
    public boolean equals(IValue boolValue){
        return true;
    }

}

package Model.Values;

import Model.Types.IType;

public interface IValue {
    IType getType();
    String toString();
    Object getValue();
    boolean equals(IValue boolValue);
}

package Model.Expression;

import Model.Collection.IHeap;
import Model.Collection.MyException;
import Model.Collection.MyIDictionary;
import Model.Types.IType;
import Model.Values.IValue;

public interface IExpression {
    IValue evaluate(MyIDictionary<String, IValue> table, IHeap<Integer, IValue> heap) throws MyException;
    IType typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException;
}

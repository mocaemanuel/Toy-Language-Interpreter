package Model.Expression;

import Model.Collection.IHeap;
import Model.Collection.MyException;
import Model.Collection.MyIDictionary;
import Model.Types.IType;
import Model.Values.IValue;

public class ValueExpression implements IExpression {
    private IValue e;

    public ValueExpression(IValue value) { e = value; }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> table, IHeap<Integer, IValue> heap) throws MyException {
        return e;
    }

    @Override
    public String toString(){
        return e.toString();
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException{
        return e.getType();
    }
}

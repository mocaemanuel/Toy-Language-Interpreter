package Model.Expression;

import Model.Collection.IHeap;
import Model.Collection.MyException;
import Model.Collection.MyIDictionary;
import Model.Types.IType;
import Model.Values.IValue;

public class VarExpression implements IExpression {
    private String id;

    public VarExpression(String newID){ id = newID; }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> table, IHeap<Integer, IValue> heap) throws MyException {
        return table.lookup(id);
    }

    @Override
    public String toString(){
        return id;
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException{
        return typeEnv.lookup(id);
    }
}

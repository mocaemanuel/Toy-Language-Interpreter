package Model.Expression;

import Model.Collection.IHeap;
import Model.Collection.MyException;
import Model.Collection.MyIDictionary;
import Model.Types.IType;
import Model.Types.ReferenceType;
import Model.Values.IValue;
import Model.Values.ReferenceValue;

public class HeapReadingExpression implements IExpression {
    private IExpression expression;

    public HeapReadingExpression(IExpression expression){
        this.expression = expression;
    }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> table, IHeap<Integer, IValue> heap) throws MyException{
        IValue returnValue = expression.evaluate(table, heap);

        if ( !(returnValue instanceof ReferenceValue))
            throw new MyException("Invalid evaluate type!");

        ReferenceValue val = (ReferenceValue) returnValue;

        if (! heap.isDefined(val.getAddress()))
            throw new MyException("Invalid address!");

        return  heap.getValue(val.getAddress());
    }

    @Override
    public String toString(){
        return "rH( " + expression.toString() + " )";
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException{
        IType typ = expression.typeCheck(typeEnv);
        if (typ instanceof ReferenceType) {
            ReferenceType reft =(ReferenceType) typ;
            return reft.getInner();
        } else
            throw new MyException("the rH argument is not a Ref Type");
    }


}

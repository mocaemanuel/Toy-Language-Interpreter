package Model.Expression;

import Model.Collection.IHeap;
import Model.Collection.MyException;
import Model.Collection.MyIDictionary;
import Model.Types.BoolType;
import Model.Types.IType;
import Model.Types.IntType;
import Model.Values.BoolValue;
import Model.Values.IValue;

public class LogicExpression implements IExpression {
    private IExpression expression1;
    private IExpression expression2;
    private int operand;

    public LogicExpression( IExpression e1, IExpression e2, int op){
        this.expression1 = e1;
        this.expression2 = e2;
        this.operand = op;
    }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> table, IHeap<Integer, IValue> heap) throws MyException {
        IValue value1, value2;
        value1 = expression1.evaluate(table, heap);
        if (value1.getType().equals(new BoolType())){
            value2 = expression2.evaluate(table, heap);
            if (value2.getType().equals(new BoolType())){
                boolean nValue1, nValue2;
                nValue1 = (boolean) value1.getValue();
                nValue2 = (boolean) value2.getValue();
                if (operand == 1) return new BoolValue(nValue1 && nValue2);
                if (operand == 2) return new BoolValue(nValue1 || nValue2);
                if (operand > 2)
                    throw new MyException("Invalid operation!");
            }
            else
                throw new MyException("Second operand is not an integer!");
        }
        else
            throw new MyException("First operand is not an integer!");
        return null;
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typ1, typ2;
        typ1=expression1.typeCheck(typeEnv);
        typ2=expression1.typeCheck(typeEnv);
        if (typ1.equals(new BoolType())) {
            if (typ2.equals(new BoolType())) {
                return new IntType();
            } else
                throw new MyException("second operand is not an integer");
        }else
            throw new MyException("first operand is not an integer");
    }
}

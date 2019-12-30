package Model.Expression;

import Model.Collection.IHeap;
import Model.Collection.MyException;
import Model.Collection.MyIDictionary;
import Model.Types.IType;
import Model.Types.IntType;
import Model.Values.IntValue;
import Model.Values.IValue;

public class ArithmeticExpression implements IExpression {
    private IExpression expression1;
    private IExpression expression2;
    private String operand;

    public ArithmeticExpression(IExpression expression1, IExpression expression2, String newOp){
        this.expression1 = expression1;
        this.expression2 = expression2;
        operand = newOp;
    }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> table, IHeap<Integer, IValue> heap) throws MyException {
        IValue value1, value2;
        value1 = expression1.evaluate(table, heap);
        if (value1.getType().equals(new IntType())) {
            value2 = expression2.evaluate(table, heap);
            if (value2.getType().equals(new IntType())) {
                int nValue1, nValue2;
                nValue1 = (int) value1.getValue();
                nValue2 = (int) value2.getValue();
                if (operand.equals("+"))
                    return new IntValue(nValue1 + nValue2);
                if (operand.equals("-"))
                    return new IntValue(nValue1 - nValue2);
                if (operand.equals("*"))
                    return new IntValue(nValue1 * nValue2);
                if (operand.equals("/"))
                    if (nValue2 == 0)
                        throw new MyException("division by 0");
                    else
                        return new IntValue(nValue1 / nValue2);
            } else
                throw new MyException("second operand is not an integer");
        }
        else
            throw new MyException("first operand is not an integer");
        return null;
    }

    @Override
    public String toString(){
        String stringToPrint = "" + expression1.toString();
        switch (operand) {
            case "+":
                stringToPrint += " + ";
                break;
            case "-":
                stringToPrint += " - ";
                break;
            case "*":
                stringToPrint += " * ";
                break;
            case "/":
                stringToPrint += " / ";
                break;
        }
        stringToPrint += expression2.toString() + "";

        return stringToPrint;
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException{
        IType typ1, typ2;
        typ1=expression1.typeCheck(typeEnv);
        typ2=expression1.typeCheck(typeEnv);
        if (typ1.equals(new IntType())) {
            if (typ2.equals(new IntType())) {
                return new IntType();
            } else
            throw new MyException("second operand is not an integer");
        }else
        throw new MyException("first operand is not an integer");
    }
}

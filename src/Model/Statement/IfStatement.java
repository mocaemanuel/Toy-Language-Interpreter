package Model.Statement;

import Model.*;
import Model.Collection.MyException;
import Model.Collection.MyIDictionary;
import Model.Collection.MyIStack;
import Model.Expression.IExpression;
import Model.Types.BoolType;
import Model.Types.IType;
import Model.Types.IntType;
import Model.Values.BoolValue;
import Model.Values.IValue;

public class IfStatement implements IStatement {
    private IExpression expression;
    private IStatement thenStatement;
    private IStatement elseStatement;

    public IfStatement(IExpression expression, IStatement thenStatement, IStatement elseStatement){
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public String toString(){
        return "(IF " + expression.toString() + " THEN " + thenStatement.toString() + " ELSE " +
                elseStatement.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStatement> stack = state.getExeStack();
        IValue value;
        try{
            value = expression.evaluate(state.getSymTable(), state.getHeapTable());
        } catch(MyException error){
            throw new MyException(error.getMessage());
        }
        if (value.equals(new BoolValue(true)))
            stack.push(thenStatement);
        else
            stack.push(elseStatement);
        return null;
    }

    @Override
    public IStatement deepCopy() { return new IfStatement(expression, thenStatement, elseStatement); }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String,IType> typeEnv) throws MyException{
        IType typexp = expression.typeCheck(typeEnv);
        if (typexp.equals(new IntType()) || typexp.equals(new BoolType())) {
            thenStatement.typeCheck(typeEnv.deepCopy());
            elseStatement.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new MyException("The condition of IF has not the type bool");
    }

}

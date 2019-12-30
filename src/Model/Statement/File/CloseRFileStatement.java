package Model.Statement.File;

import Model.Collection.MyException;
import Model.Collection.MyIDictionary;
import Model.Expression.IExpression;
import Model.ProgramState;
import Model.Statement.IStatement;
import Model.Types.IType;
import Model.Types.StringType;
import Model.Values.IValue;
import Model.Values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFileStatement implements IStatement {
    private IExpression expression;

    public CloseRFileStatement(IExpression expression){
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException{
        //state.getExeStack().pop();
        IValue returnValue = expression.evaluate(state.getSymTable(), state.getHeapTable());
        if (!returnValue.getType().equals(new StringType()))
            throw new MyException("Invalid type!");
        if (!state.getFileTable().isDefined((StringValue)returnValue))
            throw  new MyException("File not defined!");
        try {
            BufferedReader openedFile = state.getFileTable().getValue((StringValue)returnValue);
            openedFile.close();
        }
        catch (IOException error){
            throw new MyException(error.getMessage());
        }
        state.getFileTable().remove((StringValue)returnValue);
        return null;
    }

    @Override
    public String toString(){
        return "Close: " + expression.toString();
    }

    @Override
    public CloseRFileStatement deepCopy(){
        return new CloseRFileStatement(expression);
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException{
        expression.typeCheck(typeEnv);
        return typeEnv;
    }
}

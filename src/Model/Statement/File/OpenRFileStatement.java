package Model.Statement.File;

import Model.Collection.MyException;
import Model.Collection.MyIDictionary;
import Model.Expression.IExpression;
import Model.ProgramState;
import Model.Statement.IStatement;
import Model.Types.IType;
import Model.Types.ReferenceType;
import Model.Types.StringType;
import Model.Values.IValue;
import Model.Values.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFileStatement implements IStatement {
    private IExpression expression;

    public OpenRFileStatement(IExpression expression){ this.expression = expression; }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        //state.getExeStack().pop();
        IValue returnValue = expression.evaluate(state.getSymTable(), state.getHeapTable());
        if (!returnValue.getType().equals(new StringType()))
            throw new MyException("Invalid type!");
        if (state.getFileTable().isDefined((StringValue)returnValue))
            throw  new MyException("Already opened!");
        try {
            BufferedReader openedFile = new BufferedReader(new FileReader((String)returnValue.getValue()));
            state.getFileTable().update((StringValue)returnValue, openedFile);
        }
        catch (IOException error){
            throw new MyException(error.getMessage());
        }
        return null;
    }

    @Override
    public String toString(){
        return "Open: "  + expression.toString();
    }

    @Override
    public IStatement deepCopy(){ return new OpenRFileStatement(expression); }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException{
        expression.typeCheck(typeEnv);
        return typeEnv;
    }
}

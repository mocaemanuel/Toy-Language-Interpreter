package Model.Statement.File;

import Model.Collection.MyException;
import Model.Collection.MyIDictionary;
import Model.Expression.IExpression;
import Model.ProgramState;
import Model.Statement.IStatement;
import Model.Types.IType;
import Model.Types.IntType;
import Model.Types.ReferenceType;
import Model.Types.StringType;
import Model.Values.IValue;
import Model.Values.IntValue;
import Model.Values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements IStatement {
    private IExpression expression;
    private StringValue varName;

    public ReadFileStatement(IExpression expression, StringValue varName){
        this.expression = expression;
        this.varName = varName;
    }

    @Override public ProgramState execute(ProgramState state) throws MyException{
        //state.getExeStack().pop();
        if (!state.getSymTable().isDefined((String) varName.getValue()))
            throw new MyException("VarName is undefined!");
        if (!state.getSymTable().getValue((String) varName.getValue()).getType().equals(new IntType()))
            throw new MyException("VarName is not an int type!");
        IValue returnValue  = expression.evaluate(state.getSymTable(), state.getHeapTable());
        if (!returnValue.getType().equals(new StringType()))
            throw new MyException("Invalid type!");
        //boolean help = state.getFileTable().isDefined((StringValue)returnValue);
        if (!state.getFileTable().isDefined((StringValue)returnValue))
            throw  new MyException("File not defined!");
        try {
            BufferedReader openedFile = state.getFileTable().getValue((StringValue)returnValue);
            String line = openedFile.readLine();
            IntValue newValue;
            if (line == null) {
                newValue = new IntValue(0);
            }
            else {
                newValue = new IntValue(Integer.parseInt(line));
            }
            state.getSymTable().update((String)varName.getValue(), newValue);
        }
        catch (IOException error){
            throw new MyException(error.getMessage());
        }
        return null;
    }

    @Override
    public String toString(){
        return "Read: " + varName + " from: " + expression.toString();
    }

    @Override public IStatement deepCopy(){
        return new ReadFileStatement(expression, varName);
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String,IType> typeEnv) throws MyException{
        IType typexp = expression.typeCheck(typeEnv);
        if (typexp.equals(new StringType()))
            return typeEnv;
        else
            throw new MyException("File stmt: right hand side and left hand side have different types ");
    }
}

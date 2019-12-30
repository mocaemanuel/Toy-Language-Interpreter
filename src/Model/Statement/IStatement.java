package Model.Statement;

import Model.Collection.MyException;
import Model.Collection.MyIDictionary;
import Model.ProgramState;
import Model.Types.IType;

public interface IStatement {
    ProgramState execute(ProgramState state) throws MyException;
    IStatement deepCopy();
    MyIDictionary<String, IType> typeCheck(MyIDictionary<String,IType> typeEnv) throws MyException;
}

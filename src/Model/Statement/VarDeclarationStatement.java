package Model.Statement;

import Model.Collection.MyException;
import Model.Collection.MyIDictionary;
import Model.ProgramState;
import Model.Types.IType;
import Model.Values.IValue;

public class VarDeclarationStatement implements IStatement {
    private String name;
    private IType type;

    public VarDeclarationStatement( String name, IType type){
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() { return type.toString() + " " +  name + " "; }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        if (symTable.isDefined(name))
            throw new MyException("variable is already declared!");
        symTable.update(name, type.defaultValue());
        return null;
    }

    @Override
    public IStatement deepCopy() { return new VarDeclarationStatement(name, type);}

    @Override
    public MyIDictionary<String,IType> typeCheck(MyIDictionary<String,IType> typeEnv) throws MyException{
        typeEnv.update(name,type);
        return typeEnv;
    }
}

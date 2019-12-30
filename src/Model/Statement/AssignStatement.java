package Model.Statement;

import Model.*;
import Model.Collection.MyException;
import Model.Collection.MyIDictionary;
import Model.Expression.IExpression;
import Model.Types.IType;
import Model.Values.IValue;

public class AssignStatement implements IStatement {
    private String id;
    private IExpression expression;

    public AssignStatement(String newID, IExpression expression){
        this.id = newID;
        this.expression = expression;
    }

    @Override
    public String toString(){
        return id + " = " + expression.toString();
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {

        MyIDictionary<String, IValue> symTable = state.getSymTable();
        IValue value = expression.evaluate(symTable, state.getHeapTable());
        if (symTable.isDefined(id)) {
            IType typeID = (symTable.getValue(id)).getType();
            if (value.getType().equals(typeID))
                symTable.update(id, value);
            else
                throw new MyException("declared type of variable " + id + " and type of the assigned expression do  not match");
        }
        else
            throw new MyException("the used variable " + id + " was not declared before");
        return null;
    }

    @Override
    public IStatement deepCopy() { return new AssignStatement(id, expression); }

    @Override
    public MyIDictionary<String,IType> typeCheck(MyIDictionary<String,IType> typeEnv) throws MyException{
        IType typevar = typeEnv.lookup(id);
        IType typexp = expression.typeCheck(typeEnv);
        if (typevar.equals(typexp))
            return typeEnv;
        else
            throw new MyException("Assignment: right hand side and left hand side have different types ");
    }

}


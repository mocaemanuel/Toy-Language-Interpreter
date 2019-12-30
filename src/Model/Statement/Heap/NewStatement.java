package Model.Statement.Heap;

import Model.Collection.MyException;
import Model.Collection.MyIDictionary;
import Model.Expression.IExpression;
import Model.ProgramState;
import Model.Statement.IStatement;
import Model.Types.IType;
import Model.Types.ReferenceType;
import Model.Values.IValue;
import Model.Values.ReferenceValue;
import Model.Values.StringValue;


public class NewStatement implements IStatement {
    private StringValue varName;
    private IExpression expression;

    public NewStatement(StringValue varName, IExpression expression){
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public String toString(){
        return "new( " + varName + ", " + expression.toString() + " )";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException{
        if (!state.getSymTable().isDefined((String) varName.getValue()))
            throw new MyException("VarName is undefined!");
        if (!(state.getSymTable().getValue(varName.getValue().toString()).getType() instanceof ReferenceType))
            throw new MyException( varName.toString() + " is not an ref type!");

        IValue returnValue = expression.evaluate(state.getSymTable(), state.getHeapTable());
        ReferenceValue valueSymTable = (ReferenceValue) state.getSymTable().getValue(varName.toString());

        if ( returnValue.getType().equals(valueSymTable.getInnerType())) {
            ReferenceValue newValue = new ReferenceValue((Integer) state.getHeapTable().getCurrentFree(), returnValue.getType());
            newValue.setAddress((Integer) state.getHeapTable().getCurrentFree());
            state.getSymTable().update(varName.toString(), newValue);
            state.getHeapTable().update(returnValue);
        }

        return null;
    }

    @Override
    public IStatement deepCopy(){
        return new NewStatement(varName, expression);
    }

    @Override
    public MyIDictionary<String,IType> typeCheck(MyIDictionary<String,IType> typeEnv) throws MyException{
        IType typevar = typeEnv.lookup(varName.toString());
        System.out.println(typevar);
        IType typexp = expression.typeCheck(typeEnv.deepCopy());
        if (typevar.equals(new ReferenceType(typexp)))
            return typeEnv;
        else
            throw new MyException("NEW stmt: right hand side and left hand side have different types ");
    }

}

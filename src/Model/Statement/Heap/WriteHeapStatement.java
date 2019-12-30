package Model.Statement.Heap;

import Model.Collection.MyException;
import Model.Collection.MyIDictionary;
import Model.Expression.IExpression;
import Model.ProgramState;
import Model.Statement.IStatement;
import Model.Types.IType;
import Model.Types.ReferenceType;
import Model.Values.IValue;
import Model.Values.IntValue;
import Model.Values.ReferenceValue;
import Model.Values.StringValue;

public class WriteHeapStatement implements IStatement {
    private StringValue varName;
    private IExpression expression;

    public WriteHeapStatement(StringValue varName, IExpression expression){
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        if (!state.getSymTable().isDefined(varName.toString()))
            throw new MyException("Undefined variable!");
        IValue oldVal = state.getSymTable().getValue(varName.toString());
        if (!(oldVal.getType() instanceof ReferenceType))
            throw new MyException("Invalid type!");
        if (!state.getHeapTable().isDefined(((ReferenceValue) oldVal).getAddress()))
            throw new MyException("Failed to write to heap. Address not defined!");
        IValue newVal =expression.evaluate(state.getSymTable(),state.getHeapTable());
        if(!newVal.getType().equals(((ReferenceValue)oldVal).getInnerType()))
            throw new MyException("Failed");

        state.getHeapTable().update(((ReferenceValue)oldVal).getAddress(),newVal);
        return null;
    }

    @Override
    public String toString(){
        return "wH( " + varName.toString() + ", " + expression.toString() + " )";
    }

    @Override
    public IStatement deepCopy(){
        return new WriteHeapStatement(varName, expression);
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String,IType> typeEnv) throws MyException{
        IType typevar = typeEnv.lookup(varName.toString());
        IType typexp = expression.typeCheck(typeEnv);
        if (typevar.equals(new ReferenceType(typexp)))
            return typeEnv;
        else
            throw new MyException("NEW stmt: right hand side and left hand side have different types ");
    }
}

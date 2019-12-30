package Repository;

import Model.ProgramState;
import Model.Collection.MyException;

import java.io.IOException;
import java.util.ArrayList;

public interface IRepository {
    ProgramState getCurrentProgram();
    void logProgramStateExecution(ProgramState program) throws MyException;
    ArrayList<ProgramState> getProgramStateList();
    void setProgramStateList(ArrayList<ProgramState> prgStateList);
    void addProgramState(ProgramState prg);
    ProgramState getProgramStateWithId(int currentId);
}

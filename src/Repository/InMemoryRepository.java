package Repository;

import Model.Collection.MyException;
import Model.ProgramState;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class InMemoryRepository implements IRepository {
    private ArrayList<ProgramState> prgStateList;
    private String logFilePath;

    public InMemoryRepository(){}

    //public InMemoryRepository(ProgramState oldRepository){ this.localProgram = oldRepository; }

    public InMemoryRepository(ArrayList<ProgramState> oldRepository, String logFilePath){
        this.prgStateList = oldRepository;
        this.logFilePath = logFilePath;
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, false)));
            logFile.write("");
            logFile.close();
        }
        catch (IOException ignored){ }
    }

    public InMemoryRepository(String logFilePath){ this.logFilePath = logFilePath; }

    public InMemoryRepository(ProgramState oldRepository, String logFilePath){
        this.prgStateList = new ArrayList<>();
        prgStateList.add(oldRepository);
        this.logFilePath = logFilePath;
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, false)));
            logFile.write("");
            logFile.close();
        }
        catch (IOException ignored){ }
    }

    @Override
    public ProgramState getCurrentProgram() { return prgStateList.get(prgStateList.size() - 1); }

    @Override
    public void logProgramStateExecution(ProgramState program) throws MyException {
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            logFile.write(program.toString());
            logFile.close();
        }
        catch (IOException error){
            throw new MyException(error.getMessage());
        }
    }

    @Override
    public ArrayList<ProgramState> getProgramStateList(){
        return prgStateList;
    }

    @Override
    public void setProgramStateList(ArrayList<ProgramState> prgStateList){
        this.prgStateList = prgStateList;
    }

    @Override
    public void addProgramState(ProgramState prg){
        prgStateList.add(prg);
    }

    @Override
    public ProgramState getProgramStateWithId(int currentId) {
        for(ProgramState p : prgStateList) {
            if(p.getId()==currentId)
                return p;
        }
        return null;
    }
}

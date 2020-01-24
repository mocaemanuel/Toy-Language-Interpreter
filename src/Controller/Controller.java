package Controller;

import Model.Collection.MyException;
import Model.ProgramState;
import Model.Statement.IStatement;
import Repository.IRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private IRepository localRepository;
    private GarbageCollector garbageCollector;
    private ExecutorService executor;

    public Controller(IRepository repository){
        localRepository = repository;
        this.garbageCollector = new GarbageCollector();
    }

    public ArrayList removeCompletedProgram(List<ProgramState> inProgress){
        return (ArrayList) inProgress.stream().filter(ProgramState::isNotCompleted).collect(Collectors.toList());
    }


    public void oneStepForAllPrograms(ArrayList<ProgramState> programs) throws MyException{
        //print in log
        programs.forEach(program->{
            try{
                localRepository.logProgramStateExecution(program);
            }
            catch (MyException error){
                System.out.println(error.toString());
            }
        });


        //list of callable
        List<Callable<ProgramState>> callList = programs.stream().map((ProgramState p) -> (Callable<ProgramState>) p::oneStepExecution).collect(Collectors.toList());

        //start execution
        List<ProgramState> newProgramList = null;
        try{
            newProgramList = executor.invokeAll(callList).stream().map(future-> {
                try{
                    return future.get();
                }
                catch (InterruptedException | ExecutionException error){
                    System.out.println(error.getMessage());
                }
                return null;
            }).filter(Objects::nonNull).collect(Collectors.toList());
        }
        catch (InterruptedException error){
            System.out.println(error.getMessage());
        }

        //add the new thread to the list
        assert newProgramList != null;
        programs.addAll(newProgramList);
        programs.forEach(program->{
            try{
                localRepository.logProgramStateExecution(program);
                System.out.println(localRepository.getCurrentProgram().toString() + "\n");
            }
            catch (MyException error){
                System.out.println(error.getMessage());
            }
        });

        localRepository.setProgramStateList(programs);
        executor.shutdownNow();
    }

    public void allStep() throws MyException{
        executor = Executors.newFixedThreadPool(2);

        ArrayList<ProgramState> programList = removeCompletedProgram(localRepository.getProgramStateList());

        while(programList.size() > 0){
            localRepository.getCurrentProgram().getHeapTable().setContent(
                    garbageCollector.safeGarbageCollector(garbageCollector.addIndirections(
                            garbageCollector.getAddressFromTables(localRepository.getProgramStateList()),
                            localRepository.getCurrentProgram().getHeapTable()), localRepository.getCurrentProgram().getHeapTable()));
            oneStepForAllPrograms(programList);
            programList = removeCompletedProgram(localRepository.getProgramStateList());
        }

        executor.shutdownNow();
        localRepository.setProgramStateList(programList);
    }

    public IStatement getOriginalProgram(){
        return localRepository.getCurrentProgram().getOriginalProgram();
    }

    public void executeOneStep()
    {
        executor = Executors.newFixedThreadPool(8);
        ArrayList<ProgramState> programs = localRepository.getProgramStateList();

        programs.forEach(program->{
            try{
                localRepository.logProgramStateExecution(program);
            }
            catch (MyException error){
                System.out.println(error.toString());
            }
        });


        //list of callable
        List<Callable<ProgramState>> callList = programs.stream().map((ProgramState p) -> (Callable<ProgramState>) p::oneStepExecution).collect(Collectors.toList());

        //start execution
        List<ProgramState> newProgramList = null;
        try{
            newProgramList = executor.invokeAll(callList).stream().map(future-> {
                try{
                    return future.get();
                }
                catch (InterruptedException | ExecutionException error){
                    System.out.println(error.getMessage());
                }
                return null;
            }).filter(Objects::nonNull).collect(Collectors.toList());
        }
        catch (InterruptedException error){
            System.out.println(error.getMessage());
        }

        //add the new thread to the list
        assert newProgramList != null;
        programs.addAll(newProgramList);
        programs.forEach(program->{
            try{
                localRepository.logProgramStateExecution(program);
                System.out.println(localRepository.getCurrentProgram().toString() + "\n");
            }
            catch (MyException error){
                System.out.println(error.getMessage());
            }
        });

        localRepository.setProgramStateList(programs);
        executor.shutdownNow();
        

    }

    public IRepository getRepository(){
        return this.localRepository;
    }

}

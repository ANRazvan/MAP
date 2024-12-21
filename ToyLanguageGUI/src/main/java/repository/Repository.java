package repository;

import exceptions.RepoException;
import model.state.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class Repository implements IRepository {
    private List<PrgState> prgStates;
    private String logFilePath;
    private int CrtPrgIndex;

    public Repository(String logFilePath) {
        this.prgStates = new ArrayList<>();
        this.CrtPrgIndex=0;
        this.logFilePath=logFilePath;
    }

    public Repository(PrgState initState, String logFilePath) {
        this.prgStates = new ArrayList<>();
        this.prgStates.add(initState);
        this.CrtPrgIndex=0;
        this.logFilePath=logFilePath;
    }

    public void setPrgList(List <PrgState> newPrgStates){
        this.prgStates = newPrgStates;
    }

    @Override
    public List<PrgState> getPrgList() {
        return this.prgStates;
    }

    public void removePrgState() {
        prgStates.removeFirst();
    }

    public void addPrgState(PrgState state) {
        prgStates.add(state);
        this.CrtPrgIndex++;
    }

    public void logPrgStateExec(PrgState state) {
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)));
            logFile.println(state.toString());
            logFile.close();
        } catch(IOException err) {
            throw new RepoException("File not exists");
        }
    }


}
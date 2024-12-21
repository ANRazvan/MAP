package repository;
import exceptions.MyException;
import model.state.PrgState;

import java.util.List;

public interface IRepository {

    List<PrgState> getPrgList();

    void setPrgList(List<PrgState> prgStates);

    void addPrgState(PrgState state);

    void removePrgState();

    void logPrgStateExec(PrgState state) throws MyException;
}

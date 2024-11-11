package repository;
import exceptions.MyException;
import model.state.PrgState;

public interface IRepository {
    PrgState getCrtPrg();
    void addPrgState(PrgState state);

    void removePrgState();

    void logPrgStateExec() throws MyException;
}

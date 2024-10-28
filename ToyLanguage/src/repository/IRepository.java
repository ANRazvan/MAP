package repository;
import model.state.PrgState;

public interface IRepository {
    PrgState getCrtPrg();
    void addPrgState(PrgState state);
}

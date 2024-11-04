package controller;
import exceptions.EmptyStackException;
import exceptions.MyException;
import model.adt.MyIStack;
import model.state.PrgState;
import model.statements.IStmt;
import repository.IRepository;

public class Controller {
    private IRepository repo;

    public Controller(IRepository repo) {
        this.repo = repo;
    }

    public PrgState oneStep(PrgState state) throws MyException {
        MyIStack<IStmt> stack = state.getExecStack();
        if (stack.isEmpty()) {
            throw new EmptyStackException("The stack is empty!");
        }
        IStmt currentStmt = stack.pop();
        return currentStmt.execute(state);
    }

    public void allStep() throws MyException {
        PrgState prg = repo.getCrtPrg(); // repo is the controller field of type MyIRepo
        this.displayPrgState();
        while (!prg.getExecStack().isEmpty()) {
            oneStep(prg);
            this.displayPrgState();
        }
        repo.removePrgState();
    }

    public void displayPrgState() {
        System.out.println(repo.getCrtPrg());
    }

    public IRepository getRepo() {
        return repo;
    }
}
package controller;
import exceptions.EmptyStackException;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.adt.MyIStack;
import model.state.PrgState;
import model.statements.IStmt;
import repository.IRepository;

public class Controller {
    private IRepository repo;

    public Controller(IRepository repo) {
        this.repo = repo;
    }

    public PrgState oneStep(PrgState state) throws EmptyStackException, StatementException, ExpressionException {
        MyIStack<IStmt> stack = state.getExecStack();
        if (stack.isEmpty()) {
            throw new EmptyStackException("The stack is empty!");
        }
        IStmt currentStmt = stack.pop();
        return currentStmt.execute(state);
    }

    public void allStep() throws EmptyStackException, StatementException, ExpressionException {
        PrgState prg = repo.getCrtPrg(); // repo is the controller field of type MyIRepo
        this.displayPrgState();
        while (!prg.getExecStack().isEmpty()) {
            oneStep(prg);
            this.displayPrgState();
        }
    }

    public void displayPrgState() {
        System.out.println(repo.getCrtPrg());
    }
}
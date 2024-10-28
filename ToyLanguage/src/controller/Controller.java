package controller;
import exceptions.EmptyStackException;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.adt.MyIStack;
import exceptions.MyException;
import model.state.PrgState;
import model.statements.IStatement;
import repository.IRepository;

public class Controller {
    private IRepository repo;

    public Controller(IRepository repo) {
        this.repo = repo;
    }

    public PrgState oneStep(PrgState state) throws EmptyStackException, StatementException, ExpressionException {
        MyIStack<IStatement> stack = state.getExecStack();
        if (stack.isEmpty()) {
            throw new EmptyStackException("The stack is empty!");
        }
        IStatement currentStmt = stack.pop();
        return currentStmt.execute(state);
    }

    public void allStep() throws Exception {
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
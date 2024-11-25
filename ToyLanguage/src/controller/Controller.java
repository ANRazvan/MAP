package controller;
import exceptions.EmptyStackException;
import exceptions.MyException;
import model.adt.MyIStack;
import model.state.PrgState;
import model.statements.IStmt;
import model.value.RefValue;
import repository.IRepository;
import model.value.IValue;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Controller {
    private IRepository repo;

    public Controller(IRepository repo) {
        this.repo = repo;
    }


    Map<Integer, IValue> GarbageCollector(List<Integer> symTableAddr, Map<Integer,IValue> heap){
        List <Integer> heapAddr = getAddrFromHeap(heap, symTableAddr);
        return heap.entrySet().stream()
                .filter(e->heapAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues){
        return symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {
                    RefValue v1 = (RefValue)v;
                    return v1.getAddr();
                })
                .collect(Collectors.toList());
    }
    // Recursively collects addresses referenced in the heap
    List<Integer> getAddrFromHeap(Map<Integer, IValue> heap, List<Integer> symTableAddr) {
        List<Integer> addresses = symTableAddr;
        boolean added;

        do {
            added = false;
            // Collect new addresses referenced by RefValues in the current heap
            List<Integer> newAddresses = heap.entrySet().stream()
                    .filter(e -> addresses.contains(e.getKey())) // Only check reachable addresses
                    .map(Map.Entry::getValue)
                    .filter(v -> v instanceof RefValue)
                    .map(v -> ((RefValue) v).getAddr())
                    .filter(addr -> !addresses.contains(addr)) // Avoid duplicates
                    .collect(Collectors.toList());

            if (!newAddresses.isEmpty()) {
                addresses.addAll(newAddresses);
                added = true; // Continue if new addresses are added
            }
        } while (added);

        return addresses;
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
        PrgState prg = repo.getCrtPrg();
        this.displayPrgState();
        repo.logPrgStateExec();
        while (!prg.getExecStack().isEmpty()) {
            oneStep(prg);
            this.displayPrgState();
            prg.getHeap().setContent(GarbageCollector(
                    getAddrFromSymTable(prg.getSymTable().getContent().values()),
                    prg.getHeap().getContent()));
            repo.logPrgStateExec();
        }
    }

    public void displayPrgState() {
        System.out.println(repo.getCrtPrg());
    }

    public IRepository getRepo() {
        return repo;
    }
}
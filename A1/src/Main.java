
// 4. La o ferma se cresc pasari, vaci si porci.
//Sa se afiseze toate animalele care au greutatea
//mai mare decit 3kg.
import Repository.Repo;
import Controller.Controller;
import View.View;

public class Main {
    public static void main(String[] args) {
        Repo r = new Repo(10);
        Controller c = new Controller(r);
        View v = new View(c);
        v.run();
    }
}
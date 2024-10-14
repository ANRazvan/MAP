package Controller;
import Repository.*;
import Model.*;

public class Controller {
    private Repo repository;

    public Controller(Repo repo){
        this.repository = repo;
    }

    public void addAnimal(Animal a){
        try{
            this.repository.addAnimal(a);
        }
        catch (MyException e){
                System.out.println(e.getMessage());
        }
    }

    public void deleteAnimal(int index){
        try{
            this.repository.deleteAnimal(index);
        }
        catch(MyException e){
            System.out.println(e.getMessage());
        }
    }

    public Animal[] printreq(){
        try {
            Animal[] res = new Animal[this.repository.getAnimals().length];
            int i = 0;
            for (Animal a : this.repository.getAnimals()) {
                if (a != null && a.verify()) {
                    res[i] = a;
                    i++;
                }
            }
            return res;
        }
        catch (MyException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Animal[] getAnimals(){
        try{
            return this.repository.getAnimals();
        }
        catch(MyException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

}

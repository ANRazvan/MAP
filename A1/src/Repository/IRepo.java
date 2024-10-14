package Repository;
import Model.*;

public interface IRepo {
    public void addAnimal(Animal a) throws MyException;
    public void deleteAnimal(int index) throws MyException;
    public Animal[] getAnimals() throws MyException;

}

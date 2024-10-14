package Repository;

import Model.Animal;

public class Repo implements IRepo {
    private Animal[] animals;
    private int currPos;
    public Repo(int capacity){
        animals = new Animal[capacity];
        currPos = 0;
    }

    public void addAnimal(Animal a) throws MyException{
        if(currPos == animals.length)
            throw new MyException("list full");
        animals[currPos] = a;
        currPos++;
    }

    public void deleteAnimal(int index) throws MyException{
        if(index < 0 || index > currPos)
            throw new MyException("Invalid index");
        for(int i = index; i < currPos; i++){
            this.animals[i] = this.animals[i+1];
        }
        currPos--;
    }


    public Animal[] getAnimals() throws MyException{
        if(currPos == 0)
            throw new MyException("No animals");
        Animal[] a = new Animal[animals.length];
        int i = 0;
        for(Animal animal: animals){
            a[i] = animal;
            i+=1;
        }
        return a;
    }
}

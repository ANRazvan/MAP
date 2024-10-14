package Model;

public class Bird implements Animal {
    private int weight;
    private String name;

    public Bird(String name, int weight){
        this.weight = weight;
        this.name = name;
    }

    public boolean verify() {
        //    System.out.println(String.format("The weight is: %f", this.weight));
        return this.weight > 3;
    }

    public String toString(){
        return "Bird: " + this.name + " " + this.weight+"kg";
    }
}

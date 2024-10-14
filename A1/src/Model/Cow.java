package Model;

public class Cow implements Animal{
    private int weight;
    private String name;

    public Cow(String name, int weight){
        this.weight = weight;
        this.name = name;
    }

    public boolean verify() {
        //    System.out.println(String.format("The weight is: %f", this.weight));
        return this.weight > 3;
    }

    public String toString(){
        return "Cow: " + this.name + " " + this.weight+"kg";

    }

}

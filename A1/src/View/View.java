package View;
import Model.*;
import Repository.*;
import Controller.*;

import java.util.Scanner;

public class View {
    private Controller controller;

    public View(Controller controller){
        this.controller = controller;
    }

    public void addAnimal() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter name: ");
        String name = scanner.next();
        System.out.print("Enter type: ");
        String type = scanner.next();
        System.out.print("Enter weight: ");
        int weight = scanner.nextInt();
        Animal a = switch (type) {
            case "Cow", "cow" -> new Cow(name, weight);
            case "Pig", "pig" -> new Pig(name, weight);
            case "Bird", "bird" -> new Bird(name, weight);
            default -> null;
        };
        this.controller.addAnimal(a);
    }
    public void deleteAnimal() {
        System.out.print("Enter index number: ");
        Scanner scanner = new Scanner(System.in);
        int index = scanner.nextInt();
        this.controller.deleteAnimal(index);
    }

    public void showAnimalsOver3kg(){
        int i = 0;
        for(Animal a: this.controller.printreq()){
            if(a != null){
                System.out.println(i+". "+a);
                i++;
            }
        }
    }

    public void showAllAnimals(){
        int i = 0;
        for(Animal a: this.controller.getAnimals()){
            if(a != null){
                System.out.println(i+". "+a);
                i++;
            }
        }
    }

    public void menu(){
        System.out.println("Menu");
        System.out.println("1. Add animal");
        System.out.println("2. Delete animal");
        System.out.println("3. Show animals over 3kg");
        System.out.println("4. Show all animals");
        System.out.println("0. Exit");
        System.out.print("Enter option: ");
        Scanner scanner = new Scanner(System.in);
        int index = scanner.nextInt();
        switch(index){
            case 1:
                this.addAnimal();
                break;
            case 2:
                this.deleteAnimal();
                break;
            case 3:
                this.showAnimalsOver3kg();
                break;
            case 4:
                this.showAllAnimals();
                break;
            case 0:
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option");
        }
    }

    public void run(){
        Cow c = new Cow("Milka",20);
        Cow c1 = new Cow("Lola",  3);
        Cow c2 = new Cow("Margareta",  60);
        Pig p = new Pig("Piggy", 5);
        Pig p1 = new Pig("Porcucacao",2);
        Pig p2 = new Pig("Africanu", 4);
        Bird b = new Bird("Tweety", 1);
        Bird b1 = new Bird("Pingu", 2);
        Bird b2 = new Bird("Randunica", 3);
        this.controller.addAnimal(c);
        this.controller.addAnimal(p);
        this.controller.addAnimal(b);
        this.controller.addAnimal(c1);
        this.controller.addAnimal(p1);
        this.controller.addAnimal(b1);
        this.controller.addAnimal(c2);
        this.controller.addAnimal(p2);
        this.controller.addAnimal(b2);



        while(true){
            this.menu();
        }
    }


}

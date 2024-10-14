package Repository;
import java.lang.Exception;

public class MyException extends Exception {
    String message;
    public MyException(String text){
        this.message = text;
    }

    public String toString(){
        return "Error: " + this.message;
    }
}

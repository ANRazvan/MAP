package model.adt;

import exceptions.ExpressionException;
import javafx.util.Pair;
import model.statements.IStmt;

import java.util.List;

public class MyProcedureTable extends MyMap<String, Pair<List<String>, IStmt>> implements MyIProcedureTable{

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        this.map.forEach((k,v)->{
            result.append(k).append(" -> ").append(v).append("\n");
        });
        return "Procedure Table contains: " + result.toString();
    }

}

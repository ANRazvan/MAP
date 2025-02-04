package model.adt;

import javafx.util.Pair;
import model.statements.IStmt;

import java.util.List;

public interface MyIProcedureTable extends MyIMap<String, Pair<List<String>, IStmt>> {
}

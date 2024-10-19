package org.example.database;

import org.example.database.joins.JoinFactory;

import java.util.List;

public class JoinOperations {
    private List<ConnectionManager> connectionManagers;

    public JoinOperations(List<ConnectionManager> connectionManagers) {
        this.connectionManagers = connectionManagers;
    }

    public TransitDataBundle performJoin(String joinType, ConnectionManager firstConnection, String firstTable,
                                         ConnectionManager secondConnection, String secondTable) {
        JoinStrategy joinStrategy = JoinFactory.getJoinStrategy(joinType);
        if (joinStrategy != null) {
            return joinStrategy.execute(firstConnection, firstTable, secondConnection, secondTable);
        } else {
            System.out.println("Invalid join type specified.");
            return null;
        }
    }
}

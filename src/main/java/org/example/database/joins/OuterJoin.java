package org.example.database.joins;

import org.example.database.JoinStrategy;
import org.example.database.ConnectionManager;
import org.example.database.TransitDataBundle;

public class OuterJoin implements JoinStrategy {
    @Override
    public TransitDataBundle execute(ConnectionManager firstConnection, String firstTable, ConnectionManager secondConnection, String secondTable) {
        // Logic to perform outer join (full outer join as an example)
        String query = "SELECT * FROM " + firstTable + " FULL OUTER JOIN " + secondTable + " ON " + firstTable + ".id = " + secondTable + ".id";
        return firstConnection.executeQuery(query);
    }
}

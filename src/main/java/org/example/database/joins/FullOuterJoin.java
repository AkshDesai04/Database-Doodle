package org.example.database.joins;

import org.example.database.ConnectionManager;
import org.example.database.JoinStrategy;
import org.example.database.TransitDataBundle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FullOuterJoin implements JoinStrategy {

    @Override
    public TransitDataBundle execute(ConnectionManager firstConnection, String firstTable, ConnectionManager secondConnection, String secondTable) {
        TransitDataBundle dataBundle = new TransitDataBundle();
        String query = "SELECT * FROM " + firstTable + " FULL OUTER JOIN " + secondTable + " ON " + firstTable + ".id = " + secondTable + ".id"; // Adjust the join condition

        try (PreparedStatement preparedStatement = firstConnection.getConnection().prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            dataBundle.processResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataBundle;
    }
}

package org.example.database.joins;

import org.example.database.ConnectionManager;
import org.example.database.JoinStrategy;
import org.example.database.TransitDataBundle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NonEquiJoin implements JoinStrategy {

    @Override
    public TransitDataBundle execute(ConnectionManager firstConnection, String firstTable, ConnectionManager secondConnection, String secondTable) {
        TransitDataBundle dataBundle = new TransitDataBundle();
        String query = "SELECT * FROM " + firstTable + " JOIN " + secondTable + " ON " + firstTable + ".column_name < " + secondTable + ".column_name"; // Adjust the join condition

        try (PreparedStatement preparedStatement = firstConnection.getConnection().prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            dataBundle.processResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataBundle;
    }
}

package org.example.database;

import org.example.database.ConnectionManager;
import org.example.database.TransitDataBundle;

public interface JoinStrategy {
    TransitDataBundle execute(ConnectionManager firstConnection, String firstTable, ConnectionManager secondConnection, String secondTable);
}

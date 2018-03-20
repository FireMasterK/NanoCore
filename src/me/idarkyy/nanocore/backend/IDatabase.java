package me.idarkyy.nanocore.backend;


import me.idarkyy.nanocore.backend.handlers.Handler;
import me.idarkyy.nanocore.backend.type.Type;

public interface IDatabase {

    void setCurrentDatabase(String db);
    Type getCurrentDatabase();

    void setCurrentDatabaseHandler();
    Handler getCurrentDatabaseHandler();

    Type getDatabase(String db);
    Handler getDatabaseHandler(String dbHandler);

}

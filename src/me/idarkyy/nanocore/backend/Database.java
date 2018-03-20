package me.idarkyy.nanocore.backend;


import me.idarkyy.nanocore.backend.handlers.Handler;
import me.idarkyy.nanocore.backend.handlers.RedisHandler;
import me.idarkyy.nanocore.backend.type.Redis;
import me.idarkyy.nanocore.backend.type.Type;
import me.idarkyy.nanocore.exceptions.DatabaseException;

public class Database implements IDatabase {

    private Type currentDatabase;
    private Handler currentHandler;

    @Override
    public void setCurrentDatabase(String db) {
        if(db.equalsIgnoreCase("redis")) this.currentDatabase = this.getDatabase("redis");
    }

    @Override
    public Type getCurrentDatabase() {
        if (this.currentDatabase == null) {
            throw new DatabaseException("The database is null.");
        }
        return this.currentDatabase;
    }

    @Override
    public void setCurrentDatabaseHandler() {
        if(this.getCurrentDatabase().getName().equalsIgnoreCase("Redis")) {
            this.currentHandler = this.getDatabaseHandler("Redis");
        }
    }

    @Override
        public Type getDatabase (String db){
            if (db.equalsIgnoreCase("Redis")) return new Redis();
            return null;
        }

    @Override
    public Handler getCurrentDatabaseHandler() {
        if (this.currentHandler == null) {
            throw new DatabaseException("The handler is null.");
        }
        return this.currentHandler;
    }

    @Override
    public Handler getDatabaseHandler(String dbHandler) {
        if(dbHandler.equalsIgnoreCase("Redis")) {
            return new RedisHandler();
        }
        return null;
    }
}


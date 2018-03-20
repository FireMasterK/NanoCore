package me.idarkyy.nanocore.backend.type;


import me.idarkyy.nanocore.backend.handlers.Handler;

public interface Type {

    Handler getHandler();
    String getName();


}

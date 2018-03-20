package me.idarkyy.nanocore.backend.type;

import me.idarkyy.nanocore.backend.handlers.Handler;
import me.idarkyy.nanocore.backend.handlers.RedisHandler;

public class Redis implements Type {

    @Override
    public Handler getHandler() {
        return new RedisHandler();
    }

    @Override
    public String getName() {
        return "Redis";
    }
}

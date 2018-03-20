package me.idarkyy.nanocore.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@SuppressWarnings("all")
public class Updater {
    private String link;

    public Updater() {
        link = null;
    }

    public Updater(String link) {
        this.link = link;
    }

    public static Updater getNewUpdater(String link) {
        return new Updater(link);
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLatestVersion() {
        try {
            URL url = new URL(link);
            URLConnection connection = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            line = in.readLine();
            if (line != null) {
                return line;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage(c("&8(&3NanoCore&8) &7Could not execute the update task!"));
        }
        return c("&cERROR");
    }

    private String c(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
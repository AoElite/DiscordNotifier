package me.aoelite.tools.discordnotifier.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;

public class JsonConfig {

    public JsonConfig(JavaPlugin javaPlugin) {
        plugininstance = javaPlugin;
        pluginpath = plugininstance.getDataFolder().getPath();
    }

    private JavaPlugin plugininstance;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private String pluginpath;


    public <T> T getObject(String filename, Class<T> type) {
        return gson.fromJson(getJson(filename), type);
    }

    public <T> T getObjectFrom(String path, Class<T> type) {
        return gson.fromJson(getJsonFrom(path), type);
    }

    public <T> T getObject(String filename, Type type) {
        return gson.fromJson(getJson(filename), type);
    }

    public <T> T getObjectFrom(String path, Type type) {
        return gson.fromJson(getJsonFrom(path), type);
    }

    public String getJson(String filename) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(pluginpath + File.separator + filename));
            return gson.toJson(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getJsonFrom(String path) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(path));
            return gson.toJson(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> void write(String filename, T object) {
        try {
            File outFile = new File(pluginpath + File.separator + filename);
            outFile.getParentFile().mkdirs();
            outFile.createNewFile();
            FileWriter fileWriter = new FileWriter(pluginpath + File.separator + filename);
            fileWriter.write(gson.toJson(object));
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T> void write(String filename, T object, Type type) {
        try {
            File outFile = new File(pluginpath + File.separator + filename);
            outFile.getParentFile().mkdirs();
            outFile.createNewFile();
            FileWriter fileWriter = new FileWriter(pluginpath + File.separator + filename);
            fileWriter.write(gson.toJson(object, type));
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void write(String filename, String json) {
        try {
            File outFile = new File(pluginpath + File.separator + filename);
            outFile.getParentFile().mkdirs();
            outFile.createNewFile();
            FileWriter fileWriter = new FileWriter(pluginpath + File.separator + filename);
            fileWriter.write(json);
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //

    public boolean exists(String filename) {
        File outFile = new File(pluginpath + File.separator + filename);
        return outFile.exists();
    }

    public Gson getGson() {
        return gson;
    }

}

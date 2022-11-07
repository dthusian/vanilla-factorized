package dev.wateralt.mc.mcfactory;

import com.google.gson.Gson;

import java.io.*;

public class Config {
  public final boolean enableAutocrafter = true;
  public final boolean enableBreeder = true;
  public final boolean enableCauldronTap = true;
  public final boolean enablePlacer = true;
  public final boolean enablePulverizer = true;
  public final boolean enableRancher = true;

  public static Config load(File file) throws FileNotFoundException {
    Gson gson = new Gson();
    FileReader reader = new FileReader(file);
    return gson.fromJson(reader, Config.class);
  }

  public static void save(File file, Config cfg) throws IOException {
    Gson gson = new Gson();
    FileWriter writer = new FileWriter(file);
    gson.toJson(cfg, writer);
    writer.close();
  }
}

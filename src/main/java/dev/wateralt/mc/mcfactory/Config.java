package dev.wateralt.mc.mcfactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class Config {
  public final boolean enableBreeder = true;
  public final boolean enableCauldronTap = true;
  public final boolean enableNetheriteRecycler = true;
  public final boolean enablePlacer = true;
  public final boolean enablePulverizer = true;
  public final boolean enableRancher = true;
  public final boolean enableRecycler = true;
  public final boolean enableTrader = true;
  public final boolean enableTransmutor = true;

  public static Config load(File file) throws FileNotFoundException {
    Gson gson = new Gson();
    FileReader reader = new FileReader(file);
    return gson.fromJson(reader, Config.class);
  }

  public static void save(File file, Config cfg) throws IOException {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    FileWriter writer = new FileWriter(file);
    gson.toJson(cfg, writer);
    writer.close();
  }
}

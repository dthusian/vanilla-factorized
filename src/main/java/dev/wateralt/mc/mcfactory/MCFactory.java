package dev.wateralt.mc.mcfactory;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;

public class MCFactory implements ModInitializer {
  private static MCFactory INSTANCE;
  private Logger logger;
  private Config config;

  @Override
  public void onInitialize() {
    if(INSTANCE != null) throw new IllegalStateException();
    INSTANCE = this;
    logger = LoggerFactory.getLogger("mcfactory");
    logger.info("Initialize");
    URI filepath = FabricLoader.getInstance().getConfigDir().resolve("vanillafactorized.json").toUri();
    try {
      config = Config.load(new File(filepath));
    } catch (FileNotFoundException e) {
      config = new Config();
      try {
        Config.save(new File(filepath), config);
      } catch (IOException e2) {
        throw new RuntimeException(e2);
      }
    }
    MachineRegistry.init();
  }

  public static MCFactory getInstance() {
    if(INSTANCE == null) throw new IllegalStateException();
    return INSTANCE;
  }

  public Logger getLogger() {
    return logger;
  }

  public Config getConfig() { return config; }
}

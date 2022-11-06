package dev.wateralt.mc.mcfactory;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MCFactory implements ModInitializer {

  public static final Logger LOGGER = LoggerFactory.getLogger("mcfactory");

  @Override
  public void onInitialize() {
    LOGGER.info("Initialize");
    MachineRegistry.init();
  }
}

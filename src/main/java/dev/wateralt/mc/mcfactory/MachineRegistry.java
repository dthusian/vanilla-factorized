package dev.wateralt.mc.mcfactory;

import dev.wateralt.mc.mcfactory.machines.*;
import net.minecraft.block.Block;

import java.util.HashMap;
import java.util.Map;

public class MachineRegistry {

  public static final Map<Block, DispenserMachine> DISPENSER_MACHINES = new HashMap<>();

  public static void init() {
    Config cfg = Mod.getInstance().getConfig();
    if(cfg.enableRancher)
      addDispenserMachine(new MachineRancher());
    if(cfg.enableAutocrafter)
      addDispenserMachine(new MachineAutocrafter());
    if(cfg.enablePlacer)
      addDispenserMachine(new MachinePlacer());
    if(cfg.enablePulverizer)
      addDispenserMachine(new MachinePulverizer());
    if(cfg.enableBreeder)
      addDispenserMachine(new MachineBreeder());
    if(cfg.enableCauldronTap)
      addDispenserMachine(new MachineCauldronTap());
  }

  public static void addDispenserMachine(DispenserMachine machine) {
    DISPENSER_MACHINES.put(machine.getModBlock(), machine);
  }
}

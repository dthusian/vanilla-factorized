package dev.wateralt.mc.mcfactory;

import dev.wateralt.mc.mcfactory.machines.DispenserMachine;
import dev.wateralt.mc.mcfactory.machines.MachineRancher;
import net.minecraft.block.Block;

import java.util.HashMap;
import java.util.Map;

public class MachineRegistry {

  public static Map<Block, DispenserMachine> DISPENSER_MACHINES = new HashMap<>();

  public static void init() {
    addDispenserMachine(new MachineRancher());
  }

  public static void addDispenserMachine(DispenserMachine machine) {
    DISPENSER_MACHINES.put(machine.getModBlock(), machine);
  }
}

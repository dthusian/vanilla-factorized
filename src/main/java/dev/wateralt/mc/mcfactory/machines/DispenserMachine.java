package dev.wateralt.mc.mcfactory.machines;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPointer;

public abstract class DispenserMachine {
  public abstract Block getModBlock();
  public abstract Block getCostBlock();
  public abstract boolean activate(BlockPointer ptr);
}

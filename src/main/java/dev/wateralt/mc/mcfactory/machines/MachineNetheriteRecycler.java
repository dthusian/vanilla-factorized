package dev.wateralt.mc.mcfactory.machines;

import dev.wateralt.mc.mcfactory.DispenserMachine;
import dev.wateralt.mc.mcfactory.util.DispenserUtil;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPointer;

import java.util.List;

public class MachineNetheriteRecycler extends DispenserMachine {
  private static final List<Item> NETHERITE_GEAR = List.of(
      Items.NETHERITE_SWORD,
      Items.NETHERITE_AXE,
      Items.NETHERITE_PICKAXE,
      Items.NETHERITE_SHOVEL,
      Items.NETHERITE_HOE,
      Items.NETHERITE_HELMET,
      Items.NETHERITE_CHESTPLATE,
      Items.NETHERITE_LEGGINGS,
      Items.NETHERITE_BOOTS,
      Items.LODESTONE,
      Items.NETHERITE_INGOT
  );

  @Override
  public Block getModBlock() {
    return Blocks.BLACK_GLAZED_TERRACOTTA;
  }

  @Override
  public Block getCostBlock() {
    return Blocks.LODESTONE;
  }

  @Override
  public boolean activate(BlockPointer ptr) {
    DispenserBlockEntity te = ptr.blockEntity();
    int idx = DispenserUtil.searchDispenserItem(te, stack -> NETHERITE_GEAR.contains(stack.getItem()));
    if(idx >= 0) {
      te.removeStack(idx, 1);
      DispenserUtil.dropDispenserItem(ptr, new ItemStack(Items.NETHERITE_SCRAP, 4));
      ptr.world().playSound(null, ptr.pos(), SoundEvents.ENTITY_GENERIC_BURN, SoundCategory.BLOCKS, 1.0f, 1.0f);
      return true;
    }
    return false;
  }
}

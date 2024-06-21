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

import java.util.HashMap;
import java.util.Map;

public class MachineRecycler extends DispenserMachine {
  private static final Map<Item, ItemStack> RECIPES = new HashMap<>() {
      {
        put(Items.IRON_SWORD, new ItemStack(Items.IRON_NUGGET, 9));
        put(Items.IRON_AXE, new ItemStack(Items.IRON_NUGGET, 13));
        put(Items.IRON_PICKAXE, new ItemStack(Items.IRON_NUGGET, 13));
        put(Items.IRON_SHOVEL, new ItemStack(Items.IRON_NUGGET, 4));
        put(Items.IRON_HOE, new ItemStack(Items.IRON_NUGGET, 9));

        put(Items.IRON_HELMET, new ItemStack(Items.IRON_NUGGET, 22));
        put(Items.IRON_CHESTPLATE, new ItemStack(Items.IRON_NUGGET, 36));
        put(Items.IRON_LEGGINGS, new ItemStack(Items.IRON_NUGGET, 31));
        put(Items.IRON_BOOTS, new ItemStack(Items.IRON_NUGGET, 18));

        put(Items.GOLDEN_SWORD, new ItemStack(Items.GOLD_NUGGET, 9));
        put(Items.GOLDEN_AXE, new ItemStack(Items.GOLD_NUGGET, 13));
        put(Items.GOLDEN_PICKAXE, new ItemStack(Items.GOLD_NUGGET, 13));
        put(Items.GOLDEN_SHOVEL, new ItemStack(Items.GOLD_NUGGET, 4));
        put(Items.GOLDEN_HOE, new ItemStack(Items.GOLD_NUGGET, 9));

        put(Items.GOLDEN_HELMET, new ItemStack(Items.GOLD_NUGGET, 22));
        put(Items.GOLDEN_CHESTPLATE, new ItemStack(Items.GOLD_NUGGET, 36));
        put(Items.GOLDEN_LEGGINGS, new ItemStack(Items.GOLD_NUGGET, 31));
        put(Items.GOLDEN_BOOTS, new ItemStack(Items.GOLD_NUGGET, 18));
      }
  };

  @Override
  public Block getModBlock() {
    return Blocks.RED_GLAZED_TERRACOTTA;
  }

  @Override
  public Block getCostBlock() {
    return Blocks.DIAMOND_BLOCK;
  }

  @Override
  public boolean activate(BlockPointer ptr) {
    DispenserBlockEntity te = ptr.blockEntity();
    int idx = DispenserUtil.searchDispenserItem(te, stack -> RECIPES.containsKey(stack.getItem()));
    if(idx >= 0) {
      DispenserUtil.dropDispenserItem(ptr, RECIPES.get(te.getStack(idx).getItem()));
      te.removeStack(idx, 1);
      ptr.world().playSound(null, ptr.pos(), SoundEvents.ENTITY_GENERIC_BURN, SoundCategory.BLOCKS, 1.0f, 1.0f);
      return true;
    }
    return false;
  }
}

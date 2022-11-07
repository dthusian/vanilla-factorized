package dev.wateralt.mc.mcfactory.machines;

import dev.wateralt.mc.mcfactory.DispenserMachine;
import dev.wateralt.mc.mcfactory.util.DispenserUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class MachineTransmutor extends DispenserMachine {
  private record Recipe(Block inputBlock, Item inputItem, Block output) {}

  private static final List<Recipe> RECIPES = List.of(
      new Recipe(Blocks.COBBLESTONE, Items.FIRE_CHARGE, Blocks.NETHERRACK),
      new Recipe(Blocks.COBBLESTONE, Items.SCULK, Blocks.DEEPSLATE),
      new Recipe(Blocks.DEEPSLATE, Items.FIRE_CHARGE, Blocks.TUFF),
      new Recipe(Blocks.DEEPSLATE, Items.AMETHYST_SHARD, Blocks.CALCITE)
  );

  @Override
  public Block getModBlock() {
    return Blocks.PINK_GLAZED_TERRACOTTA;
  }

  @Override
  public Block getCostBlock() {
    return Blocks.DIAMOND_BLOCK;
  }

  @Override
  public boolean activate(BlockPointer ptr) {
    ServerWorld world = ptr.getWorld();
    BlockPos pointing = DispenserUtil.getDispenserPointing(ptr);
    BlockState input1 = world.getBlockState(pointing);
    DispenserBlockEntity te = ptr.getBlockEntity();
    for (Recipe recipe : RECIPES) {
      int idx = DispenserUtil.searchDispenserItem(te, stack -> stack.getItem().equals(recipe.inputItem));
      if (recipe.inputBlock.equals(input1.getBlock()) && idx >= 0) {
        ItemStack stack = te.getStack(idx);
        stack.decrement(1);
        world.setBlockState(pointing, recipe.output.getDefaultState());
        world.playSound(null, pointing, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0f, 1.0f);
        return true;
      }
    }
    return false;
  }
}

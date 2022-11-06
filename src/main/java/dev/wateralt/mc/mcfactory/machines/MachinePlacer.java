package dev.wateralt.mc.mcfactory.machines;

import dev.wateralt.mc.mcfactory.DispenserMachine;
import dev.wateralt.mc.mcfactory.util.DispenserUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class MachinePlacer extends DispenserMachine {
  private static final List<Block> REPLACEABLE_BLOCKS = List.of(new Block[]{ Blocks.AIR, Blocks.CAVE_AIR, Blocks.VOID_AIR, Blocks.WATER, Blocks.LAVA });

  @Override
  public Block getModBlock() {
    return Blocks.GRAY_GLAZED_TERRACOTTA;
  }

  @Override
  public Block getCostBlock() {
    return Blocks.DIAMOND_BLOCK;
  }

  @Override
  public boolean activate(BlockPointer ptr) {
    ServerWorld world = ptr.getWorld();
    BlockPos pointing = DispenserUtil.getDispenserPointing(ptr);
    DispenserBlockEntity te = ptr.getBlockEntity();
    int idx = DispenserUtil.searchDispenserItem(te, stack -> !stack.isEmpty());
    if(idx >= 0) {
      ItemStack stack = te.getStack(idx);
      if(stack.getItem() instanceof BlockItem blockItem) {
        BlockState blockState = blockItem.getBlock().getDefaultState();
        if(blockState != null && REPLACEABLE_BLOCKS.contains(world.getBlockState(pointing).getBlock())) {
          world.setBlockState(pointing, blockState);
          stack.decrement(1);
          return true;
        }
      }
    }
    return false;
  }
}

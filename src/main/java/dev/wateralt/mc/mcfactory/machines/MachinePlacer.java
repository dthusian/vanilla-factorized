package dev.wateralt.mc.mcfactory.machines;

import dev.wateralt.mc.mcfactory.DispenserMachine;
import dev.wateralt.mc.mcfactory.util.DispenserUtil;
import dev.wateralt.mc.mcfactory.util.ItemPlacementContextUnprotector;
import net.minecraft.block.*;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class MachinePlacer extends DispenserMachine {
  private static final List<Block> REPLACEABLE_BLOCKS = List.of(new Block[]{ Blocks.AIR, Blocks.CAVE_AIR, Blocks.VOID_AIR });

  @Override
  public Block getModBlock() {
    return Blocks.YELLOW_GLAZED_TERRACOTTA;
  }

  @Override
  public Block getCostBlock() {
    return Blocks.DIAMOND_BLOCK;
  }

  @Override
  public boolean activate(BlockPointer ptr) {
    ServerWorld world = ptr.world();
    BlockPos pointing = DispenserUtil.getDispenserPointing(ptr);
    // checks for pointing into unloaded chunks
    if(!world.canSetBlock(pointing)) return false;
    // search for an item in the dispenser and check that it's a blockitem
    DispenserBlockEntity te = ptr.blockEntity();
    int idx = DispenserUtil.searchDispenserItem(te, stack -> !stack.isEmpty());
    if(idx >= 0) {
      ItemStack stack = te.getStack(idx);
      if(stack.getItem() instanceof BlockItem blockItem) {
        // use BlockItem#getPlacementState to determine which state the block should be placed in
        Block block = blockItem.getBlock();
        ItemPlacementContext placementContext = new ItemPlacementContextUnprotector(
          world, null, Hand.MAIN_HAND, stack,
          new BlockHitResult(pointing.toCenterPos(), ptr.state().get(DispenserBlock.FACING), pointing, false)
        );
        BlockState blockState;
        // Certain placement code will try to query the given PlayerEntity
        // for a placement direction. We can't provide a PlayerEntity,
        // so ignore the generated exception and fallback to the default state.
        try {
          blockState = block.getPlacementState(placementContext);
        } catch(Exception ignored) {
          blockState = block.getDefaultState();
        }
        if(blockState != null && blockState.canPlaceAt(world, pointing) && REPLACEABLE_BLOCKS.contains(world.getBlockState(pointing).getBlock())) {
          world.setBlockState(pointing, blockState);
          block.onPlaced(world, pointing, blockState, null, stack);
          stack.decrement(1);
          world.playSound(null, pointing, SoundEvents.BLOCK_METAL_PLACE, SoundCategory.BLOCKS, 1.0f, 1.0f);
          return true;
        }
      }
    }
    return false;
  }
}

package dev.wateralt.mc.mcfactory.machines;

import dev.wateralt.mc.mcfactory.DispenserMachine;
import dev.wateralt.mc.mcfactory.util.DispenserUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
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

public class MachinePulverizer extends DispenserMachine {
  private static final List<Item> SHOVELS = List.of(Items.WOODEN_SHOVEL, Items.STONE_SHOVEL, Items.IRON_SHOVEL, Items.GOLDEN_SHOVEL, Items.DIAMOND_SHOVEL, Items.NETHERITE_SHOVEL);

  @Override
  public Block getModBlock() {
    return Blocks.YELLOW_GLAZED_TERRACOTTA;
  }

  @Override
  public Block getCostBlock() {
    return Blocks.GOLD_BLOCK;
  }

  @Override
  public boolean activate(BlockPointer ptr) {
    ServerWorld world = ptr.getWorld();
    BlockPos pointing = DispenserUtil.getDispenserPointing(ptr);
    BlockState blockState = world.getBlockState(pointing);
    BlockEntity bte = ptr.getBlockEntity();
    if(blockState.getBlock().equals(Blocks.GRAVEL) && bte instanceof DispenserBlockEntity te) {
      int idx = DispenserUtil.searchDispenserItem(te, stack -> SHOVELS.contains(stack.getItem()));
      if(idx >= 0) {
        Block replaceWith;
        ItemStack stack = te.getStack(idx);
        if(stack.getItem().equals(Items.GOLDEN_SHOVEL)) {
          replaceWith = Blocks.RED_SAND;
        } else {
          replaceWith = Blocks.SAND;
        }
        stack.damage(1, world.getRandom(), null);
        world.setBlockState(pointing, replaceWith.getDefaultState());
        world.playSound(null, pointing, SoundEvents.BLOCK_GRAVEL_BREAK, SoundCategory.BLOCKS, 1.0f, 1.0f);
        return true;
      }
    }
    return false;
  }
}

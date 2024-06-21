package dev.wateralt.mc.mcfactory.machines;

import dev.wateralt.mc.mcfactory.DispenserMachine;
import dev.wateralt.mc.mcfactory.util.DispenserUtil;
import net.minecraft.block.*;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class MachineCauldronTap extends DispenserMachine {

  @Override
  public Block getModBlock() {
    return Blocks.BLUE_GLAZED_TERRACOTTA;
  }

  @Override
  public Block getCostBlock() {
    return Blocks.DIAMOND_BLOCK;
  }

  @Override
  public boolean activate(BlockPointer ptr) {
    World world = ptr.world();
    BlockPos pointing = DispenserUtil.getDispenserPointing(ptr);
    BlockState blockState = world.getBlockState(pointing);
    int idx = DispenserUtil.searchDispenserItem(ptr.blockEntity(), stack -> stack.getItem().equals(Items.BUCKET));
    if(idx >= 0) {
      ItemStack stack = ptr.blockEntity().getStack(idx);
      boolean success = false;
      ItemStack addItems = null;
      SoundEvent sound = null;
      if(blockState.getBlock().equals(Blocks.WATER_CAULDRON) && blockState.get(LeveledCauldronBlock.LEVEL) == LeveledCauldronBlock.MAX_LEVEL) {
        addItems = new ItemStack(Items.WATER_BUCKET);
        sound = SoundEvents.ITEM_BUCKET_FILL;
        success = true;
      }
      if(blockState.getBlock().equals(Blocks.LAVA_CAULDRON)) { // lava cauldrons always full
        addItems = new ItemStack(Items.LAVA_BUCKET);
        sound = SoundEvents.ITEM_BUCKET_FILL_LAVA;
        success = true;
      }
      if(blockState.getBlock().equals(Blocks.POWDER_SNOW_CAULDRON) && blockState.get(LeveledCauldronBlock.LEVEL) == LeveledCauldronBlock.MAX_LEVEL) {
        addItems = new ItemStack(Items.POWDER_SNOW_BUCKET);
        sound = SoundEvents.ITEM_BUCKET_FILL_POWDER_SNOW;
        success = true;
      }
      if(success) {
        stack.decrement(1);
        DispenserUtil.addItemOrDrop(ptr, addItems);
        world.playSound(null, pointing, sound, SoundCategory.BLOCKS, 1.0f, 1.0f);
        world.setBlockState(pointing, Blocks.CAULDRON.getDefaultState());
        return true;
      }
    }
    return false;
  }
}

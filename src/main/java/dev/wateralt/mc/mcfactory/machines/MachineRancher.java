package dev.wateralt.mc.mcfactory.machines;

import dev.wateralt.mc.mcfactory.DispenserMachine;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

import java.util.List;

import static dev.wateralt.mc.mcfactory.util.DispenserUtil.*;

public class MachineRancher extends DispenserMachine {
  @Override
  public Block getModBlock() {
    return Blocks.WHITE_GLAZED_TERRACOTTA;
  }

  @Override
  public Block getCostBlock() {
    return Blocks.GOLD_BLOCK;
  }

  @Override
  public boolean activate(BlockPointer ptr) {
    ServerWorld world = ptr.getWorld();
    BlockPos pointing = getDispenserPointing(ptr);
    DispenserBlockEntity te = ptr.getBlockEntity();
    int idx = searchDispenserItem(te, stack -> stack.getItem().equals(Items.BUCKET));
    if(idx > 0) {
      List<CowEntity> cows = world.getEntitiesByClass(CowEntity.class, new Box(pointing), EntityPredicates.VALID_LIVING_ENTITY);
      if(cows.size() > 0) {
        world.playSound(null, pointing, SoundEvents.ENTITY_COW_MILK, SoundCategory.NEUTRAL, 1.0f, 1.0f);
        te.removeStack(idx, 1);
        ItemStack stack = new ItemStack(Items.MILK_BUCKET, 1);
        if(te.addToFirstFreeSlot(stack) == -1) {
          dropDispenserItem(ptr, stack);
        }
        return true;
      }
    }
    return false;
  }
}

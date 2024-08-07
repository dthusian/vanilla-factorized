package dev.wateralt.mc.mcfactory.machines;

import dev.wateralt.mc.mcfactory.DispenserMachine;
import dev.wateralt.mc.mcfactory.util.DispenserUtil;
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
    ServerWorld world = ptr.world();
    BlockPos pointing = getDispenserPointing(ptr);
    DispenserBlockEntity te = ptr.blockEntity();
    int idx = searchDispenserItem(te, stack -> stack.getItem().equals(Items.BUCKET));
    if(idx >= 0) {
      List<CowEntity> cows = world.getEntitiesByClass(CowEntity.class, new Box(pointing), EntityPredicates.VALID_LIVING_ENTITY);
      if(!cows.isEmpty()) {
        world.playSound(null, pointing, SoundEvents.ENTITY_COW_MILK, SoundCategory.BLOCKS, 1.0f, 1.0f);
        te.removeStack(idx, 1);
        ItemStack stack = new ItemStack(Items.MILK_BUCKET, 1);
        DispenserUtil.addItemOrDrop(ptr, stack);
        return true;
      }
    }
    return false;
  }
}

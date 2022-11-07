package dev.wateralt.mc.mcfactory.machines;

import dev.wateralt.mc.mcfactory.DispenserMachine;
import dev.wateralt.mc.mcfactory.util.DispenserUtil;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class MachineBreeder extends DispenserMachine {
  @Override
  public Block getModBlock() {
    return Blocks.LIME_GLAZED_TERRACOTTA;
  }

  @Override
  public Block getCostBlock() {
    return Blocks.GOLD_BLOCK;
  }

  @Override
  public boolean activate(BlockPointer ptr) {
    World world = ptr.getWorld();
    BlockPos pointing = DispenserUtil.getDispenserPointing(ptr);
    DispenserBlockEntity te = ptr.getBlockEntity();
    List<AnimalEntity> entities = world.getEntitiesByClass(AnimalEntity.class, new Box(pointing), EntityPredicates.VALID_ENTITY);
    if(entities.size() > 0) {
      AnimalEntity chosenOne = entities.get(world.getRandom().nextInt(entities.size()));
      int idx = DispenserUtil.searchDispenserItem(te, chosenOne::isBreedingItem);
      if(idx >= 0) {
        ItemStack stack = te.getStack(idx);
        if(!chosenOne.isInLove() && chosenOne.canEat() && chosenOne.getBreedingAge() == 0) {
          // Breedable
          stack.decrement(1);
          chosenOne.setLoveTicks(600);
          world.playSound(null, pointing, SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.NEUTRAL, 1.0f, 1.0f);
          return true;
        }
      }
    }
    return false;
  }
}

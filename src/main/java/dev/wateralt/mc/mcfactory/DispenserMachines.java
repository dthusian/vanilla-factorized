package dev.wateralt.mc.mcfactory;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class DispenserMachines {
  public interface Behavior {
    // Returns true if success
    boolean activate(BlockPointer pos);
  }

  public record Machine(Block costBlock, Behavior behavior) {
    public boolean activate(BlockPointer pos) {
      return behavior.activate(pos);
    }
  }

  public static Map<Block, Machine> MACHINES = new HashMap<>();

  public static void init() {
    // Rancher
    MACHINES.put(Blocks.WHITE_GLAZED_TERRACOTTA, new Machine(Blocks.GOLD_BLOCK, (ptr) -> {
      ServerWorld world = ptr.getWorld();
      BlockPos pointing = getDispenserPointing(ptr);
      DispenserBlockEntity te = ptr.getBlockEntity();
      int idx = searchDispenserItem(te, stack -> stack.getItem().equals(Items.BUCKET));
      if(idx > 0) {
        List<CowEntity> cows = world.getEntitiesByClass(CowEntity.class, new Box(pointing), EntityPredicates.VALID_LIVING_ENTITY);
        if(cows.size() > 0) {
          world.playSound((PlayerEntity) null, pointing, SoundEvents.ENTITY_COW_MILK, SoundCategory.NEUTRAL, 1.0f, 1.0f);
          te.removeStack(idx, 1);
          ItemStack stack = new ItemStack(Items.MILK_BUCKET, 1);
          if(te.addToFirstFreeSlot(stack) == -1) {
            dropDispenserItem(ptr, stack);
          }
          return true;
        }
      }
      return false;
    }));


  }

  public static BlockPos getDispenserPointing(BlockPointer ptr) {
    return ptr.getPos().offset(ptr.getBlockState().get(DispenserBlock.FACING));
  }

  public static int searchDispenserItem(DispenserBlockEntity te, Predicate<ItemStack> predicate) {
    for (int i = 0; i < 9; i++) {
      ItemStack stack = te.getStack(i);
      if (predicate.test(stack)) {
        return i;
      }
    }
    return -1;
  }

  public static void dropDispenserItem(BlockPointer ptr, ItemStack itemStack) {
    ItemDispenserBehavior.spawnItem(ptr.getWorld(), itemStack, 1, ptr.getBlockState().get(DispenserBlock.FACING), DispenserBlock.getOutputLocation(ptr));//TODO
  }
}

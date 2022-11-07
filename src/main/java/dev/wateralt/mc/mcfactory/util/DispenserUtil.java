package dev.wateralt.mc.mcfactory.util;

import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;

import java.util.function.Predicate;

public class DispenserUtil {
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

  public static void addItemOrDrop(BlockPointer ptr, ItemStack itemStack) {
    DispenserBlockEntity te = ptr.getBlockEntity();
    boolean hasExtra = true;
    for(int i = 0; i < 9; i++) {
      ItemStack slot = te.getStack(i);
      if(slot.isEmpty()) {
        te.setStack(i, itemStack);
        hasExtra = false;
        break;
      } else if(slot.getItem().equals(itemStack.getItem())) {
        int canAddCount = slot.getMaxCount() - slot.getCount();
        int effectivelyAdds = Math.min(canAddCount, itemStack.getCount());
        slot.increment(effectivelyAdds);
        itemStack.decrement(effectivelyAdds);
        if(itemStack.getCount() == 0) {
          hasExtra = false;
          break;
        }
      }
    }
    // Drop the rest
    if(hasExtra)
      dropDispenserItem(ptr, itemStack);
  }
}

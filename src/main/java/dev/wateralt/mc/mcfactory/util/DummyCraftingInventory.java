package dev.wateralt.mc.mcfactory.util;

import dev.wateralt.mc.mcfactory.mixin.CraftingInventoryAccessor;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;

public class DummyCraftingInventory extends CraftingInventory {
  public DummyCraftingInventory(int width, int height) {
    super(null, width, height);
  }

  @Override
  public ItemStack removeStack(int slot, int amount) {
    return Inventories.splitStack(((CraftingInventoryAccessor) this).getStacks(), slot, amount);
  }

  @Override
  public void setStack(int slot, ItemStack stack) {
    ((CraftingInventoryAccessor) this).getStacks().set(slot, stack);
  }
}

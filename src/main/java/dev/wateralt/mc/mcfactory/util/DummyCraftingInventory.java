package dev.wateralt.mc.mcfactory.util;

import dev.wateralt.mc.mcfactory.mixin.CraftingInventoryAccessor;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;

public class DummyCraftingInventory extends CraftingInventory {
  public DummyCraftingInventory(int width, int height) {
    super(null, width, height);
  }

  @Override
  public ItemStack removeStack(int slot, int amount) {
    ItemStack itemStack = Inventories.splitStack(((CraftingInventoryAccessor) this).getStacks(), slot, amount);
    return itemStack;
  }

  @Override
  public void setStack(int slot, ItemStack stack) {
    ((CraftingInventoryAccessor) this).getStacks().set(slot, stack);
  }
}

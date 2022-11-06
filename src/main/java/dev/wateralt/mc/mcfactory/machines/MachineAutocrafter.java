package dev.wateralt.mc.mcfactory.machines;

import dev.wateralt.mc.mcfactory.DispenserMachine;
import dev.wateralt.mc.mcfactory.util.DispenserUtil;
import dev.wateralt.mc.mcfactory.util.DummyCraftingInventory;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPointer;

import java.util.List;
import java.util.Optional;

public class MachineAutocrafter extends DispenserMachine {
  @Override
  public Block getModBlock() {
    return Blocks.GREEN_GLAZED_TERRACOTTA;
  }

  @Override
  public Block getCostBlock() {
    return Blocks.LODESTONE;
  }

  @Override
  public boolean activate(BlockPointer ptr) {
    ServerWorld world = ptr.getWorld();
    // verify the 3x3 crafting table structure exists
    for(int x = -1; x <= 1; x++) {
      for(int z = -1; z <= 1; z++) {
        if(!world.getBlockState(ptr.getPos().add(x, 1, z)).getBlock().equals(Blocks.CRAFTING_TABLE)) {
          return false;
        }
      }
    }
    // identify the recipe
    CraftingInventory worldCraftingInventory = new DummyCraftingInventory(3, 3);
    int i = 0;
    for(int z = -1; z <= 1; z++) {
      for(int x = -1; x <= 1; x++) {
        BlockEntity te = world.getBlockEntity(ptr.getPos().add(x, 2, z));
        if(te instanceof Inventory slot) {
          for(int j = 0; j < slot.size(); j++) {
            if(!slot.getStack(j).isEmpty()) {
              worldCraftingInventory.setStack(i, slot.getStack(j));
              break;
            }
          }
        }
        i++;
      }
    }
    Optional<CraftingRecipe> maybeRecipe = world.getRecipeManager().getFirstMatch(RecipeType.CRAFTING, worldCraftingInventory, ptr.getWorld());
    if(maybeRecipe.isEmpty()) {
      return false;
    }
    // do the recipe
    CraftingRecipe recipe = maybeRecipe.get();
    ItemStack output = recipe.craft(worldCraftingInventory);
    List<ItemStack> excess = recipe.getRemainder(worldCraftingInventory);
    DispenserUtil.dropDispenserItem(ptr, output);
    excess.forEach(itemStack -> DispenserUtil.dropDispenserItem(ptr, itemStack));
    for(i = 0; i < worldCraftingInventory.size(); i++) {
      ItemStack stack = worldCraftingInventory.getStack(i);
      if(!stack.isEmpty()) {
        stack.decrement(1);
      }
    }
    world.playSound(null, ptr.getPos(), SoundEvents.ENTITY_VILLAGER_WORK_TOOLSMITH, SoundCategory.AMBIENT, 1.0f, 1.0f);
    return true;
  }
}

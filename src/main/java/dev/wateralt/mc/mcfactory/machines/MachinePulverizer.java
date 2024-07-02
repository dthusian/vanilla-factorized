package dev.wateralt.mc.mcfactory.machines;

import dev.wateralt.mc.mcfactory.DispenserMachine;
import dev.wateralt.mc.mcfactory.util.DispenserUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
import java.util.Optional;

public class MachinePulverizer extends DispenserMachine {
  private record Recipe(Block input, Block output, boolean golden) { }
  
  private static final List<Item> TOOLS = List.of(Items.WOODEN_SHOVEL, Items.STONE_SHOVEL, Items.IRON_SHOVEL, Items.GOLDEN_SHOVEL, Items.DIAMOND_SHOVEL, Items.NETHERITE_SHOVEL);
  private static final List<Recipe> RECIPES = List.of(
    new Recipe(Blocks.COBBLESTONE, Blocks.GRAVEL, false),
    new Recipe(Blocks.GRAVEL, Blocks.RED_SAND, true),
    new Recipe(Blocks.GRAVEL, Blocks.SAND, false),
    new Recipe(Blocks.COARSE_DIRT, Blocks.DIRT, false)
  );
  
  @Override
  public Block getModBlock() {
    return Blocks.BROWN_GLAZED_TERRACOTTA;
  }

  @Override
  public Block getCostBlock() {
    return Blocks.GOLD_BLOCK;
  }

  @Override
  public boolean activate(BlockPointer ptr) {
    ServerWorld world = ptr.world();
    BlockPos pointing = DispenserUtil.getDispenserPointing(ptr);
    BlockState blockState = world.getBlockState(pointing);
    DispenserBlockEntity te = ptr.blockEntity();
    Block input = blockState.getBlock();

    int idx = DispenserUtil.searchDispenserItem(te, stack -> TOOLS.contains(stack.getItem()));
    if(idx < 0) return false;
    ItemStack shovelStack = te.getStack(idx);
    
    Optional<Recipe> maybeRecipe = RECIPES.stream().filter(v ->
      v.input() == input && (shovelStack.getItem() == Items.GOLDEN_SHOVEL || !v.golden())
    ).findFirst();
    
    if(maybeRecipe.isEmpty()) return false;
    Recipe recipe = maybeRecipe.get();

    shovelStack.damage(1, world, null, (item) -> {});
    world.setBlockState(pointing, recipe.output().getDefaultState());
    world.playSound(null, pointing, SoundEvents.BLOCK_GRAVEL_BREAK, SoundCategory.BLOCKS, 1.0f, 1.0f);
    return true;
  }
}

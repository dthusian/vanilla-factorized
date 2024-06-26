package dev.wateralt.mc.mcfactory.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

public class ItemPlacementContextUnprotector extends ItemPlacementContext {
  public ItemPlacementContextUnprotector(World world, PlayerEntity player, Hand hand, ItemStack stack, BlockHitResult hitResult) {
    super(world, player, hand, stack, hitResult);
  }
}

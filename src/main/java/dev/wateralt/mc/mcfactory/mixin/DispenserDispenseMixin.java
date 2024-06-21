package dev.wateralt.mc.mcfactory.mixin;

import dev.wateralt.mc.mcfactory.DispenserMachine;
import dev.wateralt.mc.mcfactory.MachineRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DispenserBlock.class)
public class DispenserDispenseMixin {
  @Inject(method = "dispense(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;)V", at = @At("HEAD"), cancellable = true)
  private void dispense(ServerWorld world, BlockState state, BlockPos pos, CallbackInfo ci) {
    BlockEntity te = world.getBlockEntity(pos);
    if(te instanceof DispenserBlockEntity dispTe) {
      Block modBlock = world.getBlockState(pos.add(0, -2, 0)).getBlock();
      Block costBlock = world.getBlockState(pos.add(0, -3, 0)).getBlock();
      DispenserMachine machine = MachineRegistry.DISPENSER_MACHINES.get(modBlock);
      if(machine != null) {
        ci.cancel();
        if(machine.getCostBlock().equals(costBlock)) {
          boolean success = machine.activate(new BlockPointer(world, pos, state, dispTe));
          if(success) return;
        }
        world.syncWorldEvent(1001, pos, 0);
        world.emitGameEvent(null, GameEvent.BLOCK_ACTIVATE, pos);
      }
    }
  }
}

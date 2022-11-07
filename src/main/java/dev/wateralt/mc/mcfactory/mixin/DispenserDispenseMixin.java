package dev.wateralt.mc.mcfactory.mixin;

import dev.wateralt.mc.mcfactory.DispenserMachine;
import dev.wateralt.mc.mcfactory.MachineRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPointerImpl;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DispenserBlock.class)
public class DispenserDispenseMixin {
  @Inject(method = "dispense(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;)V", at = @At("HEAD"), cancellable = true)
  private void dispense(ServerWorld world, BlockPos pos, CallbackInfo info) {
    BlockEntity te = world.getBlockEntity(pos);
    if(te instanceof DispenserBlockEntity) {
      Block modBlock = world.getBlockState(pos.add(0, -2, 0)).getBlock();
      Block costBlock = world.getBlockState(pos.add(0, -3, 0)).getBlock();
      DispenserMachine machine = MachineRegistry.DISPENSER_MACHINES.get(modBlock);
      if(machine != null) {
        if(!machine.getCostBlock().equals(costBlock)) {
          world.syncWorldEvent(1001, pos, 0);
          world.emitGameEvent(null, GameEvent.DISPENSE_FAIL, pos);
        } else {
          boolean success = machine.activate(new BlockPointerImpl(world, pos));
          if(!success) {
            world.syncWorldEvent(1001, pos, 0);
            world.emitGameEvent(null, GameEvent.DISPENSE_FAIL, pos);
          }
        }
        info.cancel();
      }
    }
  }
}

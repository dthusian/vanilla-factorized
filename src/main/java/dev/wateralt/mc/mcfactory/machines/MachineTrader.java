package dev.wateralt.mc.mcfactory.machines;

import dev.wateralt.mc.mcfactory.DispenserMachine;
import dev.wateralt.mc.mcfactory.MCFactory;
import dev.wateralt.mc.mcfactory.util.DispenserUtil;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;

import java.util.List;
import java.util.stream.Collectors;

public class MachineTrader extends DispenserMachine {
  @Override
  public Block getModBlock() {
    return Blocks.ORANGE_GLAZED_TERRACOTTA;
  }

  @Override
  public Block getCostBlock() {
    return Blocks.LODESTONE;
  }

  @Override
  public boolean activate(BlockPointer ptr) {
    BlockPos pointing = DispenserUtil.getDispenserPointing(ptr);
    
    int configHolderSlot = DispenserUtil.searchDispenserItem(ptr.blockEntity(), (itemStack) -> itemStack.getItem() == Items.NAME_TAG);
    if(configHolderSlot < 0) return false;
    ItemStack configHolder = ptr.blockEntity()
      .getStack(configHolderSlot);
    Text configStrComponent = configHolder
      .getComponents()
      .get(DataComponentTypes.CUSTOM_NAME);
    if(configStrComponent == null) return false;
    String configStr = configStrComponent.getString();
    String[] configFields = configStr.split(",");
    int configSlot = 0;
    if(configFields.length >= 1) {
      configSlot = Integer.parseInt(configFields[0]);
    } else {
      return false;
    }
    int configMaxCost = Integer.MAX_VALUE;
    if(configFields.length >= 2) {
      configMaxCost = Integer.parseInt(configFields[1]);
    }
    
    // find the offer
    List<VillagerEntity> villagers = ptr.world()
      .getEntitiesByType(
        TypeFilter.equals(VillagerEntity.class),
        new Box(pointing),
        EntityPredicates.VALID_LIVING_ENTITY
      );
    if(villagers.isEmpty()) return false;
    VillagerEntity villager = villagers.get(ptr.world().getRandom().nextInt(villagers.size()));
    TradeOfferList offers = villager.getOffers();
    TradeOffer offer = null;
    if(configSlot >= offers.size() || configSlot < 0) {
      return false;
    }
    offer = offers.get(configSlot);
    TradeOffer finalOffer = offer;
    
    // check that max uses haven't been exceeded
    if(finalOffer.getUses() >= finalOffer.getMaxUses()) {
      return false;
    }
    // check that max cost hasn't been exceeded
    if(finalOffer.getDisplayedFirstBuyItem().getCount() > configMaxCost) {
      return false;
    }
    
    // check count for stack 1
    int firstBuySlot = DispenserUtil.searchDispenserItem(ptr.blockEntity(),
      (itemStack) -> itemStack.getItem() == finalOffer.getFirstBuyItem().item().value());
    if(firstBuySlot < 0) return false;
    ItemStack firstBuyIS = ptr.blockEntity().getStack(firstBuySlot);
    if(firstBuyIS.getCount() < finalOffer.getDisplayedFirstBuyItem().getCount()) return false;
    // check count for stack 2
    ItemStack secondBuyIS = null;
    if(finalOffer.getSecondBuyItem().isPresent()) {
      int secondBuySlot = DispenserUtil.searchDispenserItem(ptr.blockEntity(),
        (itemStack) -> itemStack.getItem() == finalOffer.getSecondBuyItem().get().item().value());
      if(secondBuySlot < 0) return false;
      secondBuyIS = ptr.blockEntity().getStack(secondBuySlot);
      if(secondBuyIS.getCount() < finalOffer.getDisplayedSecondBuyItem().getCount()) return false;
    }
    
    // execute trade
    firstBuyIS.decrement(finalOffer.getDisplayedFirstBuyItem().getCount());
    if(secondBuyIS != null)
      secondBuyIS.decrement(finalOffer.getDisplayedSecondBuyItem().getCount());
    
    // autotrades do not award villager or player XP
    // so bypass the execute trades code on the entity side
    // instead, just call offer.use
    finalOffer.use();
    
    // drop output and play sound
    DispenserUtil.dropDispenserItem(ptr, finalOffer.copySellItem());
    ptr.world().playSound(null, pointing, SoundEvents.ENTITY_VILLAGER_YES, SoundCategory.BLOCKS, 1.0f, 1.0f);
    
    return true;
  }
}

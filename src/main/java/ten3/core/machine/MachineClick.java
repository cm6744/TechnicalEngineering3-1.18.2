package ten3.core.machine;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import ten3.core.item.Spanner;
import ten3.core.item.upgrades.UpgradeItem;
import ten3.lib.tile.option.RedstoneMode;
import ten3.lib.tile.option.Type;
import ten3.util.ComponentHelper;
import ten3.util.ItemNBTHelper;
import ten3.core.network.packets.PTCInfoClientPack;
import ten3.init.ItemInit;
import ten3.lib.tile.mac.CmTileMachine;
import ten3.lib.tile.option.FaceOption;

import static ten3.lib.tile.mac.CmTileMachine.*;

public class MachineClick
{

    /**
       @return can open gui
     */
    public static boolean clickMachineEvent(net.minecraft.world.level.Level worldIn, BlockPos pos, Player player, BlockHitResult hit) {

        CmTileMachine tile = (CmTileMachine) worldIn.getBlockEntity(pos);
        ItemStack main = player.getMainHandItem();
        Direction d = hit.getDirection();
        if(tile == null) {
            return false;
        }
        PTCInfoClientPack.send(tile);//send packet when open GUI
        ItemStack i = player.getMainHandItem();

        if(tile instanceof IClickProcessor prc)
        {
            return prc.click(player, main, pos);
        }

        if(i.getItem() == ItemInit.getItem("spanner"))
        {
            if(!player.isShiftKeyDown()) {
                int ene = tile.info.direCheckEnergy(d);
                int itm = tile.info.direCheckItem(d);
                int res = tile.data.get(RED_MODE);
                itm++;
                ene++;
                res++;
                if(itm >= FaceOption.size()) itm = 0;
                if(ene >= FaceOption.size()) ene = 0;
                if(res >= RedstoneMode.size()) res = 0;

                if(ItemNBTHelper.getTag(i, "mode") == Spanner.Modes.ENERGY.getIndex()) {
                    tile.info.setOpenEnergy(d, ene);
                }
                else if(ItemNBTHelper.getTag(i, "mode") == Spanner.Modes.ITEM.getIndex()) {
                    tile.info.setOpenItem(d, itm);
                }
                else if(ItemNBTHelper.getTag(i, "mode") == Spanner.Modes.REDSTONE.getIndex()) {
                    tile.data.set(RED_MODE, res);
                }
                PTCInfoClientPack.send(tile);
                return false;
            }
            else if(player.isCreative()) {
                tile.info.setCap(99999999);
                tile.data.set(MAX_ENERGY, 9999999);
                tile.data.set(ENERGY, 99999999);
                return false;
            }
        }
        else if(!player.isShiftKeyDown()
        && main.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent()) {
            FluidActionResult res = tile.handleFluidClick(main, player);
            if(res.isSuccess()) {
                if(!player.isCreative()) {
                    main.shrink(1);
                    if(main.isEmpty()) {
                        player.setItemInHand(InteractionHand.MAIN_HAND, res.getResult());
                    }
                    else {
                        ItemHandlerHelper.giveItemToPlayer(player, res.getResult());
                    }
                }
                return false;
            }
            return true;
        }
        else if(i.getItem() instanceof UpgradeItem && tile.typeOf() != Type.NON_MAC) {
            boolean success = ((UpgradeItem) i.getItem()).effect(tile);
            boolean giveSuc = false;
            for(int j = 0; j <
                    Math.min(tile.upgradeSlots.upgSize, tile.upgradeSlots.inventory.getContainerSize()); j++)
            {
                ItemStack s = tile.upgradeSlots.inventory.getItem(j);
                if(s.isEmpty()) {
                    giveSuc = true;
                    if(success) {
                        tile.upgradeSlots.inventory.setItem(j, i.copy());
                        tile.upgradeSlots.slots.get(j).setChanged();
                    }
                    break;
                }
            }
            if(success && giveSuc) {
                player.sendMessage(
                        ComponentHelper.translated(ComponentHelper.GREEN, i.getDisplayName().getString(), "ten3.info.upgrade_successfully"),
                        player.getUUID());

                if(!player.isCreative()) {
                    i.shrink(1);
                }
            }
            else if(!giveSuc) {
                player.sendMessage(
                        ComponentHelper.translated(ComponentHelper.RED, "ten3.info.too_much_upgrades"),
                        player.getUUID()
                );
            }
            else {
                player.sendMessage(
                        ComponentHelper.translated(ComponentHelper.RED, "ten3.info.not_support_upgrade"),
                        player.getUUID()
                );
            }
            PTCInfoClientPack.send(tile);
            return false;
        }

        return true;

    }

}

package ten3.core.machine;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import ten3.core.item.Spanner;
import ten3.core.item.upgrades.UpgradeItem;
import ten3.lib.tile.mac.UpgradeSlots;
import ten3.lib.tile.option.RedstoneMode;
import ten3.lib.tile.option.Type;
import ten3.util.ItemUtil;
import ten3.core.network.packets.PTCInfoClientPack;
import ten3.init.ItemInit;
import ten3.lib.tile.mac.CmTileMachine;
import ten3.lib.tile.option.FaceOption;
import ten3.util.KeyUtil;

import static ten3.lib.tile.mac.CmTileMachine.RED_MODE;

public class MachinePostEvent {

    /**
       @return hasFaceCapability open gui
     */
    public static boolean clickMachineEvent(net.minecraft.world.level.Level worldIn, BlockPos pos, Player player, BlockHitResult hit) {

        CmTileMachine tile = (CmTileMachine) worldIn.getBlockEntity(pos);
        Direction d = hit.getDirection();
        if(tile == null) {
            return false;
        }
        ItemStack i = player.getMainHandItem();

        if(!player.isShiftKeyDown() && i.getItem() == ItemInit.getItem("spanner")) {
            int ene = tile.info.direCheckEnergy(d);
            int itm = tile.info.direCheckItem(d);
            int res = tile.data.get(RED_MODE);
            itm++;
            ene++;
            res++;
            if(itm >= FaceOption.size()) itm = 0;
            if(ene >= FaceOption.size()) ene = 0;
            if(res >= RedstoneMode.size()) res = 0;

            if(ItemUtil.getTag(i, "mode") == Spanner.Modes.ENERGY.getIndex()) {
                tile.info.setOpenEnergy(d, ene);
            } else if(ItemUtil.getTag(i, "mode") == Spanner.Modes.ITEM.getIndex()) {
                tile.info.setOpenItem(d, itm);
            } else if(ItemUtil.getTag(i, "mode") == Spanner.Modes.REDSTONE.getIndex()) {
                tile.data.set(RED_MODE, res);
            }
            PTCInfoClientPack.send(tile);
            return false;
        }
        else if(i.getItem() instanceof UpgradeItem && tile.typeOf() != Type.CABLE) {
            boolean success = ((UpgradeItem) i.getItem()).effect(tile);
            boolean giveSuc = tile.itr.selfGive(i.copy(), UpgradeSlots.upgSlotFrom, UpgradeSlots.upgSlotTo, true);
            if(success && giveSuc) {
                player.sendMessage(
                        KeyUtil.translated(KeyUtil.GREEN, i.getDisplayName().getString(), "ten3.info.upgrade_successfully"),
                        player.getUUID());
                tile.itr.selfGive(i.copy(), UpgradeSlots.upgSlotFrom, UpgradeSlots.upgSlotTo, false);
                if(!player.isCreative()) {
                    i.shrink(1);
                }
            }
            else if(!giveSuc) {
                player.sendMessage(
                        KeyUtil.translated(KeyUtil.RED, "ten3.info.too_much_upgrades"),
                        player.getUUID()
                );
            }
            else {
                player.sendMessage(
                        KeyUtil.translated(KeyUtil.RED, "ten3.info.not_support_upgrade"),
                        player.getUUID()
                );
            }
            PTCInfoClientPack.send(tile);
            return false;
        }

        return true;

    }

}

package ten3.core.item.energy;

import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import ten3.lib.tile.mac.CmTileMachine;
import ten3.util.ItemNBTHelper;
import ten3.util.DisplayHelper;

import java.util.List;

import static ten3.lib.tile.mac.CmTileMachine.ENERGY;

public class EnergyItemHelper {

    public static void addTooltip(List<Component> tooltips, ItemStack stack) {

        double e = ItemNBTHelper.getTag(stack, "energy");
        double me = ItemNBTHelper.getTag(stack, "maxEnergy");

        if(e > 0 || me > 0) {
            tooltips.add(DisplayHelper.join(e, me));
        }

    }

    public static void setState(ItemStack s, int sto, int rec, int ext) {

        ItemNBTHelper.setTag(s, "receive", rec);
        ItemNBTHelper.setTag(s, "extract", ext);
        ItemNBTHelper.setTag(s, "maxEnergy", sto);

    }

    public static ItemStack getState(Item i, int sto, int rec, int ext) {

        ItemStack def = new ItemStack(i);
        setState(def, sto, rec, ext);

        return def;

    }

    public static void fillFull(Item i, NonNullList<ItemStack> stacks, int sto, int rec, int ext) {

        ItemStack full = getState(i, sto, rec, ext);
        ItemNBTHelper.setTag(full, "energy", sto);
        stacks.add(full);

    }

    public static void fillCreative(Item i, NonNullList<ItemStack> stacks)
    {
        ItemStack full = getState(i, 999999999, 999999999, 999999999);
        ItemNBTHelper.setTag(full, "energy", 999999999);
        full.setHoverName(new TextComponent("Cm Debugger").withStyle(ChatFormatting.GOLD));
        stacks.add(full);
    }

    public static void fillEmpty(Item i, NonNullList<ItemStack> stacks, int sto, int rec, int ext) {

        ItemStack def = getState(i, sto, rec, ext);
        stacks.add(def);

    }

    //MACHINES:

    public static ItemStack fromMachine(CmTileMachine tile, ItemStack stack) {

        ItemNBTHelper.setTag(stack, "energy", tile.data.get(ENERGY));
        ItemNBTHelper.setTag(stack, "receive", tile.info.maxReceiveEnergy);
        ItemNBTHelper.setTag(stack, "extract", tile.info.maxExtractEnergy);
        ItemNBTHelper.setTag(stack, "maxEnergy", tile.info.maxStorageEnergy);
        tile.nbtManager.writeNBTUpg(stack.getOrCreateTag());

        for(int i = 0; i < tile.upgradeSlots.getInv().getContainerSize(); i++) {
            stack.getOrCreateTag().put("upg" + i, tile.upgradeSlots.getInv().getItem(i).serializeNBT());
        }

        return stack;

    }

    public static void pushToTile(CmTileMachine tile, ItemStack stack) {

        tile.data.set(ENERGY, ItemNBTHelper.getTag(stack, "energy"));
        tile.nbtManager.readNBTUpg(stack.getOrCreateTag());//it's not nec to set caps.

        for(int i = 0; i < tile.upgradeSlots.getInv().getContainerSize(); i++) {
            CompoundTag nbt = stack.getOrCreateTag().getCompound("upg" + i);
            tile.upgradeSlots.getInv().setItem(i, ItemStack.of(nbt));
        }

    }

}

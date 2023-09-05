package ten3.lib.tile.mac;

import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import ten3.core.item.upgrades.UpgradeItem;
import ten3.lib.capability.item.AdvancedInventory;
import ten3.lib.tile.option.Type;
import ten3.lib.wrapper.ISlotAcceptor;
import ten3.lib.wrapper.SlotCm;

import java.util.ArrayList;
import java.util.List;

public class UpgradeSlots implements ISlotAcceptor
{

    CmTileMachine t;
    public int upgSize;
    public int initialUpgSize = 1;

    public List<SlotCm> slots = new ArrayList<>();
    public AdvancedInventory inventory;

    public UpgradeSlots(CmTileMachine tile)
    {
        t = tile;
        inventory = new AdvancedInventory(6, slots, t);

        if(t.typeOf() != Type.NON_MAC) {
            addSlot(new SlotCm(this, 0, 32, -28));
            addSlot(new SlotCm(this, 1, 51, -28));
            addSlot(new SlotCm(this, 2, 70, -28));
            addSlot(new SlotCm(this, 3, 89, -28));
            addSlot(new SlotCm(this, 4, 108, -28));
            addSlot(new SlotCm(this, 5, 127, -28));
        }
    }

    public boolean valid(int slot, ItemStack stack)
    {
        return stack.getItem() instanceof UpgradeItem;
    }

    public Container getInv()
    {
        return inventory;
    }

    public void addSlot(SlotCm s)
    {
        slots.add(s);
    }

    public void upgradeUpdate(boolean clear)
    {
        for(int i = 0; i < upgSize && !clear; i++) {
            ItemStack iks = inventory.getItem(i);
            Item ik = iks.getItem();

            if(ik instanceof UpgradeItem) {
                ((UpgradeItem) ik).effect(t);
            }

        }

        if(clear) {
            upgSize = initialUpgSize;
        }
    }

    public int countUpgrade(Class<? extends UpgradeItem> it)
    {
        int ct = 0;
        for(int i = 0; i < upgSize; i++) {
            ItemStack iks = inventory.getItem(i);
            Item ik = iks.getItem();
            if(ik.getClass() == it) {
                ct++;
            }
        }
        return ct;
    }

}

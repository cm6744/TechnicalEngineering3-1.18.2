package ten3.lib.tile.mac;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import ten3.core.item.upgrades.UpgradeItem;

public class UpgradeSlots
{

    CmTileMachine t;
    public int upgSize;
    public int initialUpgSize = 1;

    public static int upgSlotFrom = 34;
    public static int upgSlotTo = 39;

    public UpgradeSlots(CmTileMachine tile)
    {
        t = tile;
    }

    public boolean isUpgradeSlot(int slot)
    {
        return slot >= upgSlotFrom && slot <= upgSlotTo;
    }

    public void upgradeUpdate(boolean clear)
    {
        for(int i = 0; i < upgSize && !clear; i++) {
            ItemStack iks = t.inventory.getItem(i + upgSlotFrom);
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
            ItemStack iks = t.inventory.getItem(i + upgSlotFrom);
            Item ik = iks.getItem();
            if(ik.getClass() == it) {
                ct++;
            }
        }
        return ct;
    }

}

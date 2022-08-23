package ten3.lib.wrapper;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import ten3.core.client.ClientHolder;
import ten3.core.item.upgrades.UpgradeItem;

public class SlotUpgCm extends SlotCm {

    public SlotUpgCm(Container i, int id, int x, int y) {

        super(i, id, x, y, SlotCm.RECEIVE_ALL_INPUT, false, false);

    }

    @Override
    public boolean isItemValidInHandler(ItemStack stack) {

        if(stack.getItem() instanceof UpgradeItem) {
            return true;
        }

        return false;

    }

}

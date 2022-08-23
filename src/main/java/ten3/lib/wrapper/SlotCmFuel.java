package ten3.lib.wrapper;

import com.google.common.collect.Lists;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import ten3.core.machine.engine.MatchFuel;

public class SlotCmFuel extends SlotCm {

    public SlotCmFuel(Container i, int id, int x, int y, boolean ext, boolean in) {

        super(i, id, x, y, Lists.newArrayList(), ext, in);

    }

    @Override
    public boolean isItemValidInHandler(ItemStack stack) {

        return MatchFuel.matchFuelAndShrink(stack, false) > 0;

    }

}

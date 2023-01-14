package ten3.lib.wrapper;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

public class SlotChangableCm extends SlotCm {

    Condition cond;
    Condition in, out;

    public SlotChangableCm(Container i, int id, int x, int y, Condition c, Condition ext, Condition in) {

        super(i, id, x, y, null, false, false);

        cond = c;

        this.in = in;
        this.out = ext;

    }

    public boolean canHandlerExt()
    {
        return out.check(getItem());
    }

    public boolean canHandlerIn()
    {
        return in.check(getItem());
    }

    @Override
    public boolean isItemValidInHandler(ItemStack stack) {

        return cond.check(stack);

    }

    public interface Condition {
        boolean check(ItemStack stack);
    }

}

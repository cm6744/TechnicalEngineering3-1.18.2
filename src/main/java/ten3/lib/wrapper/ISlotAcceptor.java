package ten3.lib.wrapper;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

public interface ISlotAcceptor
{

    //be invoked both side!!!
    //just check stack, do not use tile entity.
    boolean valid(int slot, ItemStack stack);

    Container getInv();

}

package ten3.init.template;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class InvisibleItem extends DefItem {

    public InvisibleItem() {

        super(true);

    }

    @Override
    public void inventoryTick(ItemStack stack, Level p_41405_, Entity p_41406_, int p_41407_, boolean p_41408_)
    {
        stack.setCount(0);
    }
}
